package com.ajkko.aviaorder.db;

import com.ajkko.aviaorder.objects.Country;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

import static com.ajkko.aviaorder.utils.DbUtils.getByIdStatement;

public class CountryDb extends AbstractDb<Country> {

    private static CountryDb instance;

    private static final Logger LOG = LogManager.getLogger(CountryDb.class);

    private static final String TABLE_NAME = "country";
    private static final String SQL_GET_COUNTRIES = "select * from " + TABLE_NAME;
    private static final String SQL_GET_COUNTRY_BY_ID = SQL_GET_COUNTRIES + " where id = ?";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_SHORT_NAME = "short_name";

    private CountryDb(){
    }

    public static CountryDb getInstance(){
        if (instance == null){
            instance = new CountryDb();
        }
        return instance;
    }

    @Override
    protected void prepareUpdateStatement(PreparedStatement statement, Country object) throws SQLException {
        //TODO
    }

    @Override
    protected Country mapFromResultSet(ResultSet resultSet) throws SQLException {
        Country country = new Country();
        country.setId(resultSet.getLong(COLUMN_ID));
        country.setName(resultSet.getString(COLUMN_NAME));
        country.setShortName(resultSet.getString(COLUMN_SHORT_NAME));
        return country;
    }

    @Override
    protected Country getById(long id) throws SQLException {
        return getObject(getByIdStatement(SQL_GET_COUNTRY_BY_ID, id));
    }

    @Override
    protected void logError(Exception e) {
        LOG.error(e.getMessage(), e);
    }

    public Collection<Country> getCountries(){
       return getCollection(SQL_GET_COUNTRIES);
    }

    public Country getCountry(long id) {
        return getByIdAndCloseConnection(id);
    }
}
