package com.zerobase.fastlms.admin.controller;

import com.zerobase.fastlms.admin.dto.MemberDto;
import com.zerobase.fastlms.admin.model.MemberParam;
import com.zerobase.fastlms.admin.model.MemberPasswordDto;
import com.zerobase.fastlms.admin.model.MemberStatusDto;
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
public class AdminMemberController {

    private final MemberService memberService;

    @GetMapping("/admin/member/list")
    public String list(Model model, MemberParam param){

        param.init();

        List<MemberDto> members = memberService.list(param);
        model.addAttribute("list", members);

        long totalCount = 0;
        if(members != null && members.size() > 0){
            totalCount = members.get(0).getTotalCount();
        }

        String queryString = param.getQueryString();

        PageUtil pageUtil = new PageUtil(totalCount, param.getPageSize(), param.getPageIndex(), queryString);
        model.addAttribute("pager", pageUtil.pager());

        model.addAttribute("totalCount", totalCount);


        return "admin/member/list";
    }
    @GetMapping("/admin/member/detail")
    public String detail(Model model, MemberParam param){

        param.init();

        MemberDto member = memberService.detail(param.getUserId());
        model.addAttribute("member", member);


        return "admin/member/detail";
    }

    @PostMapping("/admin/member/status")
    public String status(Model model, MemberStatusDto parameter){


       boolean result =  memberService.updateStatus(parameter.getUserId(), parameter.getUserStatus());

       return "redirect:/admin/member/detail?userId=" + parameter.getUserId();

    }

    @PostMapping("/admin/member/password")
    public String password(Model model, MemberPasswordDto parameter){


        boolean result =  memberService.updatePassword(parameter.getUserId(), parameter.getPassword());

        return "redirect:/admin/member/detail?userId=" + parameter.getUserId();

    }
}
