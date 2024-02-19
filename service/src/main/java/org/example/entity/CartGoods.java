package org.example.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.*;
import org.example.entity.goods.Goods;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Builder
@Entity
public class CartGoods extends BaseEntity<Long> {

    @ManyToOne
    private Goods goods;
    @ManyToOne(optional = false)
    private Cart cart;
    private Instant createdAt;
    @OneToOne(mappedBy = "cartGoods", cascade = CascadeType.ALL)
    private Order order;
    private Integer totalGoods;
    private Integer totalPrice;

    public void setCart(Cart cart) {
        this.cart = cart;
        this.cart.getCartGoods().add(this);
    }

    public void setGoods(Goods goods) {
        this.goods = goods;
        this.goods.getCartGoods().add(this);
    }
}
