package com.epam.library.action;

import com.epam.library.Dao.BookDao;
import com.epam.library.action.service.GetAuthors;
import com.epam.library.action.service.GetGenres;
import com.epam.library.action.service.GetLang;
import com.epam.library.entity.Book;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.epam.library.constants.ActionConstants.*;

public class ShowBookByAuthor implements Action {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        GetLang getLang = new GetLang();
        getLang.execute(request, response);
        GetGenres getGenres = new GetGenres();
        getGenres.execute(request, response);
        GetAuthors getAuthors = new GetAuthors();
        getAuthors.execute(request, response);
        List<Book> bookList = new ArrayList<>();
        int langID = (int) request.getSession().getAttribute(LANG_ID);
        int authorID = Integer.parseInt(request.getParameter(AUTHOR_ID));
        Integer userID = (Integer) request.getSession().getAttribute(USER_ID);
        BookDao bookDAO = new BookDao();
        bookDAO.getBookByAuthor(bookList, authorID, langID, userID);
        request.setAttribute(BOOK_LIST, bookList);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/MainPage.jsp");
        dispatcher.forward(request, response);
    }
}
