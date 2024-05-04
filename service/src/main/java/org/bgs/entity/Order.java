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
@ToString(exclude = {"orderGoods", "user"})
public class Order extends AuditingEntity<Long> {

    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<OrderGoods> orderGoods;
    @Enumerated(EnumType.STRING)
    private Status status;
    private Instant reservationEndDate;
    @ManyToOne(fetch = FetchType.LAZY)
    private Customer user;
}