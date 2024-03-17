package org.boardGamesShop.repository;

import jakarta.persistence.EntityManager;
import org.boardGamesShop.util.TestDataImporter;
import org.boardGamesShop.annotation.IT;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@IT
@Transactional
public abstract class BaseIT {

    @Autowired
    protected EntityManager entityManager;

    @BeforeEach
    public void init() {
        TestDataImporter.importData(entityManager);
    }
}
