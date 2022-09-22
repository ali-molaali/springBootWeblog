package com.tosan.blog.moudules.category.service;

import com.tosan.blog.moudules.category.model.Category;
import com.tosan.blog.moudules.category.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    private CategoryRepository categoryRepository;
    @Autowired
    CategoryService(CategoryRepository categoryRepository){
        this.categoryRepository = categoryRepository;
    }

    public Category CategoryRegister(Category category){
        return categoryRepository.save(category);
    }

    public List<Category> findAll(){
        return categoryRepository.findAll();
    }

    public Object findById(Long id) {
        return categoryRepository.findById(id);
    }

    public void deleteById(Long id) {

        categoryRepository.deleteById(id);
    }
}
