package com.epam.library.action;

import com.epam.library.Dao.UserDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mindrot.jbcrypt.BCrypt;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

import static com.epam.library.constants.ActionConstants.*;

public class AddNewUser implements Action {
    private final static Logger EXCEPTION_LOG = LogManager.getLogger(EXCEPTION_LOG_NAME);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        password = hashPassword(password);
        String firstName = request.getParameter("firstName");
        String secondName = request.getParameter("secondName");
        UserDao userDAO = new UserDao();
        try {
            userDAO.addUser(login, password, firstName, secondName);
            GoToMainPage goToMainPage = new GoToMainPage();
            goToMainPage.execute(request, response);
        } catch (SQLException e) {
            EXCEPTION_LOG.info("Error registering a new user");
            request.setAttribute(EXCEPTION, "addUserException");
            RequestDispatcher dispatcher = request.getRequestDispatcher(ERROR_PAGE);
            dispatcher.forward(request, response);
        }
    }

    private static String hashPassword(String password) {
        String salt = BCrypt.gensalt(SALT_FOR_HASH);
        String hashedPassword = BCrypt.hashpw(password, salt);
        return hashedPassword;
    }
}
