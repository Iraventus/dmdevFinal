package org.example.entity.users;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;
import org.example.entity.Cart;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@DiscriminatorValue("customer")
public class Customer extends User {

    @JdbcTypeCode(SqlTypes.JSON)
    private JsonNode address;

    @Builder
    public Customer(String login, String password, String firstname, String lastname,
                    String phone, LocalDate birthDate, LocalDate registrationDate, List<Cart> carts, JsonNode address) {
        super(login, password, firstname, lastname, phone, birthDate, registrationDate, carts);
        this.address = address;
    }
}
