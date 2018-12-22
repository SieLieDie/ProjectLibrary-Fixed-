
package com.epam.library.Dao;

import com.epam.library.connection.ConnectionPool;
import com.epam.library.entity.Attribute;
import com.epam.library.entity.Book;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.epam.library.constants.ActionConstants.EXCEPTION_LOG_NAME;

public class BookDao {
    private static final String RETURN_ALL_BOOK = "SELECT book_id, book_name, concat(author_first_name,\" \", author_second_name) author_name, " +
            "genre_name, in_stock, description " +
            "FROM book JOIN book_bounds_author USING (book_id) JOIN book_author USING (author_id) JOIN book_bounds_genre USING (book_id) " +
            "JOIN book_genre USING (genre_id) WHERE author_lang_id = ? AND genre_lang_id = ?";
    private static final String RETURN_FAVORITE = "SELECT book_id, book_name, concat(author_first_name,\" \", author_second_name) author_name," +
            " genre_name, in_stock, description  " +
            "FROM book JOIN book_bounds_author USING (book_id) JOIN book_author USING (author_id) JOIN book_bounds_genre USING (book_id) " +
            "JOIN book_genre USING (genre_id) JOIN monitored USING(book_id) WHERE user_id = ? AND author_lang_id = ? AND genre_lang_id = ?;";
    private static final String SEARCH_THE_BOOK = "SELECT book_id, book_name, concat(author_first_name,\" \", author_second_name) author_name, " +
            "genre_name, in_stock, description " +
            "FROM book JOIN book_bounds_author USING (book_id) JOIN book_author USING (author_id) " +
            "JOIN book_bounds_genre USING (book_id) JOIN book_genre USING (genre_id) " +
            "WHERE author_lang_id = ? AND genre_lang_id = ? AND book_name LIKE ";
    private static final String RETURN_BOOK_BY_GENRE = "SELECT book_id, book_name, concat(author_first_name,\" \", author_second_name) author_name," +
            " genre_name, in_stock, description FROM book JOIN book_bounds_author USING (book_id) JOIN book_author USING (author_id) " +
            "JOIN book_bounds_genre USING (book_id) JOIN book_genre USING (genre_id) " +
            "WHERE genre_id = ? AND author_lang_id = ? AND genre_lang_id = ?;";
    private static final String RETURN_BOOK_BY_AUTHOR = "SELECT book_id, book_name, concat(author_first_name,\" \", author_second_name) author_name," +
            " genre_name, in_stock, description " +
            "FROM book JOIN book_bounds_author USING (book_id) JOIN book_author USING (author_id) " +
            "JOIN book_bounds_genre USING (book_id) JOIN book_genre USING (genre_id) " +
            "WHERE author_id = ? AND author_lang_id = ? AND genre_lang_id = ?;";
    private static final String RETURN_GENRES_FOR_BOOK = "SELECT genre_id, genre_name, genre_core_id FROM book_genre WHERE genre_lang_id = ";
    private static final String RETURN_AUTHOR_FOR_BOOK = "SELECT author_id, concat(author_first_name,\" \", author_second_name) author_name," +
            "author_core_id FROM book_author WHERE author_lang_id = ";
    private static final String ADD_BOOK = "INSERT INTO book (book_name, in_stock, description) VALUES (?, ?, ?)";
    private static final String ADD_AUTHOR_CORE = "INSERT INTO book_author_core (core_first_name, core_second_name) VALUES (?, ?)";
    private static final String ADD_GENRE_CORE = "INSERT INTO book_genre_core (core_name) VALUES (?)";
    private static final String SELECT_ID_AUTHOR_CORE = "SELECT core_id FROM book_author_core WHERE core_first_name = ? AND core_second_name = ?;";
    private static final String SELECT_ID_GENRE_CORE = "SELECT core_id FROM book_genre_core WHERE core_name = ?;";
    private static final String SELECT_ID_BOOK = "SELECT book_id FROM book WHERE book_name = ?;";
    private static final String SELECT_ID_GENRE_BY_CORE = "SELECT genre_id FROM book_genre WHERE genre_core_id = ?;";
    private static final String SELECT_ID_AUTHOR = "SELECT author_id FROM book_author WHERE author_core_id = ?;";
    private static final String ADD_BOUNDS_BOOK_WITH_AUTHOR = "INSERT INTO book_bounds_author (book_id, author_id) VALUES (?, ?);";
    private static final String ADD_BOUNDS_BOOK_WITH_GENRE = "INSERT INTO book_bounds_genre (book_id, genre_id) VALUES (?, ?);";
    private static final String ADD_AUTHOR = "INSERT INTO book_author(author_first_name, author_lang_id, author_core_id," +
            " author_second_name) VALUES (?, ?, ?, ?);";
    private static final String ADD_GENRE = "INSERT INTO book_genre(genre_name, genre_lang_id, genre_core_id) VALUES (?, ?, ?);";
    private static final String REMOVE_BOOK = "DELETE FROM book WHERE (book_id = ?)";
    private static final String SELECT_ID_BOUND_AUTHOR = "SELECT id FROM book_bounds_author WHERE (book_id = ?)";
    private static final String SELECT_ID_BOUND_GENRE = "SELECT id FROM book_bounds_genre WHERE (book_id = ?)";
    private static final String SELECT_ID_BOUND_FAVORITE = "SELECT id FROM monitored WHERE (book_id = ?) AND (user_id = ?)";
    private static final String REMOVE_BOUNDS_BOOK_WITH_AUTHOR = "DELETE FROM book_bounds_author WHERE (id = ?)";
    private static final String REMOVE_BOUNDS_BOOK_WITH_GENRE = "DELETE FROM book_bounds_genre WHERE (id = ?)";
    private static final String REMOVE_BOUNDS_BOOK_WITH_USER = "DELETE FROM monitored WHERE (id = ?)";
    private static final String REMOVE_AUTHOR = "DELETE FROM book_author WHERE author_core_id = ?";
    private static final String REMOVE_AUTHOR_CORE = "DELETE FROM book_author_core WHERE core_id = ?";
    private static final String REMOVE_GENRE = "DELETE FROM book_genre WHERE genre_core_id = ?";
    private static final String REMOVE_GENRE_CORE = "DELETE FROM book_genre_core WHERE core_id = ?";

