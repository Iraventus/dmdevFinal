package org.boardGamesShop.repository;

import org.boardGamesShop.ApplicationRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Sql({
        "classpath:sql/data.sql"
})
@SpringBootTest(classes = ApplicationRunner.class)
public abstract class BaseIT {
}
