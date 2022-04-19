package com.zerobase.fastlms.admin.model;

import lombok.Data;

@Data
public class CommonParam {

    int pageIndex;
    int pageSize;

    String searchType;
    String searchValue;


    // pageIndex = 1 , 0 // pageIndex = 2 ,10 ...
    public long getPageStart(){
        init();

        return (pageIndex - 1) * pageSize;
    }

    public long getPageEnd(){
        init();

        return pageSize;
    }

    public void init(){
        if (pageIndex < 1){
            pageIndex = 1;
        }

        if (pageSize < 10){
            pageSize = 10;
        }
    }

    public String getQueryString() {
        init();

        StringBuilder builder = new StringBuilder();

        // 검색결과가 있다는 조건
        if (searchType != null && searchType.length() > 0){
            builder.append(String.format("searchType=%s",searchType));
        }


        if (searchValue != null && searchValue.length() > 0){
            if(builder.length() > 0){
                builder.append("&");
            }
            builder.append(String.format("searchValue=%s",searchValue));
        }

        return builder.toString();

    }
}
