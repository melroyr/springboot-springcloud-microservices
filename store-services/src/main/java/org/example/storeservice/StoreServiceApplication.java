package org.example.storeservice;

import org.example.storeservice.model.Store;
import org.example.storeservice.repository.StoreRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class StoreServiceApplication {

    public static void main(String[] args) {SpringApplication.run(StoreServiceApplication.class, args);}

    @Bean
    public CommandLineRunner loadData(StoreRepository storeRepository) { //Spring Boot başlatıldığında çalıştırılacak bir komut dosyası sağlar, başlangıç verileri kaydeder
        return args -> {
            Store store=new Store();
            store.setSkuCode("iphone_13");
            store.setQuantity(100);

            Store store1=new Store();
            store1.setSkuCode("iphone_13_red");
            store1.setQuantity(0);
            storeRepository.save(store);
            storeRepository.save(store1);
        };
    }

}
