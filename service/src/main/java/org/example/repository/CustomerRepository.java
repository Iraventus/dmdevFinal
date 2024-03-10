package org.example.repository;

import static org.example.entity.users.QCustomer.customer;

import com.querydsl.jpa.impl.JPAQuery;
import jakarta.persistence.EntityManager;
import java.util.List;
import org.example.dto.UserFilter;
import org.example.entity.users.Customer;
import org.springframework.stereotype.Repository;

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
