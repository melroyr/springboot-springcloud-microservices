package org.example.productservice.service.Impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.productservice.dto.request.ProductRequest;
import org.example.productservice.dto.response.ProductResponse;
import org.example.productservice.model.Product;
import org.example.productservice.repository.ProductRepository;
import org.example.productservice.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor // tanımlanan repo'ları otomatik initialize eder , constructor'a gerek kalmaz
@Slf4j // log.info yazabilmek için gerekli anatasyon
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public void createProduct(ProductRequest productRequest) {
        Product product = Product.builder()
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .build();

        //builder , nesne ile ilgili gereksinimleri sağlar

        productRepository.save(product);
        log.info("Product {} is created", product.getId());
    }

    @Override
    public List<ProductResponse> getAllProducts() {
        List<Product> products=productRepository.findAll();
        return products.stream().map(this::mapToProductResponse).toList();
    }

    private ProductResponse mapToProductResponse(Product product) { // Product'ı direkt döndürmek yerine özelliklerine göre parçalayarak döndürür
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .build();

    }
}
