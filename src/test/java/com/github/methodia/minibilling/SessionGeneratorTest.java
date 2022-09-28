package com.github.methodia.minibilling;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

public class SessionGeneratorTest {

    private static SessionFactory sessionFactory;
    private Session session;

    @BeforeAll
    static void setUpBeforeClass() {
        sessionFactory = new Configuration().configure().buildSessionFactory();
    }

    @AfterAll
    static void tearDownAfterClass() {
        sessionFactory.close();
    }

    @BeforeEach
    void setUp() {
        session = sessionFactory.openSession();
    }

    @AfterEach
    void tearDown() {
        session.close();
    }

    @Test
    public void testCreate(){
        System.out.println("Running testCreate");
        session.beginTransaction();
        final User user = new User("Ivan", "2", 2);
        final Integer id = (Integer) session.save(user);
        session.getTransaction().commit();

        Assertions.assertTrue(id>0);
    }
}
