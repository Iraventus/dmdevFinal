package org.bgs.entity.users;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;
import org.bgs.entity.Cart;
import org.bgs.entity.Order;
import org.bgs.entity.Role;
import org.bgs.nodeModel.AddressNode;
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

    @Builder
    public Customer(@Email String login, String password, String firstname, String lastname, String phone, Role role, LocalDate birthDate, AddressNode address, Cart cart, List<Order> orders) {
        super(login, password, firstname, lastname, phone, role, birthDate);
        this.address = address;
        this.cart = cart;
        this.orders = orders;
    }
}
