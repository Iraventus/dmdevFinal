package org.board_games_shop.repository;

import org.board_games_shop.dto.UserFilter;
import org.board_games_shop.entity.users.Manager;

import java.util.List;

public interface FilterManagerRepository {

    List<Manager> findAllByFilter(UserFilter filter);
}
