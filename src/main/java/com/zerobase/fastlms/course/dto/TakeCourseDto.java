package com.zerobase.fastlms.course.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TakeCourseDto {


    long id;

    long courseId;
    String userId;

    long payPrice;
    String status;

    LocalDateTime regDt;

    // 조인컬럼
    String userName;
    String phone;
    String subject;

    // 페이징 카운트트
    long totalCount;
    long seq;

    public String getRegDtText(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return regDt != null ? regDt.format(formatter) : "";
    }

}
