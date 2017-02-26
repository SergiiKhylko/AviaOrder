package com.ajkko.aviaorder.db;

import com.ajkko.aviaorder.objects.City;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZoneId;
import java.util.Collection;

import static com.ajkko.aviaorder.utils.DbUtils.getByIdStatement;

public class CityDb extends AbstractDb<City>{

    private static final Logger LOG = LogManager.getLogger(CityDb.class);

    private static final String TABLE_NAME = "city";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_DESC = "desc";
    private static final String COLUMN_COUNTRY_ID = "country_id";
    private static final String COLUMN_TIME_ZONE = "time_zone";

    private static final String SQL_GET_CITIES = "select * from " + TABLE_NAME;
    private static final String SQL_GET_CITY = SQL_GET_CITIES + " where id = ?";

    private static final String SQL_INSERT_CITY = "insert into " + TABLE_NAME +
            " ('" + COLUMN_NAME + "', '" + COLUMN_COUNTRY_ID +
            "', '" + COLUMN_TIME_ZONE + "', '" + COLUMN_DESC + "') values (?, ?, ?, ?)";

    private static CityDb instance;

    private CityDb(){
    }

    public static CityDb getInstance(){
        if (instance == null){
            instance = new CityDb();
        }
        return instance;
    }

    public Collection<City> getCities(){
        return getCollection(SQL_GET_CITIES);
    }

    public City getCity(long id){
        return getByIdAndCloseConnection(id);
    }

    @Override
    protected City getById(long id) throws SQLException {
        return getObject(getByIdStatement(SQL_GET_CITY, id));
    }

    @Override
    protected City mapFromResultSet(ResultSet resultSet) throws SQLException {
        City city = new City();
        city.setId(resultSet.getLong(COLUMN_ID));
        city.setName(resultSet.getString(COLUMN_NAME));
        city.setDesc(resultSet.getString(COLUMN_DESC));
        city.setTimeZone(ZoneId.of(resultSet.getString(COLUMN_TIME_ZONE)));
        city.setCountry(CountryDb.getInstance().
                getById(resultSet.getLong(COLUMN_COUNTRY_ID)));
        return city;
    }

    public void addCity(City city) {
        addObjectAndCloseConnection(city, SQL_INSERT_CITY);
    }

    @Override
    protected void prepareUpdateStatement(PreparedStatement statement, City city) throws SQLException {
        statement.setString(1, city.getName());
        statement.setLong(2, city.getCountry().getId());
        statement.setString(3, city.getTimeZone().toString());
        statement.setString(4, city.getDesc());
    }

    @Override
    protected void logError(Exception e) {
        LOG.error(e.getMessage(), e);
    }
}
