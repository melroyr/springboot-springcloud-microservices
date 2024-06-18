package org.example.orderservice.service.Impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.orderservice.dto.request.OrderLineItemsDto;
import org.example.orderservice.dto.request.OrderRequest;
import org.example.orderservice.model.Order;
import org.example.orderservice.model.OrderLineItems;
import org.example.orderservice.repository.OrderRepository;
import org.example.orderservice.service.OrderService;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
//tüm operasyonlar ya tamamen başarılı olur ya da tamamen geri alınır (rollback). Bu sayede veri tutarsızlıkları önlenir.
//sipariş kaydet ,ödeme tamamla , , stok güncelle ; Atomiklik, izolasyon, tutarlılık ve dayanıklılık (ACID) prensipleri
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final WebClient webClient;


    @Override
    public void placeOrder(OrderRequest orderRequest) {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

        List<OrderLineItems> orderLineItems = orderRequest.getOrderLineItemsDtoList()
                .stream()
                .map(this::mapToDto)
                .toList();

        order.setOrderLineItemsList(orderLineItems);

        Boolean result =webClient.get() //client oluşturuldu
                .uri("http://localhost:8080/api/inventory")//inventory servisine istek atılıyor
                .retrieve() //response alınır
                .bodyToMono(Boolean.class) //bodyToMono ile dönüş tipi belirlenir
                .block(); //block ile response beklenir ve sonuç döner response beklenilmesi lazım çünkü stok kontrolü yapılacak



        if(result){
            orderRepository.save(order);

        }
        else {
            throw new IllegalArgumentException("Product is out of stock");
        }


    }

    private OrderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto) {
        OrderLineItems orderLineItems = new OrderLineItems();
        orderLineItems.setPrice(orderLineItemsDto.getPrice());
        orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
        orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
        return orderLineItems;
    }
}
