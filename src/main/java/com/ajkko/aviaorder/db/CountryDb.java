package com.ajkko.aviaorder.db;

import com.ajkko.aviaorder.objects.spr.Country;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

public class CountryDb {

    private static CountryDb instance;
    private static final Logger LOG = LogManager.getLogger(CountryDb.class);
    private static final String SQL_GET_COUNTRIES = "select * from FlightDB.spr_country";
    private static final String SQL_GET_COUNTRY_BY_ID = "select * from FlightDB.spr_country where id = ?";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_SHORT_NAME = "short_name";
    private static final String COLUMN_FLAG = "flag";

    private CountryDb(){
    }

    public static CountryDb getInstance(){
        if (instance == null){
            instance = new CountryDb();
        }
        return instance;
    }

    public Collection<Country> getCountries(){
        try {
            return getCountries(MainDb.getInstance().getStatement());
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        } finally {
            MainDb.getInstance().closeConnection();
        }
        return new ArrayList<>();
    }

    private Collection<Country>getCountries(Statement statement) throws SQLException {
        ResultSet resultSet = statement.executeQuery(SQL_GET_COUNTRIES);
        Collection<Country> countries = new ArrayList<>();

        while(resultSet.next()){
            Country country = new Country();
            country.setId(resultSet.getLong(COLUMN_ID));
            country.setName(resultSet.getString(COLUMN_NAME));
            country.setShortName(resultSet.getString(COLUMN_SHORT_NAME));
            country.setFlag(resultSet.getBytes(COLUMN_FLAG));

            countries.add(country);
        }

        return countries;
    }

    public Country getCountry(long id) {
        try {
            return getCountry(getCountryStatement(id));
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        } finally {
           // MainDb.getInstance().closeConnection();
        }
        return null;
    }

    private PreparedStatement getCountryStatement(long id) throws SQLException {
        Connection connection = MainDb.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement(SQL_GET_COUNTRY_BY_ID);
        statement.setLong(1, id);
        return statement;
    }

    private Country getCountry(PreparedStatement statement) throws SQLException {
        Country country = new Country();
        ResultSet resultSet = null;
        try {
            resultSet = statement.executeQuery();
            resultSet.next();

            country.setId(resultSet.getLong(COLUMN_ID));
            country.setName(resultSet.getString(COLUMN_NAME));
            country.setShortName(resultSet.getString(COLUMN_SHORT_NAME));
            country.setFlag(resultSet.getBytes(COLUMN_FLAG));
        } finally {
            closeResultSet(resultSet);
            closeStatement(statement);
        }

       return country;
    }

    private void closeResultSet(ResultSet resultSet) throws SQLException {
        if (resultSet!=null) {
            resultSet.close();
        }
    }

    private void closeStatement(Statement statement) throws SQLException {
        if (statement!=null) {
            statement.close();
        }
    }
}
