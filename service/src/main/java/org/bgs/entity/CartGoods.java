package org.bgs.entity;

import jakarta.persistence.*;
import lombok.*;
import org.bgs.entity.goods.Goods;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"goods", "cart"}, callSuper = false)
@ToString(exclude = {"goods", "cart"})
@Builder
@Entity
public class CartGoods extends BaseEntity<Long> {

    @ManyToOne(fetch = FetchType.EAGER)
    private Goods goods;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Cart cart;
    private Integer totalGoods;

    public CartGoods(Goods goods) {
        this.goods = goods;
        this.totalGoods = 0;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
        cart.getCartGoods().add(this);
    }
}
