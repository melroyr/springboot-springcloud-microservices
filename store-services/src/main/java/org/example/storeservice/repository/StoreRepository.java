package org.example.storeservice.repository;

import org.example.storeservice.model.Store;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StoreRepository extends JpaRepository<Store,Long> {

    Optional<Store> findBySkuCode(String skuCode);
}