    private static final String GENRE_ID = "genre_id";
    private static final String AUTHOR_ID = "author_id";
    private static final String BOOK_ID = "book_id";
    private static final Logger EXCEPTION_LOG = LogManager.getLogger(EXCEPTION_LOG_NAME);

    public void getAllBookFromDb(List<Book> bookList, int langID, Integer userID) {
        try {
            getBookFromDb(bookList, langID, RETURN_ALL_BOOK);
            List<Book> bookFavoriteList = new ArrayList<>();
            if (userID != null) {
                showFavorite(bookFavoriteList, langID, userID);
                markAsFavoriteBook(bookList, bookFavoriteList);
            }
        } catch (SQLException e) {
            EXCEPTION_LOG.info("Error retrieving books from the database");
        }
    }

    private void getBookFromDb(List<Book> bookList, int langID, String sql) throws SQLException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = connectionPool.retrieve();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, langID);
            preparedStatement.setInt(2, langID);
            completeList(preparedStatement, bookList);
        }
        connectionPool.putBack(connection);
    }

    private void markAsFavoriteBook(List<Book> bookList, List<Book> bookFavoriteList) {
        for (Book book : bookList) {
            for (Book favoriteBook : bookFavoriteList) {
                if (book.getId() == favoriteBook.getId()) {
                    book.setIsFavorite(true);
                }
            }
        }
    }

    private void completeList(PreparedStatement preparedStatement, List<Book> bookList) throws SQLException {
        try (ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                bookList.add(new Book(resultSet.getInt(BOOK_ID), resultSet.getString("book_name"), //in_stock instead of in_stoke
                        resultSet.getString("author_name"), resultSet.getString("genre_name"), resultSet.getInt("in_stock"),
                        resultSet.getString("description"), false));
            }
        }
    }

    public void getBookByGenre(List<Book> bookList, int genreID, int langID, Integer userID) {
        try {
            getBookListForAttribute(bookList, genreID, langID, RETURN_BOOK_BY_GENRE);
            List<Book> bookFavoriteList = new ArrayList<>();
            if (userID != null) {
                showFavorite(bookFavoriteList, langID, userID);
                markAsFavoriteBook(bookList, bookFavoriteList);
            }
        } catch (SQLException e) {
            EXCEPTION_LOG.info("Error displaying books from the database by selected genre");
        }
    }

    public void getBookByAuthor(List<Book> bookList, int authorID, int langID, Integer userID) {
        try {
            getBookListForAttribute(bookList, authorID, langID, RETURN_BOOK_BY_AUTHOR);
            List<Book> bookFavoriteList = new ArrayList<>();
            if (userID != null) {
                showFavorite(bookFavoriteList, langID, userID);
                markAsFavoriteBook(bookList, bookFavoriteList);
            }
        } catch (SQLException e) {
            EXCEPTION_LOG.info("Error displaying books from the database by selected author");
        }
    }

    public void searchBook(List<Book> bookList, String bookName, int langID, Integer userID) {
        try {
            getBookFromDb(bookList, langID, SEARCH_THE_BOOK.concat("'%").concat(bookName).concat("%'"));
            List<Book> bookFavoriteList = new ArrayList<>();
            if (userID != null) {
                showFavorite(bookFavoriteList, langID, userID);
                markAsFavoriteBook(bookList, bookFavoriteList);
            }
        } catch (SQLException e) {
            EXCEPTION_LOG.info("Book Search Error");
        }
    }

    public void showFavorite(List<Book> bookList, int langID, int userID) {
        try {
            getBookListForAttribute(bookList, userID, langID, RETURN_FAVORITE);
            for (Book book : bookList) {
                book.setIsFavorite(true);
            }
        } catch (SQLException e) {
            EXCEPTION_LOG.info("Error withdrawing selected books from the database");
        }
    }

    private void getBookListForAttribute(List<Book> bookList, int attributeID, int langID, String sql) throws SQLException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = connectionPool.retrieve();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, attributeID);
            preparedStatement.setInt(2, langID);
            preparedStatement.setInt(3, langID);
            completeList(preparedStatement, bookList);
        }
        connectionPool.putBack(connection);
    }

    public void getGenresForBook(List<Attribute> genreList, int langID) {
        try {
            getAttributeForBook(genreList, RETURN_GENRES_FOR_BOOK + langID, GENRE_ID, "genre_name", "genre_core_id");
        } catch (SQLException e) {
            EXCEPTION_LOG.info("Error getting genres for books from database");
        }
    }

    public void getAuthorForBook(List<Attribute> authorList, int langID) {
        try {
            getAttributeForBook(authorList, RETURN_AUTHOR_FOR_BOOK + langID, AUTHOR_ID, "author_name", "author_core_id");
        } catch (SQLException e) {
            EXCEPTION_LOG.info("Error getting authors for books from database");
        }
    }

    private void getAttributeForBook(List<Attribute> attributeList, String sql, String attributeID, String attributeName, String attributeCoreID) throws SQLException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = connectionPool.retrieve();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                attributeList.add(new Attribute(resultSet.getInt(attributeID), resultSet.getString(attributeName), resultSet.getInt(attributeCoreID)));
            }
        }
        connectionPool.putBack(connection);
    }

    public void addBook(String bookName, int authorCoreID, int genreCoreID, int inStoke, String description) throws SQLException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = connectionPool.retrieve();
        try (PreparedStatement preparedStatementAddBook = connection.prepareStatement(ADD_BOOK)) {
            preparedStatementAddBook.setString(1, bookName);
            preparedStatementAddBook.setInt(2, inStoke);
            preparedStatementAddBook.setString(3, description);
            preparedStatementAddBook.executeUpdate();
        }
        Integer bookID = null;
        try (PreparedStatement preparedStatementSelectID = connection.prepareStatement(SELECT_ID_BOOK)) {
            preparedStatementSelectID.setString(1, bookName);
            try (ResultSet resultSet = preparedStatementSelectID.executeQuery()) {
                while (resultSet.next()) {
                    bookID = resultSet.getInt(BOOK_ID);
                }
            }
        }
        createBounds(connection, SELECT_ID_GENRE_BY_CORE, genreCoreID, GENRE_ID, ADD_BOUNDS_BOOK_WITH_GENRE, bookID);
        createBounds(connection, SELECT_ID_AUTHOR, authorCoreID, AUTHOR_ID, ADD_BOUNDS_BOOK_WITH_AUTHOR, bookID);
        connectionPool.putBack(connection);
    }

    private void createBounds(Connection connection, String sqlSelectID, int attributeCoreID, String columnID, String sqlAddBounds, Integer bookID) throws SQLException {
        try (PreparedStatement preparedStatementSelectAuthorID = connection.prepareStatement(sqlSelectID)) {
            preparedStatementSelectAuthorID.setInt(1, attributeCoreID);
            int attributeID;
            try (ResultSet resultSet = preparedStatementSelectAuthorID.executeQuery()) {
                while (resultSet.next()) {
                    attributeID = resultSet.getInt(columnID);
                    try (PreparedStatement preparedStatementAddBoundGenre = connection.prepareStatement(sqlAddBounds)) {
                        preparedStatementAddBoundGenre.setInt(1, bookID);
                        preparedStatementAddBoundGenre.setInt(2, attributeID);
                        preparedStatementAddBoundGenre.executeUpdate();
                    }
                }
            }
        }
    }

    private void addAttributeCore(Attribute attribute, String sql) throws SQLException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = connectionPool.retrieve();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, attribute.getCoreName());
            if (attribute.getSecondCoreName() != null) {
                preparedStatement.setString(2, attribute.getSecondCoreName());
            }
            preparedStatement.executeUpdate();
        }
        connectionPool.putBack(connection);
    }

    public void addAuthor(List<Attribute> attributeList, Attribute authorCore, int coreID) throws SQLException {
        addAttributeCore(authorCore, ADD_AUTHOR_CORE);
        addAttribute(attributeList, authorCore, ADD_AUTHOR, SELECT_ID_AUTHOR_CORE, coreID);
    }

    public void addGenre(List<Attribute> inputAttributeList, Attribute genreCore, int coreID) throws SQLException {
        addAttributeCore(genreCore, ADD_GENRE_CORE);
        addAttribute(inputAttributeList, genreCore, ADD_GENRE, SELECT_ID_GENRE_CORE, coreID);

    }

    private void addAttribute(List<Attribute> attributeList, Attribute core, String sql, String sqlSelectID, int coreID) throws SQLException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = connectionPool.retrieve();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlSelectID)) {
            preparedStatement.setString(1, core.getCoreName());
            if (core.getSecondCoreName() != null) {
                preparedStatement.setString(2, core.getSecondCoreName());
            }
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                String idColumn = "core_id";
                while (resultSet.next()) {
                    coreID = resultSet.getInt(idColumn);

                }
            }
        }
        for (Attribute attribute : attributeList) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, attribute.getName());
                preparedStatement.setInt(2, attribute.getLangID());
                preparedStatement.setInt(3, coreID);
                if (attribute.getSecondName() != null) {
                    preparedStatement.setString(4, attribute.getSecondName());
                }
                preparedStatement.executeUpdate();
            }
        }
        connectionPool.putBack(connection);
    }

    public void removeBook(int bookID) {
        try {
            removeBounds(bookID, SELECT_ID_BOUND_AUTHOR, REMOVE_BOUNDS_BOOK_WITH_AUTHOR);
            removeBounds(bookID, SELECT_ID_BOUND_GENRE, REMOVE_BOUNDS_BOOK_WITH_GENRE);
            remove(bookID, REMOVE_BOOK);
        } catch (SQLException e) {
            EXCEPTION_LOG.info("Error removing book from database");
        }
    }


    private void removeBounds(int bookID, String sqlSelect, String sqlRemove) throws SQLException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = connectionPool.retrieve();
        try (PreparedStatement preparedStatementSelectID = connection.prepareStatement(sqlSelect)) {
            preparedStatementSelectID.setInt(1, bookID);
            try (ResultSet resultSet = preparedStatementSelectID.executeQuery()) {
                int boundID;
                while (resultSet.next()) {
                    boundID = resultSet.getInt("id");
                    try (PreparedStatement preparedStatementRemove = connection.prepareStatement(sqlRemove)) {
                        preparedStatementRemove.setInt(1, boundID);
                        preparedStatementRemove.executeUpdate();
                    }
                }
            }
        }
        connectionPool.putBack(connection);
    }

    public void removeGenre(int genreCoreID) {
        try {
            remove(genreCoreID, REMOVE_GENRE);
            remove(genreCoreID, REMOVE_GENRE_CORE);
        } catch (SQLException e) {
            EXCEPTION_LOG.info("Error removing genre from database");
        }
    }

    public void removeAuthor(int authorCoreID) {
        try {
            remove(authorCoreID, REMOVE_AUTHOR);
            remove(authorCoreID, REMOVE_AUTHOR_CORE);
        } catch (SQLException e) {
            EXCEPTION_LOG.info("Error removing author from database");
        }
    }

    private void remove(int id, String sql) throws SQLException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = connectionPool.retrieve();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        }
        connectionPool.putBack(connection);
    }


    public void removeFromFavorite(int bookID, int userID) {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = connectionPool.retrieve();
        int boundID = 0;
        try {
            try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ID_BOUND_FAVORITE)) {
                preparedStatement.setInt(1, bookID);
                preparedStatement.setInt(2, userID);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        boundID = resultSet.getInt("id");
                    }
                }
            }
            try (PreparedStatement preparedStatement = connection.prepareStatement(REMOVE_BOUNDS_BOOK_WITH_USER)) {
                preparedStatement.setInt(1, boundID);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            EXCEPTION_LOG.info("Error removing book from favorite list");
        }
        connectionPool.putBack(connection);
    }
}
