package org.bgs.repository;

import org.bgs.ApplicationRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Sql({
        "classpath:sql/data.sql"
})
@SpringBootTest(classes = ApplicationRunner.class)
@WithMockUser(username = "test@gmail.com", password = "test", authorities = {"MANAGER"})
public abstract class BaseIT {
}
