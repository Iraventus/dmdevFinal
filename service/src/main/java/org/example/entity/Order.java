package org.example.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "orders", schema = "public")
public class Order {

    @Id
    private Cart cart;
    private User user;
    @Enumerated(EnumType.STRING)
    private Status status;
    @Column(name = "creation_date")
    private Timestamp creationDate;
    @Column(name = "reservation_end_date")
    private Timestamp reservationEndDate;
}
