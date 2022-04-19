package com.zerobase.fastlms.configuration;


import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UserAuthenticationFailureHandeler extends SimpleUrlAuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

        String msg = "로그인에 실패!!";

        if (exception instanceof InternalAuthenticationServiceException){
            msg = exception.getMessage();
        }


        // 에러가 발생했으니 request에 에러 메세지를 넣을 거임
        setUseForward(true);
        setDefaultFailureUrl("/member/login?error=true");
        request.setAttribute("errorMessage", msg);


        super.onAuthenticationFailure(request, response, exception);
    }
}
