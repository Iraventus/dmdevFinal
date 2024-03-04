package org.example.dao;

import jakarta.persistence.EntityManager;
import org.example.entity.Producer;

public class ProducerRepository extends RepositoryBase<Long, Producer> {
    public ProducerRepository(EntityManager entityManager) {
        super(Producer.class, entityManager);
    }
}
