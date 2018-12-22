package com.epam.library.action.service;

import com.epam.library.Dao.InterfaceDao;
import com.epam.library.action.Action;
import com.epam.library.entity.UserLocale;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.epam.library.constants.ActionConstants.LANG_ID;
import static com.epam.library.constants.ActionConstants.LANG_LOCAL;

public class GetLang implements Action {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        InterfaceDao interfaceDAO = new InterfaceDao();
        List<UserLocale> langList = new ArrayList<>();
        interfaceDAO.getLangs(langList);
        request.setAttribute("langList", langList);
        Integer langID;
        if (request.getSession().getAttribute(LANG_ID) == null) {
            langID = 1;
            request.getSession().setAttribute(LANG_ID, langID);
        }
        String langLocal;
        if (request.getSession().getAttribute(LANG_LOCAL) == null) {
            langLocal = "en";
            request.getSession().setAttribute(LANG_LOCAL, langLocal);
        }
    }
}
