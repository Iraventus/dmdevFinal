package org.boardGamesShop.repository;

import org.boardGamesShop.entity.users.Customer;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long>, FilterUserRepository {

    Optional<Customer> findByLogin(String login);

    List<Customer> findAllBy(Sort sort);
}
