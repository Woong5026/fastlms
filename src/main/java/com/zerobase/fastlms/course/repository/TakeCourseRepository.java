package com.zerobase.fastlms.course.repository;

import com.zerobase.fastlms.course.entity.Course;
import com.zerobase.fastlms.course.entity.TakeCourse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface TakeCourseRepository extends JpaRepository<TakeCourse, Long> {

    // 값이 있는지 없는지 갯수로 판단
    long countByCourseIdAndUserIdAndStatusIn(long courseId, String userId, Collection<String> statusList);

}
