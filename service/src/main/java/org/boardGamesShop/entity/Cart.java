package org.boardGamesShop.entity;

import jakarta.persistence.*;
import lombok.*;
import org.boardGamesShop.entity.users.User;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false, exclude = "cartGoods")
@ToString(exclude = "cartGoods")
@Builder
@Entity
public class Cart extends BaseEntity<Long> {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private User user;
    private String name;
    @OneToMany(mappedBy = "cart")
    private List<CartGoods> cartGoods;
}
