package com.zerobase.fastlms.member.service;

import com.zerobase.fastlms.component.MailComponents;
import com.zerobase.fastlms.member.entity.Member;
import com.zerobase.fastlms.member.model.MemberRequestDto;
import com.zerobase.fastlms.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;
    private final MailComponents mailComponents;

    @Override
    public boolean register(MemberRequestDto requestDto) {

        Optional<Member> validation = memberRepository.findById(requestDto.getUserId());

        if(validation.isPresent()){
            return false;
        }

        String uuid = UUID.randomUUID().toString();


        Member member = Member.builder()
                .userId(requestDto.getUserId())
                .userName(requestDto.getUserName())
                .phone(requestDto.getPhone())
                .password(requestDto.getPassword())
                .regDt(LocalDateTime.now())
                .emailAuthYn(false)
                .emailAuthKey(uuid)
                .build();

        memberRepository.save(member);

        String email = requestDto.getUserId();
        String subject = "회원가입을 축하합니다";
        String text = "<p>회원가입을 축하하며 아래 링크를 클릭해서 가입을 완료하세요</p>" +
                "<div><a href='http://localhost:8080/member/email-auth?id="+uuid+"'>회원가입</a></div>";

        mailComponents.sendMail(email, subject,text);

        return true;
    }

    @Override
    public boolean emailAuth(String uuid) {

        // uuid를 찾아오는 작업
        Optional<Member> findMember = memberRepository.findByEmailAuthKey(uuid);

        // emailAuthKey가 없다면 false를 반환하고 있다면 member정보를 가져온다
        // 이메일 인증한 날짜도 인증하는 date 필드도 검증
        if(!findMember.isPresent()){
            return false;
        }

        Member member = findMember.get();
        member.setEmailAuthYn(true);
        member.setEmailAuthDt(LocalDateTime.now());
        memberRepository.save(member);

        return true;
    }

}
