package com.epam.library.action;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public interface Action {
    public void execute(HttpServletRequest request, HttpServletResponse response) throws  ServletException, IOException;
}