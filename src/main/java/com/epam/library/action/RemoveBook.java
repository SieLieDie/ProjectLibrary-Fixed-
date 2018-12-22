package com.epam.library.action;

import com.epam.library.Dao.BookDao;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.epam.library.constants.ActionConstants.BOOK_ID;

public class RemoveBook implements Action {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int bookID = Integer.parseInt(request.getParameter(BOOK_ID));
        BookDao bookDAO = new BookDao();
        bookDAO.removeBook(bookID);
        GoToMainPage goToMainPage = new GoToMainPage();
        goToMainPage.execute(request, response);
    }
}