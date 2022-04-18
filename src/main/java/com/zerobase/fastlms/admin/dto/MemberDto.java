package com.zerobase.fastlms.admin.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MemberDto {

    String userId;
    String userName;
    String phone;
    String password;
    LocalDateTime regDt;

    boolean emailAuthYn;
    LocalDateTime emailAuthDt;
    String emailAuthKey;

    String restPasswordKey;
    LocalDateTime resetPasswordLimitDt;

    boolean adminYn;

    // 페이징 카운트트
   long totalCount;

   long seq;
}
