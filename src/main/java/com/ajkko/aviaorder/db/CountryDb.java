package com.ajkko.aviaorder.db;

import com.ajkko.aviaorder.objects.Country;
import com.ajkko.aviaorder.utils.DbUtils;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

import static com.ajkko.aviaorder.utils.DbUtils.closeResultSet;
import static com.ajkko.aviaorder.utils.DbUtils.closeStatement;

public class CountryDb {

    private static CountryDb instance;
    private static final Logger LOG = LogManager.getLogger(CountryDb.class);
    private static final String SQL_GET_COUNTRIES = "select * from FlightDB.country";
    private static final String SQL_GET_COUNTRY_BY_ID = "select * from FlightDB.country where id = ?";
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
        ResultSet resultSet = null;
        Collection<Country> countries = new ArrayList<>();
        try {
            resultSet = statement.executeQuery(SQL_GET_COUNTRIES);
            while (resultSet.next()) {
                countries.add(getMappedCountry(resultSet));
            }
        } finally {
            closeResultSet(resultSet);
            closeStatement(statement);
        }
        return countries;
    }

    public Country getCountry(long id) {
        try {
            return get(id);
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        } finally {
            MainDb.getInstance().closeConnection();
        }
        return null;
    }

    protected Country get(long id) throws SQLException {
        return getCountry(DbUtils.getByIdStatement(SQL_GET_COUNTRY_BY_ID, id));
    }

    private Country getCountry(PreparedStatement statement) throws SQLException {
        ResultSet resultSet = null;
        try {
            resultSet = statement.executeQuery();
            resultSet.next();
            return getMappedCountry(resultSet);
        } finally {
            closeResultSet(resultSet);
            closeStatement(statement);
        }
    }

    private Country getMappedCountry(ResultSet resultSet) throws SQLException {
        Country country = new Country();
        country.setId(resultSet.getLong(COLUMN_ID));
        country.setName(resultSet.getString(COLUMN_NAME));
        country.setShortName(resultSet.getString(COLUMN_SHORT_NAME));
        country.setFlag(resultSet.getBytes(COLUMN_FLAG));
        return country;
    }
}
