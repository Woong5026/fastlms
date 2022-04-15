package com.zerobase.fastlms.member.controller;

import com.zerobase.fastlms.member.entity.Member;
import com.zerobase.fastlms.member.model.MemberRequestDto;
import com.zerobase.fastlms.member.model.ResetPasswordRequestDto;
import com.zerobase.fastlms.member.model.ResponseDto;
import com.zerobase.fastlms.member.repository.MemberRepository;
import com.zerobase.fastlms.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;


    @RequestMapping("/member/login")
    public String login(){
        return "/member/login";
    }

    @GetMapping("/member/find/password")
    public String findPassword(){
        return "member/find_password";
    }

    @PostMapping("/member/find/password")
    public String findPasswordSubmit(Model model,ResetPasswordRequestDto requestDto){
        boolean result = false;
        try {
            result = memberService.sendResetPassword(requestDto);
        }catch (Exception e){

        }

        model.addAttribute("result", result);

        // 성공 시 메인페이로 이동동
        return "member/find_password_result";
    }

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

    @GetMapping("/member/info")
    public String memberInfo(){
        return "member/info";
    }

    @GetMapping("/member/reset/password")
    public String resetPassword(Model model, HttpServletRequest request){

        String uuid = request.getParameter("id");

        // 회원이 정말 uuid를 갖고있는 유효한 회원인지 체크하는 로직
        boolean result = memberService.checkResetPassword(uuid);

        // result를 그대로 내리면 패스워드 초기화시
        model.addAttribute("result", result);

        return "member/reset_password";
    }

    // 패스워드 초기화 메서드
    @PostMapping("/member/reset/password")
    public String resetPasswordSubmit(Model model, ResetPasswordRequestDto requestDto){
        // 넘어온 값들을 받아서 초기화

        boolean result = false;

        try {
            result = memberService.resetPassword(requestDto.getId(), requestDto.getPassword());
        }catch (Exception e){
        }

        // Get방식에서 id값이 존재하므로 ResetPasswordRequestDto의
        // id 값과 Get방식의 uuid값이 자동으로 매칭된다
        model.addAttribute("result", result);

        return "member/reset_password_result";
    }
}
