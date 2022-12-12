package com.github.piyushpatel2005.spring;

import com.github.piyushpatel2005.spring.annotation.Component;

import java.util.List;

@Component
public class ProductRepository {

    public List<Product> getPrice(List<Product> items) {
        for(Product product: items) {
            Double price = (double) Math.round(30 * Math.random());

            System.out.println("Original Price of " + product.getName() + " is " + price + "$.");
            product.setPrice(price);
        }
        return items;
    }
}
