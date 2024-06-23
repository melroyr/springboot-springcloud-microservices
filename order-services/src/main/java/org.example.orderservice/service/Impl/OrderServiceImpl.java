package org.example.orderservice.service.Impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.orderservice.dto.request.OrderLineItemsDto;
import org.example.orderservice.dto.request.OrderRequest;
import org.example.orderservice.dto.response.StoreResponse;
import org.example.orderservice.model.Order;
import org.example.orderservice.model.OrderLineItems;
import org.example.orderservice.repository.OrderRepository;
import org.example.orderservice.service.OrderService;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
//tüm operasyonlar ya tamamen başarılı olur ya da tamamen geri alınır (rollback). Bu sayede veri tutarsızlıkları önlenir.
//sipariş kaydet ,ödeme tamamla , , stok güncelle ; Atomiklik, izolasyon, tutarlılık ve dayanıklılık (ACID) prensipleri
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final WebClient.Builder webClientBuilder;


    @Override
    public boolean placeOrder(OrderRequest orderRequest) {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

        List<OrderLineItems> orderLineItems = orderRequest.getOrderLineItemsDtoList()
                .stream()
                .map(this::mapToDto)
                .toList();

        order.setOrderLineItemsList(orderLineItems);

        List<String> skuCodes = order.getOrderLineItemsList().stream() //siparişteki bütün ürünlerin skuCode'ları listede tutulur
                .map(OrderLineItems::getSkuCode)
                .toList();

        //sipariş tamamlanmadan önce ürünlere ait stok kontrolü yapılır, store servisine istek atılır
        StoreResponse[] storeResponseArray = webClientBuilder.build().get() //client oluşturuldu
                .uri("http://store-service/api/store",
                        uriBuilder -> uriBuilder.queryParam("skuCode", skuCodes).build()) //skuCode listesi parametre gönderilir
                .retrieve() //response alınır
                .bodyToMono(StoreResponse[].class) //response'u StoreResponse tipine çevir
                .block(); //response beklenir ve response geldiğinde bu stockResponseArray'a eşitlenir

        boolean allProductsInStock = Arrays.stream(storeResponseArray).allMatch(StoreResponse::isInStock); //siparişteki tüm ürünlerin stok durumu


        if (Boolean.TRUE.equals(allProductsInStock)) { // eğer stok varsa sipariş tamamlanır
            orderRepository.save(order);
            return true;
        } else {
            return false;
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
