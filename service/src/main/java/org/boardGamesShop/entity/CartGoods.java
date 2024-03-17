package org.boardGamesShop.entity;

import jakarta.persistence.*;
import lombok.*;
import org.boardGamesShop.entity.goods.Goods;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"goods", "cart", "order"}, callSuper = false)
@ToString(exclude = {"goods", "cart", "order"})
@Builder
@Entity
public class CartGoods extends BaseEntity<Long> {

    @ManyToOne(fetch = FetchType.LAZY)
    private Goods goods;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Cart cart;
    private Instant createdAt;
    @OneToOne(mappedBy = "cartGoods", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
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
