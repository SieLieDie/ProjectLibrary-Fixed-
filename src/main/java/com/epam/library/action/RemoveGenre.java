package com.epam.library.action;

import com.epam.library.Dao.BookDao;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RemoveGenre implements Action {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int genreCoreID = Integer.parseInt(request.getParameter("genreCoreID"));
        BookDao bookDAO = new BookDao();
        bookDAO.removeGenre(genreCoreID);
        GoToMainPage goToMainPage = new GoToMainPage();
        goToMainPage.execute(request, response);
    }
}
