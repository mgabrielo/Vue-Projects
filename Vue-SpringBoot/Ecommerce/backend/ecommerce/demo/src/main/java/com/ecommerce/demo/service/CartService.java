package com.ecommerce.demo.service;

import com.ecommerce.demo.dto.cart.AddToCartDto;
import com.ecommerce.demo.dto.cart.CartDto;
import com.ecommerce.demo.dto.cart.CartItemDto;
import com.ecommerce.demo.exceptions.CustomException;
import com.ecommerce.demo.model.Cart;
import com.ecommerce.demo.model.Product;
import com.ecommerce.demo.model.User;
import com.ecommerce.demo.repository.CartRepo;
import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    @Autowired
    ProductService productService;
    @Autowired
    private CartRepo cartRepo;

    public void addToCart(AddToCartDto addToCartDto, User user) {
       Product product = productService.findById(addToCartDto.getProductId());
       Cart cart = new Cart();
       cart.setProduct(product);
       cart.setUser(user);
       cart.setQuantity(addToCartDto.getQuantity());
       cart.setCreatedDate(new Date());
       cartRepo.save(cart);
    }

    public CartDto listCartItems(User user) {
       List<Cart> cartList = cartRepo.findAllByUserOrderByCreatedDateDesc(user);
       List<CartItemDto> cartItems = new ArrayList<>();
       double totalCost =0;
       for(Cart cart: cartList){
           CartItemDto cartItemDto =new CartItemDto(cart);
           totalCost +=cartItemDto.getQuantity() * cart.getProduct().getPrice();
           cartItems.add(cartItemDto);
       }
       CartDto cartDto = new CartDto();
       cartDto.setCartItems(cartItems);
       cartDto.setTotalCost(totalCost);
       return cartDto;
    }

    public void deleteCartItem(Integer cartItemId, User user) {
        Optional<Cart> optionalCart= cartRepo.findById(cartItemId);
        if(optionalCart.isEmpty()){
            throw new CustomException("cart item invalid: "+ cartItemId);
        }
        Cart cart = optionalCart.get();
        if(cart.getUser() != user){
            throw new CustomException("Cart Item Doesn't belong to User"+ cartItemId);
        }

         cartRepo.delete(cart);
    }
}
