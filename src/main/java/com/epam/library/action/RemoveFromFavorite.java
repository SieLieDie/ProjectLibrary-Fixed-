package com.epam.library.action;

import com.epam.library.Dao.BookDao;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.epam.library.constants.ActionConstants.BOOK_ID;
import static com.epam.library.constants.ActionConstants.USER_ID;

public class RemoveFromFavorite implements Action {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int bookID = Integer.parseInt(request.getParameter(BOOK_ID));
        Integer userID = (Integer) request.getSession().getAttribute(USER_ID);
        BookDao bookDAO = new BookDao();
        bookDAO.removeFromFavorite(bookID, userID);
        GoToMainPage goToMainPage = new GoToMainPage();
        goToMainPage.execute(request, response);
    }
}