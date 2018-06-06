package com.waracle.cakemgr;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/cakes")
public class CakeServlet extends HttpServlet {
    private static final Logger log = LogManager.getLogger(CakeServlet.class);

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

            Gson gson = req.getHeader("Accept").contains("application/json") ?
                    new Gson() :
                    new GsonBuilder().setPrettyPrinting().create();

            resp.getWriter().println(gson.toJson(cakes));
        } catch (IOException | HibernateException e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        try (CakeRepository cakeRepo = new CakeRepository()) {

            Cake newCake = new Cake();
            newCake.setTitle(req.getParameter("title"));
            newCake.setDescription(req.getParameter("desc"));
            newCake.setImage(req.getParameter("image"));

            newCake = cakeRepo.save(newCake);

            if (newCake != null) {
                req.getSession().setAttribute("feedback", "Cake created (or updated)!");
                resp.sendRedirect("index.gsp");
            } else {
                req.setAttribute("feedback", "Bad cake, cannot create!");
                req.getRequestDispatcher("index.jsp").forward(req, resp);
            }
        } catch (IOException | HibernateException e) {
            throw new ServletException(e);
        }
    }
}
