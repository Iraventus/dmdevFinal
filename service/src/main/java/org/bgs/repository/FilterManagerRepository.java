package org.bgs.repository;

import org.bgs.dto.UserFilter;
import org.bgs.entity.users.Manager;

import java.util.List;

public interface FilterManagerRepository {

    List<Manager> findAllByFilter(UserFilter filter);
}
