package com.epam.library.action;

import com.epam.library.Dao.BookDao;
import com.epam.library.Dao.InterfaceDao;
import com.epam.library.entity.Attribute;
import com.epam.library.entity.UserLocale;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import static com.epam.library.constants.ActionConstants.ERROR_PAGE;
import static com.epam.library.constants.ActionConstants.EXCEPTION;
import static com.epam.library.constants.ActionConstants.EXCEPTION_LOG_NAME;

public class AddGenre implements Action {
    private final static Logger EXCEPTION_LOG = LogManager.getLogger(EXCEPTION_LOG_NAME);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String core = request.getParameter("genreCore");
        int coreID = 0;
        String genreName;
        ArrayList<UserLocale> langList = new ArrayList<>();
        ArrayList<Attribute> attributeList = new ArrayList<Attribute>();
        InterfaceDao interfaceDAO = new InterfaceDao();
        interfaceDAO.getLangs(langList);
        Attribute genreCore = new Attribute();
        genreCore.setCoreName(core);
        for (UserLocale lang:langList) {
            genreName = request.getParameter(lang.getName());
            attributeList.add(new Attribute(genreName, lang.getId()));
        }
        BookDao bookDAO = new BookDao();
        try {
            bookDAO.addGenre(attributeList, genreCore, coreID);
        }catch (SQLException e){
            EXCEPTION_LOG.info("Error adding genre to database");
            request.setAttribute(EXCEPTION, "addGenreException");
            RequestDispatcher dispatcher = request.getRequestDispatcher(ERROR_PAGE);
            dispatcher.forward(request, response);
        }
        GoToMainPage goToMainPage = new GoToMainPage();
        goToMainPage.execute(request, response);
    }
}