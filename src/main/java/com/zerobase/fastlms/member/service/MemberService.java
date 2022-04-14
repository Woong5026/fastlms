package com.zerobase.fastlms.member.service;

import com.zerobase.fastlms.member.model.MemberRequestDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface MemberService extends UserDetailsService {

    boolean register(MemberRequestDto requestDto);
    // uuid에 해당하는 계정을 활성화
    boolean emailAuth(String uuid);

}
