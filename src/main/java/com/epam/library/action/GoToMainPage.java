package com.epam.library.action;


import com.epam.library.action.service.GetAuthors;
import com.epam.library.action.service.GetGenres;
import com.epam.library.action.service.GetLang;
import com.epam.library.action.service.ShowAllBooks;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.epam.library.constants.ActionConstants.MAIN_PAGE;

public class GoToMainPage implements Action {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        GetLang getLang = new GetLang();
        getLang.execute(request, response);
        GetGenres getGenres = new GetGenres();
        getGenres.execute(request, response);
        GetAuthors getAuthors = new GetAuthors();
        getAuthors.execute(request, response);
        ShowAllBooks showAllBooks = new ShowAllBooks();
        showAllBooks.execute(request, response);
        RequestDispatcher dispatcher = request.getRequestDispatcher(MAIN_PAGE);
        dispatcher.forward(request, response);
    }
}