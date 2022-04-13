package com.zerobase.fastlms.member.service;

import com.zerobase.fastlms.member.model.MemberRequestDto;

public interface MemberService {

    boolean register(MemberRequestDto requestDto);
}
