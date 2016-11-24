package com.ajkko.aviaorder.db;

import com.ajkko.aviaorder.objects.spr.City;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

import static com.ajkko.aviaorder.utils.DbUtils.closeResultSet;
import static com.ajkko.aviaorder.utils.DbUtils.closeStatement;
import static com.ajkko.aviaorder.utils.DbUtils.getByIdStatement;


public class CityDb {

    private static CityDb instance;
    private static final Logger LOG = LogManager.getLogger(CityDb.class);
    private static final String SQL_GET_CITIES = "select * from FlightDB.spr_city";
    private static final String SQL_GET_CITY = "select * from FlightDB.spr_city where id = ?";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_CODE = "code";
    private static final String COLUMN_DESC = "desc";
    private static final String COLUMN_COUNTRY_ID = "country_id";

    private CityDb(){
    }

    public static CityDb getInstance(){
        if (instance == null){
            instance = new CityDb();
        }
        return instance;
    }

    public Collection<City> getCities(){
        try {
            return getCities(MainDb.getInstance().getStatement());
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        } finally {
            MainDb.getInstance().closeConnection();
        }
        return new ArrayList<>();
    }

    private Collection<City> getCities(Statement statement) throws SQLException {
        ResultSet resultSet = statement.executeQuery(SQL_GET_CITIES);
        Collection<City> cities = new ArrayList<>();
        try {
            while (resultSet.next()) {
                City city = new City();
                fillCity(city, resultSet);
                cities.add(city);
            }
        } finally {
            closeResultSet(resultSet);
            closeStatement(statement);
        }
        return cities;
    }

    public City getCity(long id){
        try {
            return getCity(getByIdStatement(SQL_GET_CITY, id));
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        } finally {
            MainDb.getInstance().closeConnection();
        }
        return null;
    }

    private City getCity(PreparedStatement statement) throws SQLException {
        City city = new City();
        ResultSet resultSet = null;
        try {
            resultSet = statement.executeQuery();
            resultSet.next();
            fillCity(city, resultSet);
        } finally {
            closeResultSet(resultSet);
            closeStatement(statement);
        }
        return null;
    }

    private void fillCity(City city, ResultSet resultSet) throws SQLException {
        city.setId(resultSet.getLong(COLUMN_ID));
        city.setName(resultSet.getString(COLUMN_NAME));
        city.setCode(resultSet.getString(COLUMN_CODE));
        city.setDesc(resultSet.getString(COLUMN_DESC));
        city.setCountry(CountryDb.getInstance().
                getCountry(resultSet.getLong(COLUMN_COUNTRY_ID)));
    }

}
