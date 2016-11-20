package com.ajkko.aviaorder.db;

import com.ajkko.aviaorder.objects.Passenger;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.Collection;

public class PassengerDb {

    private static PassengerDb instance;
    private static final Logger LOG = LogManager.getLogger(PassengerDb.class);

    private static final String SQL_GET_PASSENGER_BY_ID = "select * from FlightDB.passenger where id = ?";
    private static final String SQL_GET_PASSENGERS = "select * from FlightDB.passenger";

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
            return getPassenger(getPassengerStatement(id));
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        } finally {
            MainDb.getInstance().closeConnection();
        }

        return null;
    }

    private Passenger getPassenger(PreparedStatement statement) throws SQLException {
        Passenger passenger = new Passenger();
        ResultSet resultSet = statement.executeQuery();
        resultSet.next();

        passenger.setId(resultSet.getLong(COLUMN_ID));
        passenger.setFirstName(resultSet.getString(COLUMN_FIRST_NAME));
        passenger.setLastName(resultSet.getString(COLUMN_LAST_NAME));
        passenger.setDocumentNumber(resultSet.getString(COLUMN_DOCUMENT_NUMBER));
        passenger.setEmail(resultSet.getString(COLUMN_EMAIL));
        passenger.setPhone(resultSet.getString(COLUMN_PHONE));
        return passenger;
    }

    private PreparedStatement getPassengerStatement(long id) throws SQLException {
        Connection connection = MainDb.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement(SQL_GET_PASSENGER_BY_ID);
        statement.setLong(1, id);
        return statement;
    }

    public Collection<Passenger> getPassengers(){
        try {
            return getPassengers(getPassengersStatement());
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        } finally {
            MainDb.getInstance().closeConnection();
        }

        return null;
    }

    private Collection<Passenger> getPassengers(Statement statement) throws SQLException {
        ResultSet resultSet = statement.executeQuery(SQL_GET_PASSENGERS);
        Collection<Passenger> passengers = new ArrayList<>();

        while(resultSet.next()){
            Passenger passenger = new Passenger();
            passenger.setId(resultSet.getLong(COLUMN_ID));
            passenger.setFirstName(resultSet.getString(COLUMN_FIRST_NAME));
            passenger.setLastName(resultSet.getString(COLUMN_LAST_NAME));
            passenger.setDocumentNumber(resultSet.getString(COLUMN_DOCUMENT_NUMBER));
            passenger.setEmail(resultSet.getString(COLUMN_EMAIL));
            passenger.setPhone(resultSet.getString(COLUMN_PHONE));
            passengers.add(passenger);
        }
        return passengers;
    }

    private Statement getPassengersStatement() throws SQLException {
        Connection connection = MainDb.getInstance().getConnection();
        return connection.createStatement();
    }
}
