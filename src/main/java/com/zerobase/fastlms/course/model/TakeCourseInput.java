package com.zerobase.fastlms.course.model;

import com.zerobase.fastlms.admin.model.CommonParam;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TakeCourseInput {

    // courseId
    long courseId;
    String userId;
}
