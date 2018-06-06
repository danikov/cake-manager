package com.waracle.cakemgr;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = {"/"})
public class IndexServlet extends HttpServlet {
    private static final Logger log = LogManager.getLogger(IndexServlet.class);

    @Override
    public void init() throws ServletException {
        super.init();

        log.info("init started");
        log.info("init finished");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        try (CakeRepository cakeRepo = new CakeRepository()) {
            List<Cake> cakes = cakeRepo.getAll();
            req.setAttribute("cakes", cakes);

            RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/jsp/index.jsp");
            dispatcher.forward(req, resp);
        } catch (IOException | HibernateException e) {
            throw new ServletException(e);
        }
    }
}
