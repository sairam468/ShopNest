package com.shopnest.dto;

import java.util.List;

import com.shopnest.entities.Product;
import com.shopnest.entities.ProductCategory;

public class DashboardData {

    private List<Product> products;
    private List<ProductCategory> categories;

    public DashboardData(List<Product> products, List<ProductCategory> categories) {
        this.products = products;
        this.categories = categories;
    }

    public List<Product> getProducts() {
        return products;
    }

    public List<ProductCategory> getCategories() {
        return categories;
    }
}
