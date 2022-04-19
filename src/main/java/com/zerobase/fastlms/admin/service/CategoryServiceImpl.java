package com.zerobase.fastlms.admin.service;

import com.zerobase.fastlms.admin.entity.Category;
import com.zerobase.fastlms.admin.model.CategoryDto;
import com.zerobase.fastlms.admin.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService{

    private final CategoryRepository categoryRepository;


    @Override
    public List<CategoryDto> list() {

        List<Category> categoryList = categoryRepository.findAll();

        return CategoryDto.bof(categoryList);
    }

    @Override
    public boolean add(String categoryName) {

        Category category = Category.builder()
                .categoryName(categoryName)
                .usingYn(true)
                .sortValue(0)
                .build();

        categoryRepository.save(category);

        return true;
    }

    @Override
    public boolean update(CategoryDto param) {
        return false;
    }


    @Override
    public boolean del(long id) {
        return false;
    }
}
