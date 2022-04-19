package com.zerobase.fastlms.course.contoller;

import com.zerobase.fastlms.utils.PageUtil;

public class BaseController {

    public String getPapaerHtml(long totalCount, long pageSize, long pageIndex, String queryString){
        PageUtil pageUtil = new PageUtil(totalCount, pageSize, pageIndex, queryString);

        return pageUtil.pager();
    }
}
