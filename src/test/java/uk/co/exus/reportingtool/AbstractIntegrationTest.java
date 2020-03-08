package com.company.reportingtool;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.transaction.TestTransaction;

import javax.transaction.Transactional;

@RunWith(SpringRunner.class)
@ActiveProfiles(profiles = "test")
@AutoConfigureTestEntityManager
@ContextConfiguration(classes = ReportingToolApplication.class)
@SpringBootTest
@Transactional
public abstract class AbstractIntegrationTest {

    @Autowired
    protected TestEntityManager entityManager;

    @SpringBootApplication
    static class TestConfiguration {

        @Bean
        public FlywayMigrationStrategy dropCreate() {
            return flyway -> {
                flyway.clean();
                flyway.migrate();
            };
        }
    }


    @Before
    public void before() {
    }

    @After
    public void after() {
    }

    public void doInTransaction(Runnable r) {
        if (!TestTransaction.isActive()) {
            TestTransaction.start();
        }
        TestTransaction.flagForCommit();
        r.run();
        TestTransaction.end();
        TestTransaction.start();
        TestTransaction.flagForCommit();
    }
}
