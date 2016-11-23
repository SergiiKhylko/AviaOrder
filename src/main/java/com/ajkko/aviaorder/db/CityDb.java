package com.ajkko.aviaorder.db;

import com.ajkko.aviaorder.objects.spr.City;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;


public class CityDb {

    private static CityDb instance;
    private static final Logger LOG = LogManager.getLogger(CityDb.class);
    private static final String SQL_GET_CITIES = "select * from FlightDB.spr_city";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_POSTCODE = "postcode";
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

        while(resultSet.next()){
            City city = new City();
            city.setId(resultSet.getLong(COLUMN_ID));
            city.setName(resultSet.getString(COLUMN_NAME));
            city.setCode(resultSet.getString(COLUMN_POSTCODE));
            city.setCountry(CountryDb.getInstance().
                    getCountry(resultSet.getLong(COLUMN_COUNTRY_ID)));
            cities.add(city);
        }

        return cities;
    }

    public City getCity(long id){
        try {
            return getCity(MainDb.getInstance().getStatement());
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        } finally {
            MainDb.getInstance().closeConnection();
        }
        return null;
    }

    private City getCity(Statement statement) {
        return null;
    } //todo

}
