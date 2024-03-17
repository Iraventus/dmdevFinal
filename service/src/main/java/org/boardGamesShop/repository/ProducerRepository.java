package org.boardGamesShop.repository;

import jakarta.persistence.EntityManager;
import org.boardGamesShop.entity.Producer;
import org.springframework.stereotype.Repository;

@Repository
public class ProducerRepository extends RepositoryBase<Long, Producer> {

    public ProducerRepository(EntityManager entityManager) {
        super(Producer.class, entityManager);
    }
}
