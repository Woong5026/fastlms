package com.zerobase.fastlms.course.contoller;


import com.zerobase.fastlms.admin.dto.MemberDto;
import com.zerobase.fastlms.admin.model.MemberParam;
import com.zerobase.fastlms.admin.service.CategoryService;
import com.zerobase.fastlms.course.model.CourseDto;
import com.zerobase.fastlms.course.model.CourseInput;
import com.zerobase.fastlms.course.model.CourseParam;
import com.zerobase.fastlms.course.service.CourseService;
import com.zerobase.fastlms.utils.PageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class AdminCourseController extends BaseController{

    private final CourseService courseService;
    private final CategoryService categoryService;

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

    @GetMapping(value = {"/admin/course/add", "/admin/course/edit"})
    public String add(Model model, HttpServletRequest request, CourseInput param){

        // 카테고리 항목을 추가할 것이니 카테고리 정보를 내려줘야함
        model.addAttribute("category", categoryService.list());


        // add인지 edit인지 구분하는 메서드
        boolean editMode = request.getRequestURI().contains("/edit");
        CourseDto detail = new CourseDto();

        if(editMode){
            long id = param.getId();
            CourseDto existCourse = courseService.getById(id);

            // 수정할 데이터가 없으면
            if(existCourse == null){
                // 에러처리
                model.addAttribute("message", "강좌정보가 존재하지 않습니다");
                return "common/error";
            }
            // null 값이 아닐때 existCourse를 넣어준다
            detail = existCourse;
        }

        // 강좌 등록/ 수정을 식별해야 하기때문에 editMode로 구분해준다다
       model.addAttribute("editMode", editMode);
       model.addAttribute("detail", detail);
        return "admin/course/add";
    }

    @PostMapping(value = {"/admin/course/add", "/admin/course/edit"})
    public String addSubmit(Model model, CourseInput param, HttpServletRequest request){

        boolean editMode = request.getRequestURI().contains("/edit");

        if(editMode){
            long id = param.getId();
            CourseDto existCourse = courseService.getById(id);
            // 수정할 데이터가 없으면
            if(existCourse == null){
                // 에러처리
                model.addAttribute("message", "강좌정보가 존재하지 않습니다");
                return "common/error";
            }
            // edit모드일때는 수정
            boolean result = courseService.set(param);

        }else {
            // edit모드가 아닐때는 등록
            boolean result = courseService.add(param);
        }

        return "redirect:/admin/course/list";
    }


}
