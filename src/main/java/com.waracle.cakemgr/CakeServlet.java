package com.waracle.cakemgr;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/cakes")
public class CakeServlet extends HttpServlet {
    private static final Logger log = LogManager.getLogger(CakeRepository.class);
    private static final String CAKE_JSON_URL =
            "https://gist.githubusercontent.com/hart88/198f29ec5114a3ec3460/raw/8dd19a88f9b8d24c23d9960f3300d0c917a4f07c/cake.json";

    @Override
    public void init() throws ServletException {
        super.init();

        log.info("init started");
        log.info("downloading cake json");
        try (CakeRepository cakeRepo = new CakeRepository()) {
            List<Cake> cakes = loadCakeJson(CAKE_JSON_URL);
            cakeRepo.save(cakes);
        } catch (Exception e) {
            throw new ServletException(e);
        }

        log.info("init finished");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (CakeRepository cakeRepo = new CakeRepository()) {
            List<Cake> cakes = cakeRepo.getAll();

            resp.getWriter().println(new Gson().toJson(cakes));
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    private List<Cake> loadCakeJson(String url) throws ServletException {
        try (InputStream inputStream = new URL(url).openStream()) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            Type targetClassType = new TypeToken<ArrayList<Cake>>() { }.getType();

            List<Cake> cakes = new Gson().fromJson(reader, targetClassType);
            return cakes;
        } catch (Exception ex) {
            throw new ServletException(ex);
        }
    }
}
