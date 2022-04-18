package com.zerobase.fastlms.admin;

import com.zerobase.fastlms.admin.dto.MemberDto;
import com.zerobase.fastlms.admin.model.MemberParam;
import com.zerobase.fastlms.member.service.MemberService;
import com.zerobase.fastlms.utils.PageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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
}
