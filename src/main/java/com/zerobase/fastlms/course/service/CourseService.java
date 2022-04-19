package com.zerobase.fastlms.course.service;

import com.zerobase.fastlms.admin.model.CategoryDto;
import com.zerobase.fastlms.course.model.CourseDto;
import com.zerobase.fastlms.course.model.CourseParam;

import java.util.List;

public interface CourseService {

    boolean add(CourseDto param);

    List<CourseDto> list(CourseParam param);
}
