package com.zerobase.fastlms.course.service;

import com.zerobase.fastlms.course.entity.Course;
import com.zerobase.fastlms.course.entity.TakeCourse;
import com.zerobase.fastlms.course.mapper.CourseMapper;
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
public class CourseServiceImpl implements CourseService{

    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;
    private final TakeCourseRepository takeCourseRepository;

    private LocalDate getLocalDate(String value){
        // 문자열을 LocalDate로 바꿀 수 있는지 확인
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        try {
            return LocalDate.parse(value, format);
        }catch (Exception e){
        }

        return null;
    }

    @Override
    public boolean add(CourseInput param) {

        LocalDate saleEndDt = getLocalDate(param.getSaleEndDtText());

        Course course = Course.builder()
                .categoryId(param.getCategoryId())
                .subject(param.getSubject())
                .keyword(param.getKeyword())
                .summary(param.getSummary())
                .contents(param.getContents())
                .price(param.getPrice())
                .salePrice(param.getSalePrice())
                .saleEndDt(saleEndDt)
                .regDt(LocalDateTime.now())
                .build();
        courseRepository.save(course);


        return true;
    }

    @Override
    public boolean set(CourseInput param) {

        LocalDate saleEndDt = getLocalDate(param.getSaleEndDtText());

        Optional<Course> optionalCourse = courseRepository.findById(param.getId());

        // null 이면 수정할 데이터가 없는것
        if(!optionalCourse.isPresent()){
            return false;
        }
        // 수정할 데이터가 있다면
        Course course = optionalCourse.get();
        course.setCategoryId(param.getCategoryId());
        course.setSubject(param.getSubject());
        course.setKeyword(param.getKeyword());
        course.setSummary(param.getSummary());
        course.setContents(param.getContents());
        course.setPrice(param.getPrice());
        course.setSalePrice(param.getSalePrice());
        course.setUdtDt(LocalDateTime.now());
        course.setSaleEndDt(saleEndDt);
        courseRepository.save(course);

        return false;
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

    @Override
    public CourseDto getById(long id) {

        return courseRepository.findById(id).map(CourseDto::of).orElse(null);

    }

    @Override
    public boolean del(String idList) {

        // , 단위의 문자열이기 때문에 null 체크
        if(idList != null && idList.length() > 0){

            String[] ids = idList.split(",");
            for (String x : ids){
                // id를 정수혈태로 담아야 하기에 long타입
                long id = 0L;
                try {
                    id = Long.parseLong(x);
                }catch (Exception e){

                }

                if(id > 0){
                    courseRepository.deleteById(id);
                }

            }

        }

        return true;
    }

    @Override
    public List<CourseDto> frontList(CourseParam param) {

        // 이 경우는 전체니까 전체를 가져오는 값을 넣으면 된다
        if(param.getCategoryId() < 1){
            List<Course> courseList = courseRepository.findAll();
            return CourseDto.of(courseList);
        }

        Optional<List<Course>> optionalCourses = courseRepository.findByCategoryId(param.getCategoryId());

        if(optionalCourses.isPresent()){
            return CourseDto.of(optionalCourses.get());
        }

        return null;



    }

    @Override
    public CourseDto frontDetail(long id) {

        Optional<Course> optionalCourse = courseRepository.findById(id);

        if(optionalCourse.isPresent()){
            return CourseDto.of(optionalCourse.get());
        }
        return null;
    }

    @Override
    public ServiceResult request(TakeCourseInput param) {

        ServiceResult result = new ServiceResult();

        // 강좌글 id 가져오기
        Optional<Course> optionalCourse = courseRepository.findById(param.getCourseId());
        if(!optionalCourse.isPresent()){
            // 현재 에러는 강좌정보 자체가 없는것
            result.setResult(false);
            result.setMessage("강좌정보가 존재하지 않습니다");
            return result;
        }

        Course course = optionalCourse.get();

        // 이미 신청한 경우 신청이 안되는 로직

        String[] statusList = {TakeCourse.STATUS_REQ, TakeCourse.STATUS_COM};

        long count = takeCourseRepository.countByCourseIdAndUserIdAndStatusIn(course.getId(),
                param.getUserId(), Arrays.asList(statusList));

        if (count > 0){
            // 이미 신청한정보가 있을 때
            result.setResult(false);
            result.setMessage("이미 신청한 강좌정보가 있습니다");
            return result;
        }

        TakeCourse takeCourse = TakeCourse.builder()
                .courseId(course.getId())
                .userId(param.getUserId())
                .payPrice(course.getSalePrice())
                .regDt(LocalDateTime.now())
                .status(TakeCourse.STATUS_REQ)
                .build();

        takeCourseRepository.save(takeCourse);


        // 성공했을 때
        result.setResult(true);
        result.setMessage("");
        return result;
    }
}
