package com.dipartimento.demowebapplications.controller.servlet;

import com.dipartimento.demowebapplications.model.Restaurant;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/ristoranti")
public class ElencoRistoranti extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("Funziona!!");
        //PrintWriter writer = resp.getWriter();
        //writer.println("<html><strong>Funziona!!</strong></html>");

        String username = (String) req.getSession(true).getAttribute("username");
        System.out.println("username: " + username);
        if (username != null) {
            //Ho fatto la query
            List<Restaurant> ristoranti = new ArrayList<Restaurant>();
            Restaurant r1 = new Restaurant();
            r1.setName("Le Casette di Zio Santino");
            r1.setDescription("Ristorante/Pizzeria");
            r1.setLocation("Rende");
            ristoranti.add(r1);

            Restaurant r2 = new Restaurant();
            r2.setName("Fratfrode");
            r2.setDescription("Pizzeria");
            r2.setLocation("Rende");
            ristoranti.add(r2);

            req.setAttribute("ristoranti", ristoranti);

            RequestDispatcher dispatcher = req.getRequestDispatcher("views/elenco_ristoranti.html");
            dispatcher.forward(req, resp);
        }else{
            RequestDispatcher dispatcher = req.getRequestDispatcher("views/notAuthorized.html");
            dispatcher.forward(req, resp);
        }

    }
}
