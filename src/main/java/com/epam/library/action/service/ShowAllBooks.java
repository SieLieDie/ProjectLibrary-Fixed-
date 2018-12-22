package com.epam.library.action.service;

import com.epam.library.Dao.BookDao;
import com.epam.library.action.Action;
import com.epam.library.entity.Book;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.epam.library.constants.ActionConstants.BOOK_LIST;
import static com.epam.library.constants.ActionConstants.LANG_ID;
import static com.epam.library.constants.ActionConstants.USER_ID;

public class ShowAllBooks implements Action{

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Book> bookList = new ArrayList<Book>();
        Integer langID = (Integer) request.getSession().getAttribute(LANG_ID);
        Integer userID = (Integer) request.getSession().getAttribute(USER_ID);
        BookDao bookDAO = new BookDao();
        bookDAO.getAllBookFromDb(bookList, langID, userID);
        request.setAttribute(BOOK_LIST, bookList);
    }
}
