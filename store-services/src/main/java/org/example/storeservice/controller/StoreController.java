package org.example.storeservice.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.storeservice.dto.response.StoreResponse;
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

    //http://localhost8082/api/store/iphone_13,iphone_13_red ---> path variable
    //http://localhost8082/api/store?skuCode=iphone_13&skuCode=iphone_13_red -->request param
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<StoreResponse> isInStock(@RequestParam List<String> skuCode) {
        return storeService.isInStock(skuCode);
    }

}
