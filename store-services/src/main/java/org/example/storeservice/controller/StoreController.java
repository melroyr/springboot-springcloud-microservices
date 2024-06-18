package org.example.storeservice.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.storeservice.service.StoreService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/store")
@RequiredArgsConstructor
@Slf4j
public class StoreController {

    private final StoreService storeService;

    @GetMapping("/{sku-code}")
    @ResponseStatus(HttpStatus.OK)
    public boolean isInStock(@PathVariable("sku-code") String skuCode) {
      return storeService.isInStock(skuCode);
    }

}
