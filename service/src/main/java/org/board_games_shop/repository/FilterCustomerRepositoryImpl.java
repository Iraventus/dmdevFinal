package org.board_games_shop.repository;

import com.querydsl.jpa.impl.JPAQuery;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.board_games_shop.dto.UserFilter;
import org.board_games_shop.entity.users.Customer;

import java.util.List;

import static org.board_games_shop.entity.users.QCustomer.customer;

@RequiredArgsConstructor
public class FilterCustomerRepositoryImpl implements FilterCustomerRepository {

    private final EntityManager entityManager;

    @Override
    public List<Customer> findAllByFilter(UserFilter filter) {
        var predicate = QPredicate.builder().add(filter.login(), customer.login::eq).add(filter.firstname(), customer.firstname::eq).add(filter.lastname(), customer.lastname::eq).buildAnd();
        return new JPAQuery<Customer>(entityManager).select(customer).from(customer).where(predicate).fetch();
    }
}
