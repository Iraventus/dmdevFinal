package org.board_games_shop.repository;

import org.board_games_shop.dto.UserFilter;
import org.board_games_shop.entity.users.User;

import java.util.List;

public interface FilterUserRepository<T extends User> {

    List<T> findAllByFilter(UserFilter filter);
}
