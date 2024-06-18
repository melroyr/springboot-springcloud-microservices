package org.example.storeservice.service.Impl;

import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.storeservice.repository.StoreRepository;
import org.example.storeservice.service.StoreService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService {

    private final StoreRepository storeRepository;

    @Override
    @Transactional(readOnly = true) //okuma yapar, herhangi bir şekilde veritabanı değişikliklerine neden olmaz
    public boolean isInStock(String skuCode) {
        return storeRepository.findBySkuCode(skuCode).isPresent();
    }
}
