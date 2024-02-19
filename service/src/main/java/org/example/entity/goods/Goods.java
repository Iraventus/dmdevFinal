package org.example.entity.goods;

import jakarta.persistence.*;
import lombok.*;
import org.example.entity.BaseEntity;
import org.example.entity.CartGoods;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "name", callSuper = true)
@ToString(exclude = "cartGoods")
@Entity
@Table(name = "goods")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type")
public abstract class Goods extends BaseEntity<Long> {

    @Column(nullable = false)
    private String name;
    private String description;
    private Integer quantity;
    private Integer price;
    @OneToMany(mappedBy = "goods")
    private List<CartGoods> cartGoods = new ArrayList<>();
}
