package com.zerobase.fastlms.component;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;

import javax.mail.internet.MimeMessage;

@Component
@RequiredArgsConstructor
public class MailComponents {

    private final JavaMailSender javaMailSender;

    public void sendMailTest(){

        SimpleMailMessage message = new SimpleMailMessage();
        // 누구한테 보낼것인가?
        message.setTo("ktw5026@naver.com");
        message.setSubject("인텔리제이에서 보낸 메일");
        message.setText("hihihihi");

        javaMailSender.send(message);

    }

    public boolean sendMail(String mail, String subject, String text){

        // 기본적으로 false값을 갖고 메세지가 성공적으로 보내졌을때만 true 발행
        boolean result = false;

        MimeMessagePreparator msg = new MimeMessagePreparator() {
            @Override
            public void prepare(MimeMessage mimeMessage) throws Exception {
                MimeMessageHelper mh = new MimeMessageHelper(
                        mimeMessage, true, "UTF-8");
                mh.setTo(mail);
                mh.setSubject(subject);
                mh.setText(text, true);
            }
        };
        try {
            javaMailSender.send(msg);
            result = true;
        }catch (Exception e){
            System.out.println("메세지 에러");
        }
        return result;
    }
}
