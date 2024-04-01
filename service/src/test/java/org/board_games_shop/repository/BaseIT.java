package org.board_games_shop.repository;

import org.board_games_shop.ApplicationRunner;
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
