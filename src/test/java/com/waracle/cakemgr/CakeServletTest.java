package com.waracle.cakemgr;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CakeServletTest {
    private SessionFactory sessionFactory;
    private Session session = null;
    private CakeRepository cakeRepo = null;

    @Before
    public void before() {
        sessionFactory = HibernateUtil.getSessionFactory();
        session = sessionFactory.openSession();
        cakeRepo = new CakeRepository(session);
    }

    @Test
    public void testServletInitialisesCorrectly() throws Exception {
        new InitListener().loadDefaultCakes(getClass().getClassLoader().getResource("inital-cakes.json").toString());

        assertEquals(5, cakeRepo.getAll().size());
    }

    @After
    public void after() {
        session.close();
    }
}
