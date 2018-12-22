package com.epam.library.action;

import com.epam.library.Dao.UserDao;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.epam.library.constants.ActionConstants.BOOK_ID;
import static com.epam.library.constants.ActionConstants.USER_ID;

public class AddToFavorite implements Action {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int userID = (int) request.getSession().getAttribute(USER_ID);
        int bookID = Integer.parseInt(request.getParameter(BOOK_ID));
        UserDao userDAO = new UserDao();
        userDAO.addToFavorite(userID, bookID);
        GoToMainPage goToMainPage = new GoToMainPage();
        goToMainPage.execute(request, response);
    }
}
