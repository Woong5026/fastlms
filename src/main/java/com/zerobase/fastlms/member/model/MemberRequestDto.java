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
}