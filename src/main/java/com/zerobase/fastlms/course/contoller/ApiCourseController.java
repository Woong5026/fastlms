package com.zerobase.fastlms.course.contoller;

import com.zerobase.fastlms.admin.dto.CategoryDto;
import com.zerobase.fastlms.admin.service.CategoryService;
import com.zerobase.fastlms.common.model.ResponseResult;
import com.zerobase.fastlms.course.model.CourseDto;
import com.zerobase.fastlms.course.model.CourseParam;
import com.zerobase.fastlms.course.model.ServiceResult;
import com.zerobase.fastlms.course.model.TakeCourseInput;
import com.zerobase.fastlms.course.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class ApiCourseController extends BaseController{

    private final CourseService courseService;
    private final CategoryService categoryService;

    @PostMapping("/api/course/req.api")
    public ResponseEntity<?> courseRequest(Model model,
                                           @RequestBody TakeCourseInput param,
                                           Principal principal){

        // 유저정보 가져오는 코드
        param.setUserId(principal.getName());

        ServiceResult result = courseService.request(param);

        if (!result.isResult()){

            ResponseResult responseResult = new ResponseResult(false, result.getMessage());

            return ResponseEntity.ok().body(responseResult);
        }

        // 내리는 값자체를 param이 아닌 response 값 자체를 내릴것
        ResponseResult responseResult = new ResponseResult(true);
        return ResponseEntity.ok().body(responseResult);
    }
}
