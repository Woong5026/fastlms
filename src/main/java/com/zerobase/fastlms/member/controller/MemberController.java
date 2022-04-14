package com.zerobase.fastlms.member.controller;

import com.zerobase.fastlms.member.entity.Member;
import com.zerobase.fastlms.member.model.MemberRequestDto;
import com.zerobase.fastlms.member.model.ResponseDto;
import com.zerobase.fastlms.member.repository.MemberRepository;
import com.zerobase.fastlms.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberRepository memberRepository;
    private final MemberService memberService;
    private final EntityManager em;

    @GetMapping("/member/register")
    public String register(){

        return "member/register";
    }

    @PostMapping("/member/register")
    public String registerPost(Model model,MemberRequestDto requestDto){

        boolean result = memberService.register(requestDto);
        model.addAttribute("result", result);

        return "member/register_complate";
    }

    @GetMapping("/member/email-auth")
    public String emailAuth(Model model, HttpServletRequest request){

        // 쿼리스트링 주소에 파라멘터 값으로 넘기는 값의 이름이 id
        String uuid = request.getParameter("id");

        boolean result = memberService.emailAuth(uuid);
        model.addAttribute("result", result);

        return "member/email_auth";
    }
}
