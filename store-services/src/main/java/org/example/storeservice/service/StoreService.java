package org.example.storeservice.service;

import org.example.storeservice.dto.response.StoreResponse;

import java.util.List;

public interface StoreService {
    List<StoreResponse> isInStock(List<String> skuCode);
}
