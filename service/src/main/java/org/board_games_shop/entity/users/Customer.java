package org.board_games_shop.entity.users;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.board_games_shop.entity.Cart;
import org.board_games_shop.entity.Order;
import org.board_games_shop.entity.Role;
import org.board_games_shop.nodeModel.AddressNode;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@DiscriminatorValue("CUSTOMER")
public class Customer extends User {

    @JdbcTypeCode(SqlTypes.JSON)
    private AddressNode address;
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Cart cart;
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Order> orders;

    public Customer(String login, String password, String firstname, String lastname, String phone,
                    Role role, LocalDate birthDate, LocalDate registrationDate, AddressNode address, Cart cart, List<Order> orders) {
        super(login, password, firstname, lastname, phone, role, birthDate, registrationDate);
        this.address = address;
        this.cart = cart;
        this.orders = orders;
    }
}
