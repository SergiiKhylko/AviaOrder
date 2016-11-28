package com.ajkko.aviaorder.db;

import com.ajkko.aviaorder.objects.Flight;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collection;

import static com.ajkko.aviaorder.utils.DbUtils.closeResultSet;
import static com.ajkko.aviaorder.utils.DbUtils.closeStatement;
import static com.ajkko.aviaorder.utils.DbUtils.getByIdStatement;

public class FlightDb {

    private static FlightDb instance;
    private static final Logger LOG = LogManager.getLogger(FlightDb.class);
    private static final String SQL_GET_FLIGHTS = "select * from flight";
    private static final String SQL_GET_FLIGHT = "select * from flight where id = ?";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_CODE = "code";
    private static final String COLUMN_DEPART = "date_depart";
    private static final String COLUMN_DATE_COME = "date_come";
    private static final String COLUMN_AIRCRAFT_ID = "aircraft_id";
    private static final String COLUMN_CITY_FROM_ID = "city_from_id";
    private static final String COLUMN_CITY_TO_ID = "city_to_id";

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
            return getFlights(MainDb.getInstance().getStatement());
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        } finally {
            MainDb.getInstance().closeConnection();
        }
        return new ArrayList<>();
    }

    private Collection<Flight> getFlights(Statement statement) throws SQLException {
        ResultSet resultSet = statement.executeQuery(SQL_GET_FLIGHTS);
        Collection<Flight> flights = new ArrayList<>();
        try {
            while (resultSet.next()) {
                flights.add(getMappedFlight(resultSet));
            }
        } finally {
            closeStatement(statement);
            closeResultSet(resultSet);
        }
        return flights;
    }

    private Flight getMappedFlight(ResultSet resultSet) throws SQLException {
        Flight flight = new Flight();
        flight.setId(resultSet.getLong(COLUMN_ID));
        flight.setCode(resultSet.getString(COLUMN_CODE));
        flight.setCityFrom(CityDb.getInstance().
                get(resultSet.getLong(COLUMN_CITY_FROM_ID)));
        flight.setCityTo(CityDb.getInstance().get(resultSet.getLong(COLUMN_CITY_TO_ID)));
        flight.setDateDepart(getDateTime(resultSet.getLong(COLUMN_DEPART),
                flight.getCityFrom().getTimeZone()));
        flight.setDateCome(getDateTime(resultSet.getLong(COLUMN_DATE_COME),
                flight.getCityTo().getTimeZone()));
        flight.setAircraft(AircraftDb.getInstance().
                get(resultSet.getLong(COLUMN_AIRCRAFT_ID)));
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
        return getFlight(getByIdStatement(SQL_GET_FLIGHT, id));
    }

    private Flight getFlight(PreparedStatement statement) throws SQLException {
        ResultSet resultSet = null;
        try {
            resultSet = statement.executeQuery();
            resultSet.next();
            return getMappedFlight(resultSet);
        } finally {
            closeResultSet(resultSet);
            closeStatement(statement);
        }
    }

}
