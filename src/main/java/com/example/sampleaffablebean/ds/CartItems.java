package com.example.sampleaffablebean.ds;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.LinkedList;
import java.util.Objects;

@Getter
@Setter
@ToString
public class CartItems {
    private int id;
    private String name;
    private Double price;
    private int quantity;
    private boolean render;
    private LinkedList<Integer> quanLinkedList = new LinkedList<>();

    public CartItems() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartItems cartItems = (CartItems) o;
        return id == cartItems.id && name.equals(cartItems.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    public CartItems(int id, String name, Double price, int quantity) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }
}
