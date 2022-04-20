package com.zerobase.fastlms.course.model;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
public class CourseDto {

    Long id;
    long categoryId;
    String imagePath;
    String keyword;
    String subject;
    String summary;
    String contents;
    long price;
    long salePrice;
    Date saleEndDt;
    LocalDateTime regDt;//등록일(추가날짜)
    LocalDateTime udtDt;//수정일(수정날짜)

    // 페이징 카운트트
    long totalCount;
    long seq;
}
