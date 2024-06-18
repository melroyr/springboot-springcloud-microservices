package org.example.storeservice.repository;

import org.example.storeservice.model.Store;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StoreRepository extends JpaRepository<Store,Long> {

    List<Store> findBySkuCodeIn(List<String> skuCode);
}
