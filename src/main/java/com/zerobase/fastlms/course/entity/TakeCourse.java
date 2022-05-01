package com.zerobase.fastlms.course.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TakeCourse implements TakeCourseCode{

    public static String STATUS_REQ = "REQ"; // 수강 신청상태
    public static String STATUS_COM = "COM"; // 결제완료
    public static String STATUS_CANCEL = "CANCEL"; // 수강취소

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    long courseId;
    String userId;

    long payPrice;
    String status;

    LocalDateTime regDt;
}
