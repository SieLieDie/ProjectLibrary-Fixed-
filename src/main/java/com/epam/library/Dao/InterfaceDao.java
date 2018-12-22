package com.epam.library.Dao;

import com.epam.library.connection.ConnectionPool;
import com.epam.library.entity.UserLocale;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static com.epam.library.constants.ActionConstants.EXCEPTION_LOG_NAME;

public class InterfaceDao {
    private static final String GET_ALL_LANGUAGES = "SELECT lang_id, lang_name, lang_local FROM interface_lang";
    private static final String GET_LOCALE_BY_ID = "SELECT lang_local FROM interface_lang WHERE lang_id = ?";
    private static final String LOCAL = "lang_local";
    private static final Logger EXCEPTION_LOG = LogManager.getLogger(EXCEPTION_LOG_NAME);

    public void getLangs(List<UserLocale> langList){
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = connectionPool.retrieve();
        try {
            try (PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_LANGUAGES);
                 ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    langList.add(new UserLocale(resultSet.getInt("lang_id"), resultSet.getString("lang_name"), resultSet.getString(LOCAL)));
                }
            }
        } catch (SQLException e){
            EXCEPTION_LOG.info("Exception in DAOMethod 'getLangs'\n" + e);
        }
        connectionPool.putBack(connection);
    }

    public String getLocalById(int langID) {
        String local = null;
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = connectionPool.retrieve();
        try {
            try (PreparedStatement preparedStatement = connection.prepareStatement(GET_LOCALE_BY_ID)) {
                preparedStatement.setInt(1, langID);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        local = resultSet.getString(LOCAL);
                    }
                }
            }
        }catch (SQLException e){
            EXCEPTION_LOG.info("Exception in DAOMethod 'getLocalByID'\n" + e);
        }
        return local;
    }
}
