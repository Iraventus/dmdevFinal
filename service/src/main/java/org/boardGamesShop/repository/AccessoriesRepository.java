package org.boardGamesShop.repository;

import com.querydsl.jpa.impl.JPAQuery;
import jakarta.persistence.EntityManager;
import org.boardGamesShop.entity.Producer_;
import org.boardGamesShop.entity.goods.Accessories;
import org.boardGamesShop.entity.goods.Accessories_;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static org.boardGamesShop.entity.goods.QAccessories.accessories;

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
