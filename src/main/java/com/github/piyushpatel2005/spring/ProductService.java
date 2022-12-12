package com.github.piyushpatel2005.spring;

import com.github.piyushpatel2005.spring.annotation.Autowired;
import com.github.piyushpatel2005.spring.annotation.Component;

import java.util.List;

@Component
public class ProductService {
//    hard coded repository everytime the service class is created. This is what we will bring sing Dependency injection.
//    private ProductRepository repo = new ProductRepository();
    @Autowired
    private ProductRepository repo;

    public List<Product> getFinalPrice(List<Product> items) {
        List<Product> list = repo.getPrice(items);

        for(Product product: list) {
            product.setPrice(product.getPrice() * (100 - product.getDiscount()) / 100);
            System.out.println("Price of " + product.getName() + " after " + product.getDiscount() + "% discount is " + product.getPrice());
        }
        return list;
    }
}
