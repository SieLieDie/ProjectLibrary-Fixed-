package com.epam.library.action;

import static com.epam.library.constants.ActionConstants.ERROR_PAGE;
import static com.epam.library.constants.ActionConstants.EXCEPTION;
import static com.epam.library.constants.ActionConstants.EXCEPTION_LOG_NAME;

import com.epam.library.Dao.BookDao;
import com.epam.library.Dao.InterfaceDao;
import com.epam.library.entity.Attribute;
import com.epam.library.entity.UserLocale;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AddAuthor implements Action {
    private final static Logger EXCEPTION_LOG = LogManager.getLogger(EXCEPTION_LOG_NAME);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int coreID = 0;
        String regex = "\\W";
        String authorCoreName = request.getParameter("authorCoreName");
        String[] coreArray = authorCoreName.split(regex);
        ArrayList<UserLocale> langList = new ArrayList<>();
        ArrayList<Attribute> authorList = new ArrayList<Attribute>();
        InterfaceDao interfaceDAO = new InterfaceDao();
        interfaceDAO.getLangs(langList);
        BookDao bookDAO = new BookDao();
        for (UserLocale lang : langList) {
            String authorName = request.getParameter(lang.getName());
            String[] nameArray = authorName.split(regex);
            authorList.add(new Attribute(nameArray[0], nameArray[1], lang.getId()));
        }
        Attribute authorCore = new Attribute();
        authorCore.setCoreName(coreArray[0]);
        authorCore.setSecondCoreName(coreArray[1]);
        try {
            bookDAO.addAuthor(authorList, authorCore, coreID);
        } catch (SQLException e) {
            EXCEPTION_LOG.info("Error adding author to database");
            request.setAttribute(EXCEPTION, "addAuthorException");
            RequestDispatcher dispatcher = request.getRequestDispatcher(ERROR_PAGE);
            dispatcher.forward(request, response);
        }
        GoToMainPage goToMainPage = new GoToMainPage();
        goToMainPage.execute(request, response);
    }
}