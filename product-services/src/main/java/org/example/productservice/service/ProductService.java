package org.example.productservice.service;


import org.example.productservice.dto.request.ProductRequest;
import org.example.productservice.dto.response.ProductResponse;

import java.util.List;

public interface ProductService {
    void createProduct(ProductRequest productRequest);
    List<ProductResponse> getAllProducts();
}
