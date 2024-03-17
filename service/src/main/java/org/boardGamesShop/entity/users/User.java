package org.boardGamesShop.entity.users;

import jakarta.persistence.*;
import lombok.*;
import org.boardGamesShop.entity.BaseEntity;
import org.boardGamesShop.entity.Cart;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString(exclude = "carts")
@EqualsAndHashCode(callSuper = false, exclude = "carts")
@Table(name = "users")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "role")
public abstract class User extends BaseEntity<Long> {

    private String login;
    private String password;
    private String firstname;
    private String lastname;
    private String phone;
    private LocalDate birthDate;
    private LocalDate registrationDate;
    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST)
    private List<Cart> carts = new ArrayList<>();
}
