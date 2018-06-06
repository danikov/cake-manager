package com.waracle.cakemgr;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@WebListener
public class InitListener implements ServletContextListener {
    private static final Logger log = LogManager.getLogger(InitListener.class);
    private static final String CAKE_JSON_URL =
            "https://gist.githubusercontent.com/hart88/198f29ec5114a3ec3460/raw/8dd19a88f9b8d24c23d9960f3300d0c917a4f07c/cake.json";

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            loadDefaultCakes(CAKE_JSON_URL);
        } catch (ServletException e) {

        }

        log.info("On start web app");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        log.info("On shutdown web app");
    }

    List<Cake> loadDefaultCakes(String url) throws ServletException {
        try (InputStream inputStream = new URL(url).openStream(); CakeRepository cakeRepo = new CakeRepository()) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            Type targetClassType = new TypeToken<ArrayList<Cake>>() {
            }.getType();
            List<Cake> cakes = new Gson().fromJson(reader, targetClassType);

            cakeRepo.save(cakes);
            return cakeRepo.getAll();
        } catch (IOException ex) {
            log.warn("Error fetching initial cake json", ex);
            throw new ServletException(ex);
        }
    }
}
