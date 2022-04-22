package com.zerobase.fastlms.admin.service;

import com.zerobase.fastlms.admin.entity.Category;
import com.zerobase.fastlms.admin.dto.CategoryDto;
import com.zerobase.fastlms.admin.mapper.CategoryMapper;
import com.zerobase.fastlms.admin.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService{

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    private Sort getSortBySortValueDesc(){
        return Sort.by(Sort.Direction.DESC, "sortValue");
    }


    @Override
    public List<CategoryDto> list() {

        List<Category> optionalCategories = categoryRepository.findAll(getSortBySortValueDesc());

        return CategoryDto.bof(optionalCategories);
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

        Optional<Category> optionalCategory = categoryRepository.findById(param.getId());
        if(optionalCategory.isPresent()){

            Category category = optionalCategory.get();
            category.setCategoryName(param.getCategoryName());
            category.setSortValue(param.getSortValue());
            category.setUsingYn(param.isUsingYn());
            categoryRepository.save(category);

        }

        return true;
    }


    @Override
    public boolean del(long id) {

        categoryRepository.deleteById(id);

        return true;
    }

    @Override
    public List<CategoryDto> frontList(CategoryDto param) {

        return categoryMapper.select(param);

    }
}
