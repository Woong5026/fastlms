package com.zerobase.fastlms.course.service;

import com.zerobase.fastlms.course.dto.TakeCourseDto;
import com.zerobase.fastlms.course.model.*;

import java.util.List;

public interface TakeCourseService {


    // 수강목록
    List<TakeCourseDto> list(TakeCourseParam param);

    // 수강내용 상태변경
    ServiceResult updateStatus(long id, String status);
}
