package com.ajkko.aviaorder.db;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.apache.commons.dbcp2.BasicDataSource;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;


public class MainDb {

    private static final Logger LOG = LogManager.getLogger(MainDb.class);


    private Connection connection;
    private static MainDb instance;
    private static BasicDataSource dataSource;

    private static final String DB_PROPERTY_FILE = "src/main/resources/db.properties";
    private static final String JDBC_DRIVER_PROPERTY = "jdbc.Driver";
    private static final String DB_PROPERTY_HOST = "db.host";
    private static final String DB_PROPERTY_LOGIN = "db.login";
    private static final String DB_PROPERTY_PASSWORD = "db.password";

    private MainDb(){
    }

    public static MainDb getInstance(){
        if (instance == null){
            instance = new MainDb();
        }
        return instance;
    }

    public Connection getConnection(){
        if (connection == null || isConnectionClosed()){
            connection = getConnectionFromDataSource();
            LOG.info("Connection is created");
        }
        return connection;
    }

    private static BasicDataSource getDataSource(){
        if (dataSource == null){
            Properties properties = getDbProperties();

            BasicDataSource ds = new BasicDataSource();
            ds.setDriverClassName(properties.getProperty(JDBC_DRIVER_PROPERTY));
            ds.setUrl(properties.getProperty(DB_PROPERTY_HOST));
            ds.setUsername(properties.getProperty(DB_PROPERTY_LOGIN));
            ds.setPassword(properties.getProperty(DB_PROPERTY_PASSWORD));
            ds.setMinIdle(5);
            ds.setMaxIdle(10);
            ds.setMaxOpenPreparedStatements(100);
            dataSource = ds;
        }
        return dataSource;
    }

    private Connection createConnection(){
        Properties properties = getDbProperties();

        try {
            LOG.info("Connecting to DataBase");
            Class.forName(properties.getProperty(JDBC_DRIVER_PROPERTY));
            LOG.info("MySQL JDBC Driver Registered!");
            return DriverManager.getConnection(
                    properties.getProperty(DB_PROPERTY_HOST),
                    properties.getProperty(DB_PROPERTY_LOGIN),
                    properties.getProperty(DB_PROPERTY_PASSWORD));

        } catch (ClassNotFoundException e) {
            LOG.error("MySQL JDBC Driver is not loaded", e);
        } catch (SQLException e) {
            LOG.error("Connection Failed! Check output console", e);
        }
        return connection;
    }

    private Connection getConnectionFromDataSource(){
        try {
            return getDataSource().getConnection();
        } catch (SQLException e) {
            LOG.error("Connection Failed! Check output console", e);
        }
        return null;
    }

    private boolean isConnectionClosed(){
        boolean result = true;
        try {
            result = connection.isClosed();
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
        return result;
    }

    public void closeConnection(){
        if (connection == null){
            return;
        }
        try {
            connection.close();
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
    }

    public Statement getStatement() throws SQLException {
        return getConnection().createStatement();
    }

    private static Properties getDbProperties(){
        FileInputStream fis;
        Properties property = new Properties();

        try {
            fis = new FileInputStream(DB_PROPERTY_FILE);
            property.load(fis);
        } catch (FileNotFoundException e) {
            LOG.error("db.properties file is not found", e);
        } catch (IOException e) {
            LOG.error("Cannot load properties from db.properties file", e);
        }

        return property;
    }


}
