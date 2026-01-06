package com.shopnest.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.shopnest.dto.DashboardData;
import com.shopnest.entities.Product;
import com.shopnest.entities.ProductCategory;
import com.shopnest.repositories.ProductRepository;
import com.shopnest.repositories.ProductCategoryRepositorty;

@Service
public class DashboardService {

    private final ProductRepository productRepo;
    private final ProductCategoryRepositorty categoryRepo;

    public DashboardService(ProductRepository productRepo,
                            ProductCategoryRepositorty categoryRepo) {
        this.productRepo = productRepo;
        this.categoryRepo = categoryRepo;
    }

    public DashboardData getDashboardData() {

        List<Product> products = productRepo.findAll();
        List<ProductCategory> categories = categoryRepo.findAll();

        return new DashboardData(products, categories);
    }
}
