package org.bgs.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.*;
import org.bgs.entity.goods.Goods;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"goods", "order"}, callSuper = false)
@ToString(exclude = {"goods", "order"})
@Builder
@Entity
public class OrderGoods extends BaseEntity<Long> {

    @ManyToOne(fetch = FetchType.EAGER)
    private Goods goods;
    @ManyToOne(fetch = FetchType.EAGER)
    private Order order;
    private Integer totalGoods;

    public void setOrder(Order order) {
        this.order = order;
        this.order.getOrderGoods().add(this);
    }
}
