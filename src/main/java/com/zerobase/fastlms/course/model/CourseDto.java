package com.zerobase.fastlms.course.model;

import lombok.Data;

@Data
public class CourseDto {

    String subject;

    // 페이징 카운트트
    long totalCount;
    long seq;
}
