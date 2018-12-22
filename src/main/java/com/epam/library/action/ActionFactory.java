package com.epam.library.action;

import com.epam.library.action.service.ChangeLang;
import com.epam.library.action.service.ExitFromSession;

import java.util.HashMap;
import java.util.Map;

public class ActionFactory {
    private static ActionFactory actionFactory;
    private static Map<String, Action> actionMap = new HashMap<String, Action>();

    public ActionFactory() {
        init();
    }

    private void init() {
        actionMap.put("/do/goToMainPage", new GoToMainPage());
        actionMap.put("/do/authorization", new AuthorizationAction());
        actionMap.put("/do/showBookByGenre", new ShowBookByGenre());
        actionMap.put("/do/showBookByAuthor", new ShowBookByAuthor());
        actionMap.put("/do/searchBookByName", new SearchBookByName());
        actionMap.put("/do/exitFromSession", new ExitFromSession());
        actionMap.put("/do/changeLang", new ChangeLang());
        actionMap.put("/do/registration", new AddNewUser());
        actionMap.put("/do/addBook", new AddBook());
        actionMap.put("/do/addAuthor", new AddAuthor());
        actionMap.put("/do/addGenre", new AddGenre());
        actionMap.put("/do/removeBook", new RemoveBook());
        actionMap.put("/do/removeAuthor", new RemoveAuthor());
        actionMap.put("/do/removeGenre", new RemoveGenre());
        actionMap.put("/do/addToFavorite", new AddToFavorite());
        actionMap.put("/do/removeFromFavorite", new RemoveFromFavorite());
        actionMap.put("/do/showFavorite", new ShowFavorite());
    }

    public static ActionFactory getInstance() {
        if (actionFactory == null) {
            actionFactory = new ActionFactory();
            return actionFactory;
        } else {
            return actionFactory;
        }
    }

    public Action getAction(String actionRequest) {
        Action action = actionMap.get(actionRequest);
        return action;
    }
}