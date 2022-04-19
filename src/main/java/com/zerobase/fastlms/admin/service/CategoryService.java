package com.zerobase.fastlms.admin.service;

import com.zerobase.fastlms.admin.model.CategoryDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoryService {

    // 관리자에서는 카테고리 사용여부를 제어할 수 있지만
    // 라이언트 쪽에서는 카테고리가 사용되는 것만 나와야하기에 list로 구현
    List<CategoryDto> list();

    boolean add(String categoryName);

    boolean update(CategoryDto param);

    boolean del(long id);
}
