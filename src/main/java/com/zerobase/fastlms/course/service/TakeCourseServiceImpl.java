package com.zerobase.fastlms.course.service;

import com.zerobase.fastlms.course.dto.TakeCourseDto;
import com.zerobase.fastlms.course.entity.Course;
import com.zerobase.fastlms.course.entity.TakeCourse;
import com.zerobase.fastlms.course.mapper.CourseMapper;
import com.zerobase.fastlms.course.mapper.TakeCourseMapper;
import com.zerobase.fastlms.course.model.*;
import com.zerobase.fastlms.course.repository.CourseRepository;
import com.zerobase.fastlms.course.repository.TakeCourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class TakeCourseServiceImpl implements TakeCourseService{


    private final TakeCourseMapper takeCourseMapper;


    @Override
    public List<TakeCourseDto> list(TakeCourseParam param) {

        long totalCount = takeCourseMapper.selectListCount(param);

        List<TakeCourseDto> list = takeCourseMapper.selectList(param);

        // 각 칼럼에 totalCount를 넣어주는 식
        if(!CollectionUtils.isEmpty(list)){
            int i = 0;
            for (TakeCourseDto m : list){
                m.setTotalCount(totalCount);
                m.setSeq(totalCount - param.getPageStart() - i);
                i++;
            }
        }

        return list;
    }
}
