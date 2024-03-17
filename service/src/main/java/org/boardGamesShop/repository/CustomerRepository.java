package org.boardGamesShop.repository;

import com.querydsl.jpa.impl.JPAQuery;
import jakarta.persistence.EntityManager;
import org.boardGamesShop.dto.UserFilter;
import org.boardGamesShop.entity.users.Customer;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.boardGamesShop.entity.users.QCustomer.customer;

@Repository
public class CustomerRepository extends RepositoryBase<Long, Customer> {

    public CustomerRepository(EntityManager entityManager) {
        super(Customer.class, entityManager);
    }

    public List<Customer> findByFilters(EntityManager entityManager, UserFilter userFilter) {
        var predicate = QPredicate.builder()
                .add(userFilter.getLogin(), customer.login::eq)
                .add(userFilter.getFirstName(), customer.firstname::eq)
                .add(userFilter.getLastName(), customer.lastname::eq)
                .buildAnd();
        return new JPAQuery<Customer>(entityManager)
                .select(customer)
                .from(customer)
                .where(predicate)
                .fetch();
    }
}
