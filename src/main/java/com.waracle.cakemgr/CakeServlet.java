package com.waracle.cakemgr;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.google.common.collect.Lists;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URL;
import java.util.List;

@WebServlet("/cakes")
public class CakeServlet extends HttpServlet {
    private static final String CAKE_JSON_URL =
            "https://gist.githubusercontent.com/hart88/198f29ec5114a3ec3460/raw/8dd19a88f9b8d24c23d9960f3300d0c917a4f07c/cake.json";

    @Override
    public void init() throws ServletException {
        super.init();

        System.out.println("init started");

        System.out.println("downloading cake json");
        try (CakeRepository cakeRepo = new CakeRepository()) {
            cakeRepo.save(loadCakeJson(CAKE_JSON_URL));
        } catch (Exception e) {
            throw new ServletException(e);
        }

        System.out.println("init finished");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (CakeRepository cakeRepo = new CakeRepository()) {
            List<Cake> list = cakeRepo.getAll();

            resp.getWriter().println("[");

            for (Cake entity : list) {
                resp.getWriter().println("\t{");

                resp.getWriter().println("\t\t\"title\" : " + entity.getTitle() + ", ");
                resp.getWriter().println("\t\t\"desc\" : " + entity.getDescription() + ",");
                resp.getWriter().println("\t\t\"image\" : " + entity.getImage());

                resp.getWriter().println("\t}");
            }

            resp.getWriter().println("]");
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    private List<Cake> loadCakeJson(String url) throws ServletException {
        List<Cake> cakes = Lists.newArrayList();

        try (InputStream inputStream = new URL(url).openStream()) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            StringBuffer buffer = new StringBuffer();
            String line = reader.readLine();
            while (line != null) {
                buffer.append(line);
                line = reader.readLine();
            }

            System.out.println("parsing cake json");
            JsonParser parser = new JsonFactory().createParser(buffer.toString());
            if (JsonToken.START_ARRAY != parser.nextToken()) {
                throw new Exception("bad token");
            }

            JsonToken nextToken = parser.nextToken();
            while(nextToken == JsonToken.START_OBJECT) {
                System.out.println("creating cake entity");

                Cake cakeEntity = new Cake();
                System.out.println(parser.nextFieldName());
                cakeEntity.setTitle(parser.nextTextValue());

                System.out.println(parser.nextFieldName());
                cakeEntity.setDescription(parser.nextTextValue());

                System.out.println(parser.nextFieldName());
                cakeEntity.setImage(parser.nextTextValue());

                cakes.add(cakeEntity);

                nextToken = parser.nextToken();
                System.out.println(nextToken);

                nextToken = parser.nextToken();
                System.out.println(nextToken);
            }

        } catch (Exception ex) {
            throw new ServletException(ex);
        }

        return cakes;
    }
}
