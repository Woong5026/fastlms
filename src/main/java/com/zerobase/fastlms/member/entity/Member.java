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
public class Member implements MemberCode{

    @Id
    private String userId;
    private String userName;
    private String phone;
    private String password;
    private LocalDateTime regDt;
    private LocalDateTime uptDt;

    private boolean emailAuthYn;
    private String emailAuthKey;
    private LocalDateTime emailAuthDt;

    // 아래의 키가 맞는 사용자만 초기화하기위한 컬럼
    private String resetPasswordKey;
    // 이 날짜보다 값이 크면 초기화할 수 없다
    private LocalDateTime resetPasswordLimitDt;

    // 관리자여부를 지정할건지 or 회원에 따른 role(준회원,정회원,관리자 등)을 지정할 것이냐
    // 일단 사용자가 관리자인지 아닌지 판단할 거임
    private boolean adminYn;

    // 이용가능한 상태 , 정지상태태
   private String userStatus;

   private String zipcode;
   private String addr;
   private String addrDetail;

}
