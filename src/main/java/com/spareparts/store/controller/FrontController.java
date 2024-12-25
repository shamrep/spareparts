package com.spareparts.store.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class FrontController extends HttpServlet {

    private final Dispatcher dispatcher = new DispatcherImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {

        dispatcher.dispatch(new Request(req), new Response(resp));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        dispatcher.dispatch(new Request(req), new Response(resp));
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        dispatcher.dispatch(new Request(req), new Response(resp));
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        dispatcher.dispatch(new Request(req), new Response(resp));
    }
}
