package com.example.sampleaffablebean.controller;

import com.example.sampleaffablebean.ds.CartItems;
import com.example.sampleaffablebean.entity.PurchaseForm;
import com.example.sampleaffablebean.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class ProductController {
    @Autowired
    private ProductService  productService;

    @GetMapping({"/","/home"})
    public String home(){
        return "home";
    }

    @GetMapping("/products")
    public String listProductByCategory(@RequestParam("cid") int cid, Model model){
        model.addAttribute("products",productService.listProducts(cid));
        return "products";
    }

//  Add To Cart Section
    @GetMapping("/cart/add-cart")
    public String addToCart(@RequestParam("id") int id){
        productService.addToCart(id);
        return "redirect:/products?cid=1";
    }

    @ModelAttribute("cartSize")
    public int cartSize(){
        CartItems cartItems = new CartItems();
        return productService.cartSize() + cartItems.getQuanLinkedList().size();
    }

    @GetMapping("/view-cart")
    public String viewCart(Model model){
        model.addAttribute("CartItems",new CartItems());
        model.addAttribute("getCartItems",productService.getCartItems());
        return "cart-view";
    }

    @ModelAttribute("totalPrice")
    public double totalPrice(){
        return productService.getCartItems().stream().map(i -> i.getPrice() * i.getQuantity()).mapToDouble(i -> i).sum();
    }

    private boolean changeButton;

    @ModelAttribute("changeButton")
    public boolean changeButton(){
        return changeButton;
    }

    @GetMapping("/clickEditButton")
    public String clickEditButton(Model model){
        Set<CartItems> cartItems = productService.getCartItems().stream()
                .map(i -> {
                    i.setRender(true);
                    return i;
                }).collect(Collectors.toSet());
        model.addAttribute("CartItems",new CartItems());
        model.addAttribute("getCartItems",cartItems);
        model.addAttribute("changeButton",true);
        return "cart-view";
    }

    @PostMapping("/saveEditQuantity")
    public String saveEditQuantity(CartItems cartItems,Model model){
        model.addAttribute("getCartItems",productService.getCartItems());
        int i=0;
        for (CartItems cartItems1 : productService.getCartItems()){
                cartItems1.setQuantity(cartItems.getQuanLinkedList().get(i));
                cartItems1.setRender(false);
                i++;
        }

        return "redirect:/view-cart";
    }

    @GetMapping("/view-cart/clear")
    public String clearCart(){
        productService.clearCart();
        return "redirect:/view-cart";
    }

    @GetMapping("/view-cart/remove")
    public String removeItem(@RequestParam("id") int id){
        productService.removeFromCart(id);
        return "redirect:/view-cart";
    }

//    Check out button onClick Sections

    @GetMapping("/checkOut/view")
    public String clickCheckOut(Model model){
        model.addAttribute("totalPrice",totalPrice());
        model.addAttribute("allTotal",totalPrice()+2);
        model.addAttribute("purchaseForm",new PurchaseForm());

        return "checkOut-view";
    }

    @PostMapping("/checkOut/confirm")
    public String confirmPurchase(@Valid PurchaseForm purchaseForm, BindingResult result,Model model){
        if (result.hasErrors()){
            return "checkOut-view";
        }
        purchaseForm.getLocalDate();

        model.addAttribute("cartItems",productService.getCartItems());
        model.addAttribute("confirmPurchase",purchaseForm);
        model.addAttribute("totalPrice",totalPrice());
        model.addAttribute("allTotal",totalPrice()+2);
        return "confirm-view";
    }

}






















