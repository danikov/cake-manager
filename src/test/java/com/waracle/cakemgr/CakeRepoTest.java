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
    CakeRepository cakeRepo = null;

    @Before
    public void before() {
        sessionFactory = HibernateUtil.getSessionFactory();
        session = sessionFactory.openSession();
        cakeRepo = new CakeRepository(session);
    }

    @Test
    public void returnsCakeWithMatchingTitle() {
        Cake unicornCake = makeUnicornCake();

        assertEquals(0, cakeRepo.getAll().size());
        session.save(unicornCake);
        Cake loadedCake = cakeRepo.getByTitle("Unicorn Cake");
        assertNotNull(loadedCake);
        assertEquals(loadedCake, unicornCake);
    }

    @Test
    public void savesCake() {
        Cake unicornCake = makeUnicornCake();

        assertEquals(0, cakeRepo.getAll().size());
        cakeRepo.save(unicornCake);
        assertEquals(1, cakeRepo.getAll().size());

        Cake loadedCake = cakeRepo.getByTitle("Unicorn Cake");
        assertNotNull(loadedCake);
        assertEquals(loadedCake, unicornCake);
    }

    private Cake makeUnicornCake() {
        Cake cake = new Cake();
        cake.setTitle("Unicorn Cake");
        cake.setDescription("A rainbow sponge cake with glitter frosting");
        cake.setImage("http://www.cupofsugarpinchofsalt.com/wp-content/uploads/2013/07/RainbowCake-small2.jpg");
        return cake;
    }

    @After
    public void after() {
        session.close();
    }
}
