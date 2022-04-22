package com.zerobase.fastlms.admin.dto;

import com.zerobase.fastlms.admin.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CategoryDto {

    Long id;
    String categoryName;
    int sortValue;
    boolean usingYn;

    // 추가 칼럼
    int courseCount;

    public static List<CategoryDto> bof(List<Category> categories){

        if(categories != null){
            List<CategoryDto> categoryList = new ArrayList<>();
            for (Category c : categories) {
                categoryList.add(of(c));
            }
            return categoryList;
        }
        return null;
    }


    // 단건에 대한 of
    public static CategoryDto of(Category category){
        return CategoryDto.builder()
                .id(category.getId())
                .categoryName(category.getCategoryName())
                .sortValue(category.getSortValue())
                .usingYn(category.isUsingYn())
                .build();
    }
}
