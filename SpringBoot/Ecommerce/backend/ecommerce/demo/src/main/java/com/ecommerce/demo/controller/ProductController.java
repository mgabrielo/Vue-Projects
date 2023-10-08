package com.ecommerce.demo.controller;

import com.ecommerce.demo.common.ApiResponse;
import com.ecommerce.demo.dto.ProductDto;
import com.ecommerce.demo.model.Category;
import com.ecommerce.demo.model.Product;
import com.ecommerce.demo.repository.CategoryRepo;
import com.ecommerce.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/product")
public class ProductController {

    @Autowired
    ProductService productService;

    @Autowired
    CategoryRepo categoryRepo;

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> createProduct(@RequestBody ProductDto productDto){
        final Optional<Category> op_category= categoryRepo.findById(productDto.getCategoryId());
        if(!op_category.isPresent()){
            return new ResponseEntity<>(
                    new ApiResponse(false, "Category Not Found"),
                    HttpStatus.NOT_FOUND
            );
        }
        productService.createProduct(productDto, op_category.get());
        return new ResponseEntity<>(
                new ApiResponse(true, "Product Added"),
                HttpStatus.CREATED
        );
    }

    @GetMapping("/")
    public ResponseEntity<List<ProductDto>> getProducts(){
        List<ProductDto> products = productService.getAllProducts();
        return new ResponseEntity<>(products,HttpStatus.OK);
    }

    @PostMapping("/update/{productId}")
    public ResponseEntity<ApiResponse> updateProduct(
            @PathVariable("productId") Integer productId,
            @RequestBody ProductDto productDto
    ) throws Exception {
        final Optional<Category> op_category= categoryRepo.findById(productDto.getCategoryId());
        if(!op_category.isPresent()){
            return new ResponseEntity<>(
                    new ApiResponse(false, "Category Not Found"),
                    HttpStatus.NOT_FOUND
            );
        }
        productService.updateProduct(productDto, productId);
        return new ResponseEntity<>(
                new ApiResponse(true, "Product Updated"),
                HttpStatus.OK
        );
    }

}
