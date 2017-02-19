package com.ajkko.aviaorder.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import static com.ajkko.aviaorder.utils.DbUtils.closeResultSet;
import static com.ajkko.aviaorder.utils.DbUtils.closeStatement;
import static com.ajkko.aviaorder.utils.DbUtils.getPrepareStatement;

public abstract class AbstractDb<T> {

    protected abstract T mapFromResultSet(ResultSet resultSet) throws SQLException;

    protected Collection<T> getCollection(PreparedStatement statement) throws SQLException {
        ResultSet resultSet = null;
        Collection<T> objects = new ArrayList<>();
        try {
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                objects.add(mapFromResultSet(resultSet));
            }
        } finally {
            closeStatement(statement);
            closeResultSet(resultSet);
        }
        return objects;
    }

    protected T getObject(PreparedStatement statement) throws SQLException{
        ResultSet resultSet = null;
        try {
            resultSet = statement.executeQuery();
            resultSet.next();
            return mapFromResultSet(resultSet);
        } finally {
            closeResultSet(resultSet);
            closeStatement(statement);
        }
    }

    protected void executeInsert(T object, String query) throws SQLException { //TODO close connection, statement
        PreparedStatement statement = getPrepareStatement(query);
        prepareUpdateStatement(statement, object);
        statement.executeUpdate();
    }

    protected abstract void prepareUpdateStatement(PreparedStatement statement, T object) throws SQLException;

    public Collection<T> getCollection(String query){
        return getCollection(query, null);
    }

    public Collection<T> getCollection(String query, StatementPreparator preparator){
        try {
            PreparedStatement statement = getPrepareStatement(query);
            prepareStatement(preparator, statement);
            return getCollection(statement);
        } catch (SQLException e) {
            logError(e);
        } finally {
            MainDb.getInstance().closeConnection();
        }
        return new ArrayList<>();
    }

    private void prepareStatement(StatementPreparator preparator,
                                  PreparedStatement statement) throws SQLException {
        if (preparator != null) {
            preparator.prepare(statement);
        }
    }

    protected abstract void logError(Exception e);

    protected T getByIdAndCloseConnection(long id) {
        try {
            return getById(id);
        } catch (SQLException e) {
            logError(e);
        } finally {
            MainDb.getInstance().closeConnection();
        }
        return null;
    }

    protected abstract T getById(long id) throws SQLException;

}
