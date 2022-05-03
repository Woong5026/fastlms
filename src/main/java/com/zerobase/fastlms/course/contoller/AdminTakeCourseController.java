package com.zerobase.fastlms.course.contoller;


import com.zerobase.fastlms.admin.service.CategoryService;
import com.zerobase.fastlms.course.dto.TakeCourseDto;
import com.zerobase.fastlms.course.model.*;
import com.zerobase.fastlms.course.service.CourseService;
import com.zerobase.fastlms.course.service.TakeCourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class AdminTakeCourseController extends BaseController{

    private final TakeCourseService takeCourseService;

    @GetMapping("/admin/takecourse/list")
    public String list(Model model, TakeCourseParam param){

        param.init();

        List<TakeCourseDto> courseList = takeCourseService.list(param);


        long totalCount = 0;
        if(courseList != null && courseList.size() > 0){
            totalCount = courseList.get(0).getTotalCount();
        }

        String queryString = param.getQueryString();
        String pagerHtml = getPapaerHtml(totalCount, param.getPageSize(), param.getPageIndex(), queryString);

        model.addAttribute("list", courseList);
        model.addAttribute("pager", pagerHtml);
        model.addAttribute("totalCount", totalCount);

        return "admin/takecourse/list";
    }

    @PostMapping("/admin/takecourse/status")
    public String status(Model model, TakeCourseParam param){

        ServiceResult result = takeCourseService.updateStatus(param.getId(), param.getStatus());

        if(!result.isResult()){
            model.addAttribute("message", result.getMessage());
            return "common/error";
        }



        return "redirect:/admin/takecourse/list";
    }

}
