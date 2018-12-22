package com.epam.library.action.service;

import com.epam.library.action.Action;
import com.epam.library.action.GoToMainPage;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

import static com.epam.library.constants.ActionConstants.USER_ROLE_ID;

public class ExitFromSession implements Action {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getSession().removeAttribute(USER_ROLE_ID);
        GoToMainPage goToMainPage = new GoToMainPage();
        goToMainPage.execute(request, response);
    }
}
