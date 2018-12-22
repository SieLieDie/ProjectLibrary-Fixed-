package com.epam.library.action;

import com.epam.library.Dao.BookDao;
import com.epam.library.action.service.GetAuthors;
import com.epam.library.action.service.GetGenres;
import com.epam.library.entity.Book;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.epam.library.constants.ActionConstants.*;

public class SearchBookByName implements Action {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Book> bookList = new ArrayList<>();
        int langID = (int) request.getSession().getAttribute(LANG_ID);
        Integer userID = (Integer) request.getSession().getAttribute(USER_ID);
        String bookName = request.getParameter(BOOK_NAME);
        BookDao bookDAO = new BookDao();
        bookDAO.searchBook(bookList, bookName, langID, userID);
        if(bookList.size() == 0){
            request.setAttribute("find", false);
        }
        request.setAttribute(BOOK_LIST, bookList);
        GetGenres getGenres = new GetGenres();
        getGenres.execute(request, response);
        GetAuthors getAuthors = new GetAuthors();
        getAuthors.execute(request, response);
        RequestDispatcher dispatcher = request.getRequestDispatcher(MAIN_PAGE);
        dispatcher.forward(request, response);
    }
}
