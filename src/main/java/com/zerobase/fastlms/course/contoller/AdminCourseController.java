package com.zerobase.fastlms.course.contoller;


import com.zerobase.fastlms.admin.dto.MemberDto;
import com.zerobase.fastlms.admin.model.MemberParam;
import com.zerobase.fastlms.course.model.CourseDto;
import com.zerobase.fastlms.course.model.CourseParam;
import com.zerobase.fastlms.course.service.CourseService;
import com.zerobase.fastlms.utils.PageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class AdminCourseController extends BaseController{

    private final CourseService courseService;

    @GetMapping("/admin/course/list")
    public String list(Model model, CourseParam param){

        param.init();

        List<CourseDto> courseList = courseService.list(param);


        long totalCount = 0;
        if(courseList != null && courseList.size() > 0){
            totalCount = courseList.get(0).getTotalCount();
        }

        String queryString = param.getQueryString();
        String pagerHtml = getPapaerHtml(totalCount, param.getPageSize(), param.getPageIndex(), queryString);

        model.addAttribute("list", courseList);
        model.addAttribute("pager", pagerHtml);
        model.addAttribute("totalCount", totalCount);

        return "admin/course/list";
    }

    @GetMapping("/admin/course/add")
    public String add(Model model){


        return "admin/course/add";
    }

    @PostMapping("/admin/course/add")
    public String addSubmit(Model model, CourseDto param){

        boolean result = courseService.add(param);

        return "redirect:/admin/course/list";
    }


}
