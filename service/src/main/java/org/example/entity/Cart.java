package org.example.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.entity.users.User;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "cartGoods")
@Builder
@Entity
public class Cart extends BaseEntity<Long> {

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST, optional = false)
    private User user;
    private String name;
    @OneToMany(mappedBy = "cart")
    private List<CartGoods> cartGoods;
}
