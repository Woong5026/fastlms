package com.zerobase.fastlms.member.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class ResponseDto {

    private String status;
    private String message;

    public ResponseDto(String status, String message){
        this.status = status;
        this.message = message;
    }
}
