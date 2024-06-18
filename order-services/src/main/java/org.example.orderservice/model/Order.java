package org.example.orderservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "t_orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String orderNumber;
    @OneToMany(cascade = CascadeType.ALL) //Order nesnesi üzerinde yapılan değişikliklerin  ilişkili OrderLineItems nesnelerine yansıtılmasını sağlar.
    private List<OrderLineItems> orderLineItemsList; // bir sipariş birden fazla farklı ürün içerebilir
}
