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
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Slf4j
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

    private String[] getNewSaveFiles(String baseLocalPath,String baseUrlPath, String originalFileNames){

        LocalDate now = LocalDate.now();

        // 현재 경로에 연도를 추가한 것
        String[] dirs = {
                String.format("%s/%d/", baseLocalPath, now.getYear()),
                String.format("%s/%d/%02d/", baseLocalPath, now.getYear(),  now.getMonthValue()),
                String.format("%s/%d/%02d/%02d/", baseLocalPath, now.getYear(),  now.getMonthValue(), now.getDayOfMonth())
        };

        // urlDir은 dirs의 포맷과 동일하지만 baseUrlPath의 경로로 적용
        String urlDir = String.format("%s/%d/%02d/%02d/", baseUrlPath, now.getYear(),  now.getMonthValue(), now.getDayOfMonth());

        for (String dir : dirs) {
            File file = new File(dir);
            if(!file.isDirectory()){
                file.mkdir();
            }
        }

        // originalFileNames의 확장자
        String fileExtension = "";
        if(originalFileNames != null){
            // .에 대한 위치를 가져와서
            int dotPots = originalFileNames.lastIndexOf(".");
            if(dotPots > -1){
                fileExtension = originalFileNames.substring(dotPots + 1);
            }
        }

        // 생성이 된 이후에 newfIle까지 리턴
        String uuid = UUID.randomUUID().toString().replace("-", "");
        String newFiienamse = String.format("%s%s", dirs[2], uuid); // 여기까지 파일명
        String newUrlFilename = String.format("%s%s", urlDir, uuid);
        if(fileExtension.length() > 0){
            newFiienamse += "." + fileExtension;
            newUrlFilename += "." + fileExtension;
        }

        String[] returnFimename = {newFiienamse, newUrlFilename};
        return returnFimename;
    }

    @PostMapping(value = {"/admin/course/add", "/admin/course/edit"})
    public String addSubmit(Model model, CourseInput param,
                            HttpServletRequest request,
                            MultipartFile file){

        String saveFiles = "";
        String urlFilenames = "";


        if(file != null){
            String originalFileNames = file.getOriginalFilename();

            String baseLocalPath = "C:\\Users\\user\\IdeaProjects\\Pfastlms\\files";
            String baseUrlPath = "/files";

            String[] arrFilenames = getNewSaveFiles(baseLocalPath,baseUrlPath, originalFileNames);

            saveFiles = arrFilenames[0];
            urlFilenames = arrFilenames[1];


            try {
                File newfile = new File(saveFiles);
                FileCopyUtils.copy(file.getInputStream(), new FileOutputStream(newfile));
            } catch (IOException e) {
                log.info("####에러발생");
                log.info(e.getMessage());
            }
        }

        param.setFilename(saveFiles);
        param.setUrlFilenames(urlFilenames);

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

    @PostMapping("/admin/course/delete")
    public String delete(Model model, CourseInput param, HttpServletRequest request){

        boolean result = courseService.del(param.getIdList());

        return "redirect:/admin/course/list";
    }


}
