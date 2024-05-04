package org.bgs.entity.goods;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.*;
import org.bgs.entity.Producer;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "producer")
@EqualsAndHashCode(callSuper = true)
@Entity
@DiscriminatorValue("accessories")
public class Accessories extends Goods {

    @ManyToOne(fetch = FetchType.EAGER)
    private Producer producer;

    @Builder
    public Accessories(String name, String description, Integer quantity, Integer price, Producer producer) {
        super(name, description, quantity, price);
        this.producer = producer;
    }
}
