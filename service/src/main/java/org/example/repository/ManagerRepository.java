package org.example.repository;

import static org.example.entity.users.QManager.manager;

import com.querydsl.jpa.impl.JPAQuery;
import jakarta.persistence.EntityManager;
import java.util.List;
import org.example.dto.UserFilter;
import org.example.entity.users.Manager;
import org.springframework.stereotype.Repository;

@Repository
public class ManagerRepository extends RepositoryBase<Long, Manager> {

  public ManagerRepository(EntityManager entityManager) {
    super(Manager.class, entityManager);
  }

  public List<Manager> findByFilters(EntityManager entityManager, UserFilter userFilter) {
    var predicate = QPredicate.builder()
        .add(userFilter.getLogin(), manager.login::eq)
        .add(userFilter.getFirstName(), manager.firstname::eq)
        .add(userFilter.getLastName(), manager.lastname::eq)
        .buildAnd();
    return new JPAQuery<Manager>(entityManager)
        .select(manager)
        .from(manager)
        .where(predicate)
        .fetch();
  }
}
