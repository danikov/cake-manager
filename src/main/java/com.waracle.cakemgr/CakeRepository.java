package com.waracle.cakemgr;

import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;

import java.util.Collection;
import java.util.List;

@AllArgsConstructor
public class CakeRepository implements AutoCloseable {
    private static final Logger log = LogManager.getLogger(CakeRepository.class);

    private final Session session;
    private final Boolean ownsSession;

    public CakeRepository() {
        this.session = HibernateUtil.getSessionFactory().openSession();
        ownsSession = true;
    }

    public Cake getByTitle(String title) {
        return (Cake) session.get(Cake.class, title);
    }

    @SuppressWarnings("unchecked")
    public List<Cake> getAll() {
        return (List<Cake>) session.createCriteria(Cake.class).list();
    }

    public void save(Cake cake) {
        try {
            session.beginTransaction();
            session.persist(cake);
            log.info("Added cake: {}", cake);
            session.getTransaction().commit();
        } catch (ConstraintViolationException ex) {
            log.warn("Failed to add cake", ex);
        }
    }

    public void save(Collection<Cake> cakes) {
        cakes.forEach(this::save);
    }

    @Override
    public void close() throws Exception {
        if (ownsSession) {
            session.close();
        }
    }
}
