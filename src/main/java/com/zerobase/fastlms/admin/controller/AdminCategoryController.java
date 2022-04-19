package com.zerobase.fastlms.admin.controller;

import com.zerobase.fastlms.admin.dto.MemberDto;
import com.zerobase.fastlms.admin.model.CategoryDto;
import com.zerobase.fastlms.admin.model.MemberParam;
import com.zerobase.fastlms.admin.model.MemberPasswordDto;
import com.zerobase.fastlms.admin.model.MemberStatusDto;
import com.zerobase.fastlms.admin.service.CategoryService;
import com.zerobase.fastlms.member.service.MemberService;
import com.zerobase.fastlms.utils.PageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class AdminCategoryController {

    private final CategoryService categoryService;

    @GetMapping("/admin/category/list")
    public String list(Model model, MemberParam param){

        List<CategoryDto> list = categoryService.list();
        model.addAttribute("list",list);

        return "admin/category/list";
    }

    @PostMapping ("/admin/category/add")
    public String add(Model model, CategoryDto param){

        boolean result = categoryService.add(param.getCategoryName());

        return "redirect:/admin/category/list";
    }

}
