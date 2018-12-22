package com.epam.library.controller;

import com.epam.library.action.Action;
import com.epam.library.action.ActionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class MainController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger ROOT_LOGGER  = LogManager.getRootLogger();
    public MainController() {
        super();
    }

    @Override
    public void init() throws ServletException {
        super.init();
        ROOT_LOGGER.info("Servlet started");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String actionRequest = request.getRequestURI();
        ActionFactory factory = ActionFactory.getInstance();
        Action action = factory.getAction(actionRequest);
        action.execute(request, response);
    }
}
