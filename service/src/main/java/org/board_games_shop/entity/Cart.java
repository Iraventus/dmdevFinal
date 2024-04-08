package org.board_games_shop.entity;

import jakarta.persistence.*;
import lombok.*;
import org.board_games_shop.entity.users.Customer;
import org.board_games_shop.entity.users.User;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false, exclude = "cartGoods")
@ToString(exclude = "cartGoods")
@Builder
@Entity
public class Cart extends BaseEntity<Long> {

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    private Customer user;
    @OneToMany(mappedBy = "cart")
    private List<CartGoods> cartGoods;

    public void setCart(Customer user) {
        user.setCart(this);
        this.user = user;
    }
}
