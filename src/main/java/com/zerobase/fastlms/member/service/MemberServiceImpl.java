package com.zerobase.fastlms.member.service;

import com.zerobase.fastlms.component.MailComponents;
import com.zerobase.fastlms.member.entity.Member;
import com.zerobase.fastlms.member.exception.MemberNotEmailAuthException;
import com.zerobase.fastlms.member.model.MemberRequestDto;
import com.zerobase.fastlms.member.model.ResetPasswordRequestDto;
import com.zerobase.fastlms.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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

        // BCrypt로 암호화하는 과정
        String encPassword = BCrypt.hashpw(requestDto.getPassword(), BCrypt.gensalt());
        String uuid = UUID.randomUUID().toString();


        Member member = Member.builder()
                .userId(requestDto.getUserId())
                .userName(requestDto.getUserName())
                .phone(requestDto.getPhone())
                .password(encPassword)
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

    @Override
    public boolean sendResetPassword(ResetPasswordRequestDto requestDto) {

        // 일치하는 정보를 찾기 위해 회원정보를 먼저 찾는다
        Optional<Member> optionalMember = memberRepository
                .findByUserIdAndUserName(requestDto.getUserId(), requestDto.getUserName());
        if(!optionalMember.isPresent()){
            throw new UsernameNotFoundException("회원정보가 존재하지 않습니다");
        }
        Member member = optionalMember.get();

        String uuid = UUID.randomUUID().toString();

        member.setResetPasswordKey(uuid);
        // 초기화는 하루정도면 충분하기에 아래와 같이 설정정
       member.setResetPasswordLimitDt(LocalDateTime.now().plusDays(1));
       memberRepository.save(member);

        // 메일은 사용자가 받은 메일에서 링크를 타고 오면
        // 사이트에서 그 사람의 id, password를 입력해서 값이 맞으면 그 값으로 초기화하는 작업을 하기에
        // 해당 사용자만 아는 값을 보내줘야 한다
        String email = requestDto.getUserId();
        String subject = "비밀번호 초기화 메일입니다";
        String text = "<p>아래의 링크를 출력해 비밀번호를 초기화하세요</p>" +
                "<div><a href='http://localhost:8080/member/reset/password?id="+uuid+"'>비밀번호 초기화 링크</a></div>";

        mailComponents.sendMail(email, subject,text);

        return true;
    }

    @Override
    public boolean resetPassword(String uuid, String password) {

        Optional<Member> optionalMember = memberRepository.findByResetPasswordKey(uuid);
        if(!optionalMember.isPresent()){
            throw new UsernameNotFoundException("회원정보가 존재하지 않습니다");
        }
        Member member = optionalMember.get();

        // 초기화 날짜가 유효한지 확인
        // 아래 두가지 조건문을 만족해야지 값이 들어간다
        if(member.getResetPasswordLimitDt() == null){
            throw new RuntimeException("유효한 날짜가 아닙니다");
        }

        if(member.getResetPasswordLimitDt().isBefore(LocalDateTime.now())){
            throw new RuntimeException("유효한 날짜가 아닙니다");
        }


        String encPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        member.setPassword(encPassword);
        // 초기화할때 패스워드만 초기화하는 것이 아니라 아래 값도 초기화해야함
        member.setResetPasswordKey("");
        member.setResetPasswordLimitDt(null);
        memberRepository.save(member);

        return true;
    }

    @Override
    public boolean checkResetPassword(String uuid) {
        Optional<Member> optionalMember = memberRepository.findByResetPasswordKey(uuid);
        if(!optionalMember.isPresent()){
            return false;
        }
        Member member = optionalMember.get();

        // 초기화 날짜가 유효한지 확인
        // 아래 두가지 조건문을 만족해야지 값이 들어간다
        if(member.getResetPasswordLimitDt() == null){
            throw new RuntimeException("유효한 날짜가 아닙니다");
        }

        if(member.getResetPasswordLimitDt().isBefore(LocalDateTime.now())){
            throw new RuntimeException("유효한 날짜가 아닙니다");
        }

        return true;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<Member> optionalMember = memberRepository.findById(username);
        if(!optionalMember.isPresent()){
            throw new UsernameNotFoundException("회원정보가 존재하지 않습니다");
        }
        Member member = optionalMember.get();

        // 이메일 인증절차에 관한 로직
        // true가 아닐때 예외처리
        if(!member.isEmailAuthYn()){
            throw new MemberNotEmailAuthException("이메일 활성화 이후에 로그인 진행해주세요");
        }

        // return 객체의 파라미터가 이름, 비밀번호 , role인데 role은 직접 설정해야함
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));

        return new User(member.getUserName(), member.getPassword(),grantedAuthorities);
    }
}
