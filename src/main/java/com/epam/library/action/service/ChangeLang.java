package com.epam.library.action.service;

import com.epam.library.Dao.InterfaceDao;
import com.epam.library.action.Action;
import com.epam.library.action.GoToMainPage;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.epam.library.constants.ActionConstants.LANG_ID;
import static com.epam.library.constants.ActionConstants.LANG_LOCAL;

public class ChangeLang implements Action {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer langID = Integer.parseInt(request.getParameter(LANG_ID));
        InterfaceDao interfaceDAO = new InterfaceDao();
        String langLocal = interfaceDAO.getLocalById(langID);
        request.getSession().setAttribute(LANG_ID, langID);
        request.getSession().setAttribute(LANG_LOCAL, langLocal);
        GoToMainPage goToMainPage = new GoToMainPage();
        goToMainPage.execute(request, response);
    }
}
