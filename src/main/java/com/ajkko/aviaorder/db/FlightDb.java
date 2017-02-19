package com.ajkko.aviaorder.db;

import com.ajkko.aviaorder.objects.City;
import com.ajkko.aviaorder.objects.Company;
import com.ajkko.aviaorder.objects.Flight;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collection;

import static com.ajkko.aviaorder.utils.DbUtils.getByIdStatement;
import static com.ajkko.aviaorder.utils.DbUtils.getPrepareStatement;

public class FlightDb extends AbstractDb<Flight>{

    private static final Logger LOG = LogManager.getLogger(FlightDb.class);

    private static final String TABLE_NAME = "flight";

    private static final String COLUMN_ID = "id";
    private static final String COLUMN_DEPART = "date_depart";
    private static final String COLUMN_DATE_COME = "date_come";
    private static final String COLUMN_COMPANY_ID = "company_id";
    private static final String COLUMN_CITY_FROM_ID = "city_from_id";
    private static final String COLUMN_CITY_TO_ID = "city_to_id";

    private static final String SQL_GET_FLIGHTS = "select * from " + TABLE_NAME;
    private static final String SQL_GET_FLIGHT = SQL_GET_FLIGHTS + " where " + COLUMN_ID + " = ?";
    private static final String SQL_GET_FLIGHT_BY_CITY_AND_DATE = SQL_GET_FLIGHTS +
            " where " + COLUMN_CITY_FROM_ID + " = ?" +
            " and " + COLUMN_DEPART + " >= ?" +
            " and " + COLUMN_DEPART + " < ?";

    private static FlightDb instance;

    private static final String SQL_INSERT_FLIGHT = "insert into " + TABLE_NAME +
            "(" + COLUMN_DEPART + ", " +
            COLUMN_DATE_COME + ", " +
            COLUMN_COMPANY_ID + ", " +
            COLUMN_CITY_FROM_ID + ", " +
            COLUMN_CITY_TO_ID + ") " +
            "values (?, ?, ?, ?, ?, ?);";

    private FlightDb() {
    }

    public static FlightDb getInstance() {
        if (instance == null) {
            instance = new FlightDb();
        }
        return instance;
    }

    public Collection<Flight> getFlights(){
        try {
            return getCollection(getPrepareStatement(SQL_GET_FLIGHTS));
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        } finally {
            MainDb.getInstance().closeConnection();
        }
        return new ArrayList<>();
    }

    public Collection<Flight> getFlights(long cityId, long timeStampFrom, long timeStampTo){
        try {
            PreparedStatement statement = getPrepareStatement(SQL_GET_FLIGHT_BY_CITY_AND_DATE);
            statement.setLong(1, cityId);
            statement.setLong(2, timeStampFrom);
            statement.setLong(3, timeStampTo);
            return getCollection(statement);
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        } finally {
            MainDb.getInstance().closeConnection();
        }
        return new ArrayList<>();
    }

    @Override
    protected Flight mapFromResultSet(ResultSet resultSet) throws SQLException {
        Flight flight = new Flight();
        flight.setId(resultSet.getLong(COLUMN_ID));
        flight.setCityFrom(CityDb.getInstance().
                get(resultSet.getLong(COLUMN_CITY_FROM_ID)));
        flight.setCityTo(CityDb.getInstance().get(resultSet.getLong(COLUMN_CITY_TO_ID)));
        flight.setDateDepart(getDateTime(resultSet.getLong(COLUMN_DEPART),
                flight.getCityFrom().getTimeZone()));
        flight.setDateCome(getDateTime(resultSet.getLong(COLUMN_DATE_COME),
                flight.getCityTo().getTimeZone()));
        flight.setCompany(CompanyDb.getInstance().
                get(resultSet.getLong(COLUMN_COMPANY_ID)));
        return flight;
    }

    private ZonedDateTime getDateTime(long timestamp, ZoneId timeZone){
        Instant instant = Instant.ofEpochMilli(timestamp);
        return ZonedDateTime.ofInstant(instant, timeZone);
    }

    public Flight getFlight(long id){
        try {
            return get(id);
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        } finally {
            MainDb.getInstance().closeConnection();
        }
        return null;
    }

    protected Flight get(long id) throws SQLException {
        return getObject(getByIdStatement(SQL_GET_FLIGHT, id));
    }

    public void addFlight(Flight flight) {
        try {
            executeInsert(flight, SQL_INSERT_FLIGHT);
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
    }

    @Override
    protected void prepareStatement(PreparedStatement statement, Flight flight) throws SQLException {
        statement.setLong(1, toMilliSec(flight.getDateDepart()));
        statement.setLong(2, toMilliSec(flight.getDateCome()));
        statement.setLong(3, getCompanyId(flight.getCompany()));
        statement.setLong(4, getCityId(flight.getCityFrom()));
        statement.setLong(5, getCityId(flight.getCityTo()));
    }

    private long toMilliSec(ZonedDateTime time) {
        return time == null ? 0 : time.toInstant().toEpochMilli();
    }

    private long getCompanyId(Company company) {
        return company == null ? 0 : company.getId();
    }

    private long getCityId(City city) {
        return city == null ? 0 : city.getId();
    }

}
