package com.waracle.cakemgr;

import lombok.AllArgsConstructor;
import org.hibernate.Session;

import java.util.List;

@AllArgsConstructor
public class CakeRepository {
    private final Session session;

    public Cake loadBy(String title) {
        return (Cake) session.get(Cake.class, title);
    }

    @SuppressWarnings("unchecked")
    public List<Cake> getAll() {
        return (List<Cake>) session.createCriteria(Cake.class).list();
    }
}
