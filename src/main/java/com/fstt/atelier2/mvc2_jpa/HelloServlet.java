package com.fstt.atelier2.mvc2_jpa;

import java.io.*;

import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
@WebServlet(name = "helloServlet", urlPatterns = "*")
public class HelloServlet extends HttpServlet {
    private String message;

    public void init() {
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // redirect to /produits/list
        response.sendRedirect(request.getContextPath() + "/produits/list");
    }

    public void destroy() {
    }
}