package com.ecommerce.demo.controller;

import com.ecommerce.demo.common.ApiResponse;
import com.ecommerce.demo.model.Category;
import com.ecommerce.demo.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createCategory(@RequestBody Category category){
        categoryService.createCategory(category);
        return new ResponseEntity<>(
                new ApiResponse(true, "Category Created"),
                HttpStatus.CREATED
        );
    }

    @GetMapping("/list")
    public List<Category> listCategory(){
        return categoryService.listCategory();
    }
    
    @PostMapping("/update/{categoryId}")
    public ResponseEntity<ApiResponse> updateCategory(@PathVariable("categoryId") int categoryId,@RequestBody Category category){
        System.out.println("categoryId: "+ categoryId);
        if(!categoryService.findById(categoryId)){
            return new ResponseEntity<>(
                    new ApiResponse(false, "Category Not Found"),
                    HttpStatus.NOT_FOUND
            );
        }
        categoryService.editCategory(categoryId, category);
        return new ResponseEntity<>(
                new ApiResponse(true, "Category Updated"),
                HttpStatus.OK
        );
    }
}
