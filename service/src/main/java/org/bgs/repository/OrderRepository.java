package org.bgs.repository;

import org.bgs.entity.Order;
import org.bgs.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findAllByReservationEndDateBeforeAndStatus(Instant instant, Status status);

    List<Order> findAllByUserLoginAndStatus(String login, Status status);
}
