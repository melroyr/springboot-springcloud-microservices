package org.example.orderservice.controller;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.orderservice.dto.request.OrderRequest;
import org.example.orderservice.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "http://localhost:3000")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @CircuitBreaker(name = "store",fallbackMethod = "fallbackMethod") //store adındaki circiut braaker'ı etkinleştirir ,placeOrder metodunu izler yanıt vermez veya hata alırsa fallbackMethod çağırılır
    @TimeLimiter(name="store") //store adındaki timelimiter etkinleştirilir, placeOrder 3 sn (app.prop) içinde tamamlanmazsa hata fıraltır
    @Retry(name = "store") //store adındaki retry etkineştirlir ,  placeOrder methodu başarısız veya hata oluşursa max-attemps(app.prop) kadar tekrar denenir ve  istekler arası wait-duration kadar beklenir
    public CompletableFuture<String> placeOrder(@RequestBody OrderRequest orderRequest) {
        return  CompletableFuture.supplyAsync(()-> orderService.placeOrder(orderRequest)); //placeOrder asenkron çalışır , retry için http istekleri bloke olmadan ayrı threadlerde çalışabilir
    }

    public CompletableFuture<String> fallbackMethod(OrderRequest orderRequest,RuntimeException runtimeException) {
        return CompletableFuture.supplyAsync(()->"Oops!Something went wrong , please order after some time!");
    }

}
