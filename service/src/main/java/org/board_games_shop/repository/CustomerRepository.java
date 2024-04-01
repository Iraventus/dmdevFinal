package org.board_games_shop.repository;

import org.board_games_shop.entity.users.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long>, FilterCustomerRepository {

    Optional<Customer> findByLogin(String login);
}
