package com.github.piyushpatel2005.spring;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class Application {
    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        ApplicationContext context = new ApplicationContext(AppConfig.class);
        ProductService service = context.getBean(ProductService.class);
        List<Product> items = new ArrayList<>();
        items.add(new Product("Apple TV", 40));
        items.add(new Product("XBox", 30));
        items.add(new Product("AirBnb", 20));

        List<Product> finalPrice = service.getFinalPrice(items);
        for (Product product: finalPrice) {
            System.out.println(product.getName() + " at " + product.getDiscount() + "% discount: " + product.getPrice());
        }

    }
}
