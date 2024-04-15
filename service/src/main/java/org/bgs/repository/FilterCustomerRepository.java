package org.bgs.repository;

import org.bgs.dto.UserFilter;
import org.bgs.entity.users.Customer;

import java.util.List;

public interface FilterCustomerRepository {

    List<Customer> findAllByFilter(UserFilter filter);
}
