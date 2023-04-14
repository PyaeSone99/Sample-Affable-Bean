package com.example.sampleaffablebean.service;

import com.example.sampleaffablebean.dao.ProductDao;
import com.example.sampleaffablebean.ds.Cart;
import com.example.sampleaffablebean.ds.CartItems;
import com.example.sampleaffablebean.entity.Product;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductDao productDao;

    @Autowired
    private Cart cart;

    public ProductService(ProductDao productDao, Cart cart) {
        this.productDao = productDao;
        this.cart = cart;
    }

    public List<Product> listProducts(int categoryId){
        return productDao.findProductByCategoryId(categoryId);
    }


//    Add To Cart
    public void addToCart(int id){
        Product product = productDao.findById(id).orElseThrow(EntityNotFoundException::new);
        cart.addToCart(new CartItems(
            product.getId(),
            product.getName(),
            product.getPrice(),
                1
        ));
    }

    public int cartSize(){
        return cart.cartSize();
    }

    public Set<CartItems> getCartItems(){
        return cart.getCartItems();
    }

    public void clearCart(){
        cart.clearCart();
    }

    public Set<CartItems> removeFromCart(int id){
        Set<CartItems> cartItems = getCartItems().stream().filter(i -> i.getId()!= id).collect(Collectors.toSet());
        cart.setCartItems(cartItems);
        return cartItems;
    }

}
