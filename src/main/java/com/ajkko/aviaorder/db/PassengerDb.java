package com.ajkko.aviaorder.db;

import com.ajkko.aviaorder.objects.Passenger;
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

public class PassengerDb {

    private static PassengerDb instance;
    private static final Logger LOG = LogManager.getLogger(PassengerDb.class);

    private static final String SQL_GET_PASSENGERS = "select * from FlightDB.passenger";
    private static final String SQL_GET_PASSENGER = SQL_GET_PASSENGERS + " where id = ?";

    private static final String COLUMN_ID = "id";
    private static final String COLUMN_FIRST_NAME = "first_name";
    private static final String COLUMN_LAST_NAME = "last_name";
    private static final String COLUMN_DOCUMENT_NUMBER = "document_number";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_PHONE = "phone";

    private PassengerDb(){
    }

    public static PassengerDb getInstance(){
        if (instance == null){
            instance = new PassengerDb();
        }
        return instance;
    }

    public Passenger getPassenger(long id){
        try {
            return get(id);
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        } finally {
            MainDb.getInstance().closeConnection();
        }
        return null;
    }

    protected Passenger get(long id) throws SQLException {
        return getPassenger(DbUtils.getByIdStatement(SQL_GET_PASSENGER, id));
    }

    private Passenger getPassenger(PreparedStatement statement) throws SQLException {
        ResultSet resultSet = null;
        try {
            resultSet = statement.executeQuery();
            resultSet.next();
            return getMappedPassenger(resultSet);
        } finally {
            closeResultSet(resultSet);
            closeStatement(statement);
        }

    }

    public Collection<Passenger> getPassengers(){
        try {
            return getPassengers(MainDb.getInstance().getStatement());
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        } finally {
            MainDb.getInstance().closeConnection();
        }
        return null;
    }

    private Collection<Passenger> getPassengers(Statement statement) throws SQLException {
        ResultSet resultSet = null;
        Collection<Passenger> passengers = new ArrayList<>();
        try {
            resultSet = statement.executeQuery(SQL_GET_PASSENGERS);
            while (resultSet.next()) {
                passengers.add(getMappedPassenger(resultSet));
            }
        } finally {
            closeResultSet(resultSet);
            closeStatement(statement);
        }
        return passengers;
    }

    private Passenger getMappedPassenger(ResultSet resultSet) throws SQLException {
        Passenger passenger = new Passenger();
        passenger.setId(resultSet.getLong(COLUMN_ID));
        passenger.setFirstName(resultSet.getString(COLUMN_FIRST_NAME));
        passenger.setLastName(resultSet.getString(COLUMN_LAST_NAME));
        passenger.setDocumentNumber(resultSet.getString(COLUMN_DOCUMENT_NUMBER));
        passenger.setEmail(resultSet.getString(COLUMN_EMAIL));
        passenger.setPhone(resultSet.getString(COLUMN_PHONE));
        return passenger;
    }
}
