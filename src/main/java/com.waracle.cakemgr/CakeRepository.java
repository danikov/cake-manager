package com.waracle.cakemgr;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;

import java.util.Collection;
import java.util.List;

@AllArgsConstructor
public class CakeRepository implements AutoCloseable {
    private final Session session;

    public CakeRepository() {
        this.session = HibernateUtil.getSessionFactory().openSession();
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
            System.out.println("Added cake entity");
            session.getTransaction().commit();
        } catch (ConstraintViolationException ex) {
            System.err.println("Failed to add cake entity");
            ex.printStackTrace();
        }
    }

    public void save(Collection<Cake> cakes) {
        cakes.forEach(this::save);
    }

    @Override
    public void close() throws Exception {
        session.close();
    }
}
