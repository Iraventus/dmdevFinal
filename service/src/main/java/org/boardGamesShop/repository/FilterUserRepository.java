package org.boardGamesShop.repository;

import org.boardGamesShop.dto.UserFilter;
import org.boardGamesShop.entity.users.User;

import java.util.List;

public interface FilterUserRepository {

    List<User> findAllByFilter(UserFilter filter);
}
