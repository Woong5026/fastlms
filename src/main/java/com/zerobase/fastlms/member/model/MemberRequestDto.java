package com.zerobase.fastlms.member.model;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class MemberRequestDto {
    private String userId;
    private String userName;
    private String phone;
    private String password;

    // 추가
    private String newPassword;

    private String zipcode;
    private String addr;
    private String addrDetail;
}
