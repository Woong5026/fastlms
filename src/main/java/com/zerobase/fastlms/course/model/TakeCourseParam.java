package com.zerobase.fastlms.course.model;

import com.zerobase.fastlms.admin.model.CommonParam;
import lombok.Data;

@Data
public class TakeCourseParam extends CommonParam {
    long id;
    String status;
    String userId;

    // 검색을 위한 아이디
    long searchCourseId;

}
