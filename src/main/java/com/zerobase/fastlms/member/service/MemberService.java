package com.zerobase.fastlms.member.service;

import com.zerobase.fastlms.member.model.MemberRequestDto;
import com.zerobase.fastlms.member.model.ResetPasswordRequestDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface MemberService extends UserDetailsService {

    boolean register(MemberRequestDto requestDto);
    // uuid에 해당하는 계정을 활성화
    boolean emailAuth(String uuid);

    // 입력한 이메일로 비밀번호 초기화 정보를 전송
    boolean sendResetPassword(ResetPasswordRequestDto requestDto);

    // 입력받은 uuid에 대해서 패스워드로 초기화 진행
    boolean resetPassword(String id, String password);

    // 입력받은 uuid값이 유효한지 확인
    boolean checkResetPassword(String uuid);
}
