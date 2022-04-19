package com.zerobase.fastlms.course.service;

import com.zerobase.fastlms.admin.model.CategoryDto;
import com.zerobase.fastlms.course.entity.Course;
import com.zerobase.fastlms.course.model.CourseDto;
import com.zerobase.fastlms.course.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class CourseServiceImpl implements CourseService{

    private final CourseRepository courseRepository;

    @Override
    public boolean add(CourseDto param) {

        Course course = Course.builder()
                .subject(param.getSubject())
                .regDt(LocalDateTime.now())
                .build();
        courseRepository.save(course);


        return true;
    }
}
