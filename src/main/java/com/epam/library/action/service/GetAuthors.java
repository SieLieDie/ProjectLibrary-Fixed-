package com.epam.library.action.service;

import com.epam.library.Dao.BookDao;
import com.epam.library.action.Action;
import com.epam.library.entity.Attribute;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.epam.library.constants.ActionConstants.*;

public class GetAuthors implements Action {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Attribute> authorList = new ArrayList<>();
        BookDao bookDAO = new BookDao();
        int langID = (int) request.getSession().getAttribute(LANG_ID);
        bookDAO.getAuthorForBook(authorList,langID);
        request.setAttribute("authorList", authorList);
    }
}
