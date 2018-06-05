package com.waracle.cakemgr;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CakeRepoTest {
    private SessionFactory sessionFactory;
    private Session session = null;

    @Before
    public void before() {
        sessionFactory =  HibernateUtil.getSessionFactory();
        session = sessionFactory.openSession();
    }

    @Test
    public void returnsCakeWithMatchingTitle() {
        Cake cake = new Cake();
        cake.setTitle("Unicorn Cake");
        cake.setDescription("A rainbow sponge cake with glitter frosting");
        cake.setImage("http://www.cupofsugarpinchofsalt.com/wp-content/uploads/2013/07/RainbowCake-small2.jpg");

        session.save(cake);

        CakeRepository cakeRepo = new CakeRepository(session);
        Cake loadedCake = cakeRepo.loadBy("Unicorn Cake");
        assertNotNull(loadedCake);
        assertEquals(loadedCake, cake);
    }

    @After
    public void after() {
        session.close();
        sessionFactory.close();
    }
}
