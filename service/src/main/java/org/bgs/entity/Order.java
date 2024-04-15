package org.bgs.entity;

import jakarta.persistence.*;
import lombok.*;
import org.bgs.entity.users.Customer;

import java.time.Instant;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "orders")
public class Order extends AuditingEntity<Long> {

    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY)
    private List<CartGoods> cartGoods;
    @Enumerated(EnumType.STRING)
    private Status status;
    private Instant reservationEndDate;
    @ManyToOne(fetch = FetchType.LAZY)
    private Customer user;
}