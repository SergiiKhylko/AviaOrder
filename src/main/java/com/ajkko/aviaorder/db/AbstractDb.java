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

    protected void executeInsert(T object, String query) throws SQLException {
        PreparedStatement statement = getPrepareStatement(query);
        prepareStatement(statement, object);
        statement.executeUpdate();
    }

    protected abstract void prepareStatement(PreparedStatement statement, T object) throws SQLException;

}
