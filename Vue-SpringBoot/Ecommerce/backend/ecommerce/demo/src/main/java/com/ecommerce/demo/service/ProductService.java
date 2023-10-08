package com.ecommerce.demo.service;

import com.ecommerce.demo.dto.ProductDto;
import com.ecommerce.demo.exceptions.ProductNotExistException;
import com.ecommerce.demo.model.Category;
import com.ecommerce.demo.model.Product;
import com.ecommerce.demo.repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    ProductRepo productRepo;


    public void createProduct(ProductDto productDto, Category category) {
        Product product = new Product();
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setImageUrl(productDto.getImageUrl());
        product.setCategory(category);
        productRepo.save(product);
    }

    public ProductDto getProductDto( Product product ){
        ProductDto product_dto = new ProductDto();
        product_dto.setName(product.getName());
        product_dto.setDescription(product.getDescription());
        product_dto.setPrice(product.getPrice());
        product_dto.setImageUrl(product.getImageUrl());
        product_dto.setCategoryId(product.getCategory().getId());
        product_dto.setId(product.getId());
        return  product_dto;
    }
    public List<ProductDto> getAllProducts() {
        List<Product> all_products = productRepo.findAll();
        List<ProductDto> productDtos = new ArrayList<>();

        for(Product product : all_products){
                productDtos.add(getProductDto(product));
        }
        return  productDtos;
    }

    public void updateProduct(ProductDto productDto, Integer productId) throws Exception {
       Optional<Product>  op_product = productRepo.findById(productId);
       if(!op_product.isPresent()){
           throw new Exception("Product Not Found");
       }
        Product product = op_product.get();
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setImageUrl(productDto.getImageUrl());
        productRepo.save(product);
    }

    public Product findById(Integer productId) throws ProductNotExistException {
       Optional<Product> optionalProduct = productRepo.findById(productId);
        if(optionalProduct.isEmpty()){
            throw new ProductNotExistException("product is Invalid: "+productId);
        }
        return optionalProduct.get();
    }
}
