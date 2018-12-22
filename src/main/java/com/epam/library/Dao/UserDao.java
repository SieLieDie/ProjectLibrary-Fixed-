package com.epam.library.Dao;

import com.epam.library.connection.ConnectionPool;
import com.epam.library.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.epam.library.constants.ActionConstants.EXCEPTION_LOG_NAME;

public class UserDao {
    private static final String SELECT_USER = "SELECT user_id, login, password, role_name, first_name, second_name FROM user JOIN user_role " +
            "USING (role_id) WHERE login = ?";
    private static final String ADD_USER = "INSERT INTO user (login, password, role_id, first_name, second_name) VALUES (?, ?, 2, ?, ?);";
    private static final String ADD_TO_FAVORITE = "INSERT INTO monitored(user_id, book_id) VALUES (?, ?);";
    private final static Logger EXCEPTION_LOG = LogManager.getLogger(EXCEPTION_LOG_NAME);

    public User getUser(String login) throws SQLException {
        User user = null;
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = connectionPool.retrieve();
        PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER);
        preparedStatement.setString(1, login);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            user = new User(resultSet.getInt("user_id"), resultSet.getString("login"), resultSet.getString("password"),
                    resultSet.getString("role_name"), resultSet.getString("first_name"),
                    resultSet.getString("second_name"));
        }
        connectionPool.putBack(connection);
        return user;
    }

    public void addUser(String login, String password, String firstName, String secondName) throws SQLException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = connectionPool.retrieve();
        try (PreparedStatement preparedStatement = connection.prepareStatement(ADD_USER)) {
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, firstName);
            preparedStatement.setString(4, secondName);
            preparedStatement.executeUpdate();
        }
        connectionPool.putBack(connection);
    }

    public void addToFavorite(int userID, int bookID) {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = connectionPool.retrieve();
        try {
            try (PreparedStatement preparedStatement = connection.prepareStatement(ADD_TO_FAVORITE)) {
                preparedStatement.setInt(1, userID);
                preparedStatement.setInt(2, bookID);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            EXCEPTION_LOG.info("Exception in DAOMethod 'addToFavorite'\n" + e);
        }
        connectionPool.putBack(connection);
    }
}
