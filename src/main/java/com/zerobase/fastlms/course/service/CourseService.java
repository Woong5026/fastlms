package com.zerobase.fastlms.course.service;

import com.zerobase.fastlms.course.model.*;

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

    // 프론트용 강좌 목록
    List<CourseDto> frontList(CourseParam param);

    // 강좌 상세정보
   CourseDto frontDetail(long id);

   // 수강신청
   ServiceResult request(TakeCourseInput param);

   // 전체 강좌정보 목록
    List<CourseDto> listAll();
}
