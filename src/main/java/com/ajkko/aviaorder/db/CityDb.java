package com.ajkko.aviaorder.db;

import com.ajkko.aviaorder.objects.spr.City;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.ZoneId;
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
    private static final String COLUMN_TIME_ZONE = "time_zone";

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
        ResultSet resultSet = null;
        Collection<City> cities = new ArrayList<>();
        try {
            resultSet = statement.executeQuery(SQL_GET_CITIES);
            while (resultSet.next()) {
                cities.add(getMappedCity(resultSet));
            }
        } finally {
            closeResultSet(resultSet);
            closeStatement(statement);
        }
        return cities;
    }

    public City getCity(long id){
        try {
            return get(id);
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        } finally {
            MainDb.getInstance().closeConnection();
        }
        return null;
    }

    protected City get(long id) throws SQLException {
        return getCity(getByIdStatement(SQL_GET_CITY, id));
    }

    private City getCity(PreparedStatement statement) throws SQLException {
        ResultSet resultSet = null;
        try {
            resultSet = statement.executeQuery();
            resultSet.next();
            return getMappedCity(resultSet);
        } finally {
            closeResultSet(resultSet);
            closeStatement(statement);
        }
    }

    private City getMappedCity(ResultSet resultSet) throws SQLException {
        City city = new City();
        city.setId(resultSet.getLong(COLUMN_ID));
        city.setName(resultSet.getString(COLUMN_NAME));
        city.setCode(resultSet.getString(COLUMN_CODE));
        city.setDesc(resultSet.getString(COLUMN_DESC));
        city.setTimeZone(ZoneId.of(resultSet.getString(COLUMN_TIME_ZONE)));
        city.setCountry(CountryDb.getInstance().
                get(resultSet.getLong(COLUMN_COUNTRY_ID)));
        return city;
    }
}
