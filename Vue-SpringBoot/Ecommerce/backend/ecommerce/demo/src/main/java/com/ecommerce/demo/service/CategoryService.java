package com.ecommerce.demo.service;

import com.ecommerce.demo.model.Category;
import com.ecommerce.demo.repository.CategoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    CategoryRepo categoryRepo;

    public void createCategory(Category category){
        categoryRepo.save(category);
    }

    public List<Category> listCategory(){
        return  categoryRepo.findAll();
    }
    public void editCategory(int categoryId, Category update_category){
//        return  categoryRepo.findAll();
        Category category = categoryRepo.getById(categoryId);
        category.setCategoryName(update_category.getCategoryName());
        category.setDescription(update_category.getDescription());
        category.setImageUrl(update_category.getImageUrl());
        categoryRepo.save(category);
    }

    public boolean findById(int categoryId) {
        return  categoryRepo.findById(categoryId).isPresent();
    }
}
