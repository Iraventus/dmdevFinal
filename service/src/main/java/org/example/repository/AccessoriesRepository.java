package org.example.repository;

import static org.example.entity.goods.QAccessories.accessories;

import com.querydsl.jpa.impl.JPAQuery;
import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Optional;
import org.example.entity.Producer_;
import org.example.entity.goods.Accessories;
import org.example.entity.goods.Accessories_;
import org.springframework.stereotype.Repository;

@Repository
public class AccessoriesRepository extends RepositoryBase<Long, Accessories> {

  public AccessoriesRepository(EntityManager entityManager) {
    super(Accessories.class, entityManager);
  }

  public Optional<Accessories> findByName(EntityManager entityManager, String name) {
    return Optional.ofNullable(new JPAQuery<Accessories>(entityManager)
        .select(accessories)
        .from(accessories)
        .where(accessories.name.eq(name))
        .fetchOne());
  }

  public List<Accessories> findByProducerName(EntityManager entityManager, String producerName) {

    var cb = entityManager.getCriteriaBuilder();
    var criteria = cb.createQuery(Accessories.class);
    var goods = criteria.from(Accessories.class);
    var producer = goods.join(Accessories_.PRODUCER);
    criteria.select(goods).where(cb.equal(producer.get(Producer_.NAME), producerName));
    return entityManager.createQuery(criteria).getResultList();
  }
}
