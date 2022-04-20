package com.zerobase.fastlms.course.service;

import com.zerobase.fastlms.admin.dto.MemberDto;
import com.zerobase.fastlms.admin.model.CategoryDto;
import com.zerobase.fastlms.course.entity.Course;
import com.zerobase.fastlms.course.mapper.CourseMapper;
import com.zerobase.fastlms.course.model.CourseDto;
import com.zerobase.fastlms.course.model.CourseParam;
import com.zerobase.fastlms.course.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CourseServiceImpl implements CourseService{

    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;

    @Override
    public boolean add(CourseDto param) {

        Course course = Course.builder()
                .subject(param.getSubject())
                .regDt(LocalDateTime.now())
                .build();
        courseRepository.save(course);


        return true;
    }

    @Override
    public List<CourseDto> list(CourseParam param) {

        long totalCount = courseMapper.selectListCount(param);

        List<CourseDto> list = courseMapper.selectList(param);

        // 각 칼럼에 totalCount를 넣어주는 식
        if(!CollectionUtils.isEmpty(list)){
            int i = 0;
            for (CourseDto m : list){
                m.setTotalCount(totalCount);
                m.setSeq(totalCount - param.getPageStart() - i);
                i++;
            }
        }

        return list;
    }
}
