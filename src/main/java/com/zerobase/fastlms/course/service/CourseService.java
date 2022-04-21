package com.zerobase.fastlms.course.service;

import com.zerobase.fastlms.admin.model.CategoryDto;
import com.zerobase.fastlms.course.model.CourseDto;
import com.zerobase.fastlms.course.model.CourseInput;
import com.zerobase.fastlms.course.model.CourseParam;

import java.util.List;

public interface CourseService {

    boolean add(CourseInput param);

    // 강좌정보 수정
    boolean set(CourseInput param);

    List<CourseDto> list(CourseParam param);

    // 강좌 상세정보
    CourseDto getById(long id);

    // 강좌 내용삭제
    boolean del(String idList);
}
