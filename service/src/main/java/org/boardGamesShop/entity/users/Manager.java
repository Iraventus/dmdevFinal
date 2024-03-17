package org.boardGamesShop.entity.users;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;
import org.boardGamesShop.entity.Cart;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@DiscriminatorValue("manager")
public class Manager extends User {

    private Integer personalDiscount;

    @Builder
    public Manager(String login, String password, String firstname, String lastname,
                   String phone, LocalDate birthDate, LocalDate registrationDate, List<Cart> carts, Integer personalDiscount) {
        super(login, password, firstname, lastname, phone, birthDate, registrationDate, carts);
        this.personalDiscount = personalDiscount;
    }
}
