package com.zerobase.fastlms.utils;

import com.zerobase.fastlms.course.model.ServiceResult;
import org.springframework.security.crypto.bcrypt.BCrypt;

public class PasswordUtil {

    public static boolean equals(String plaintext, String hashed){

        if(plaintext == null || plaintext.length() < 1){
            return false;
        }

        if (hashed == null || hashed.length() < 1) {
            return false;
        }

        return BCrypt.checkpw(plaintext, hashed);
    }

    // 해쉬함수화
    public static String encPassword(String plaintext){

        if(plaintext == null || plaintext.length() < 1){
            return "";
        }
        return BCrypt.hashpw(plaintext, BCrypt.gensalt());
    }
}
