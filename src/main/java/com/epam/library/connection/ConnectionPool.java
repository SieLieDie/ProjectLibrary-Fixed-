package com.epam.library.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ResourceBundle;
import java.util.concurrent.ArrayBlockingQueue;

public class ConnectionPool {

    private static ConnectionPool connectionPool;
    ResourceBundle bundle = ResourceBundle.getBundle("ConnectionData");
    private String driver = bundle.getString("driver");
    private String url = bundle.getString("url");
    private String user = bundle.getString("user");
    private String password = bundle.getString("password");
    private int initConnectionCount = Integer.parseInt(bundle.getString("initConnectionCount"));
    private ArrayBlockingQueue<Connection> connectionsQueue = new ArrayBlockingQueue<Connection>(initConnectionCount);

    private ConnectionPool() {
        init();
    }

    private void init() {
        try {
            Class.forName(driver);
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (int i = 1; i <= initConnectionCount; i++) {
            try {
                connectionsQueue.put(getConnection());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private Connection getConnection() {
        Connection dbConnection = null;
        try {
            dbConnection = DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dbConnection;
    }

    public static ConnectionPool getInstance() {
        if (connectionPool == null) {
            connectionPool = new ConnectionPool();
            return connectionPool;
        } else {
            return connectionPool;
        }

    }

    public synchronized Connection retrieve() {
        Connection newConnectionDB = null;
        try {
            newConnectionDB = connectionsQueue.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return newConnectionDB;
    }

    public synchronized void putBack(Connection connectionDB) throws NullPointerException {
        if (connectionDB != null) {
            try {
                connectionsQueue.put(connectionDB);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            throw new NullPointerException("Connection not found");
        }
    }
}

