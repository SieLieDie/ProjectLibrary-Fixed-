package com.epam.library.action;

import com.epam.library.Dao.BookDao;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RemoveAuthor implements Action {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws  ServletException, IOException {
        int authorCoreID = Integer.parseInt(request.getParameter("authorCoreID"));
        BookDao bookDAO = new BookDao();
        bookDAO.removeAuthor(authorCoreID);
        GoToMainPage goToMainPage = new GoToMainPage();
        goToMainPage.execute(request, response);
    }
}
