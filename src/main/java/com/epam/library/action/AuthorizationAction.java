package com.epam.library.action;

import static com.epam.library.constants.ActionConstants.ERROR_PAGE;
import static com.epam.library.constants.ActionConstants.EXCEPTION;
import static com.epam.library.constants.ActionConstants.EXCEPTION_LOG_NAME;
import static com.epam.library.constants.ActionConstants.USER_ID;
import static com.epam.library.constants.ActionConstants.USER_ROLE_ID;

import com.epam.library.Dao.UserDao;
import com.epam.library.entity.User;
import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mindrot.jbcrypt.BCrypt;

public class AuthorizationAction implements Action {
    private final static Logger EXCEPTION_LOG = LogManager.getLogger(EXCEPTION_LOG_NAME);


    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        int adminRoleID = 1;
        int userRoleID = 2;
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        UserDao userDAO = new UserDao();
        try {
            User user = userDAO.getUser(login);

            RequestDispatcher dispatcher;
            if (user != null && checkPassword(password, user.getPassword())) {
                if (user.getRole().equals("admin")){
                    request.getSession().setAttribute(USER_ROLE_ID, adminRoleID);
                    request.getSession().setAttribute(USER_ID, user.getId());
                }else {
                    request.getSession().setAttribute(USER_ROLE_ID, userRoleID);
                    request.getSession().setAttribute(USER_ID, user.getId());
                }
                request.getSession().setAttribute("userName", user.getFirstName());
                GoToMainPage goToMainPage = new GoToMainPage();
                goToMainPage.execute(request, response);
            } else {
                request.setAttribute(EXCEPTION, "authorizationException");
                dispatcher = request.getRequestDispatcher(ERROR_PAGE);
                dispatcher.forward(request, response);
            }
        } catch (SQLException e) {
            EXCEPTION_LOG.info("User authorization failed");
        }
    }

    private static boolean checkPassword(String password, String hashFromBD) {
        boolean passwordVerified = false;
        if (hashFromBD == null) {
            throw new java.lang.IllegalArgumentException("Invalid hash provided for comparison");
        } else {
            try {
                passwordVerified = BCrypt.checkpw(password, hashFromBD);
            }catch (IllegalArgumentException e){
                //FIX MEEEE!!!!!!!!!!
            }
        }
        return (passwordVerified);
    }
}
