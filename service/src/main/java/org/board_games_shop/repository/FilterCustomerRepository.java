package org.board_games_shop.repository;

import org.board_games_shop.dto.UserFilter;
import org.board_games_shop.entity.users.Customer;

import java.util.List;

public interface FilterCustomerRepository {

    List<Customer> findAllByFilter(UserFilter filter);
}
