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

        Member member = new Member();
        member.setUserId(requestDto.getUserId());
        member.setUserName(requestDto.getUserName());
        member.setPhone(requestDto.getPhone());
        member.setPassword(requestDto.getPassword());
        member.setRegDt(LocalDateTime.now());

        // 처음에는 인증하지 않았으니 false
        member.setEmailAuthYn(false);
        member.setEmailAuthKey(uuid);

        memberRepository.save(member);

        String email = requestDto.getUserId();
        String subject = "회원가입을 축하합니다";
        String text = "<p>회원가입을 축하하며 아래 링크를 클릭해서 가입을 완료하세요</p>" +
                "<div><a href='http://localhost:8080/member/email-auth?id="+uuid+"'>회원가입</a></div>";

        mailComponents.sendMail(email, subject,text);

        return true;
    }

}
