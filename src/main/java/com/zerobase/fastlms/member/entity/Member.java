package com.zerobase.fastlms.member.entity;

import com.zerobase.fastlms.member.model.MemberRequestDto;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Member {

    @Id
    private String userId;
    private String userName;
    private String phone;
    private String password;
    private LocalDateTime regDt;

    private boolean emailAuthYn;
    private String emailAuthKey;
    private LocalDateTime emailAuthDt;

    public Member(String userId, String userName, String phone, String password, LocalDateTime regDt) {
        this.userId = userId;
        this.userName = userName;
        this.phone = phone;
        this.password = password;
        this.regDt = regDt;
    }

    public Member(MemberRequestDto requestDto){
        this.userId = requestDto.getUserId();
        this.password = requestDto.getPassword();
        this.userName = requestDto.getUserName();
        this.phone = requestDto.getPhone();
        this.regDt = LocalDateTime.now();
    }
}
