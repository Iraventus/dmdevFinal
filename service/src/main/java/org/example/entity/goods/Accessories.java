package org.example.entity.goods;

import jakarta.persistence.*;
import lombok.*;
import org.example.entity.CartGoods;
import org.example.entity.Producer;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "producer")
@EqualsAndHashCode(callSuper = true)
@Entity
@DiscriminatorValue("accessories")
public class Accessories extends Goods {

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    private Producer producer;

    @Builder
    public Accessories(String name, String description, Integer quantity,
                       Integer price, List<CartGoods> cartGoods, Producer producer) {
        super(name, description, quantity, price, cartGoods);
        this.producer = producer;
    }
}
