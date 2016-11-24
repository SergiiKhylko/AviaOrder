package com.ajkko.aviaorder.db;

import com.ajkko.aviaorder.objects.spr.Aircraft;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

import static com.ajkko.aviaorder.utils.DbUtils.closeResultSet;
import static com.ajkko.aviaorder.utils.DbUtils.closeStatement;

public class AircraftDb {

    private static final Logger LOG = LogManager.getLogger(AircraftDb.class);

    private static final String SQL_GET_AIRCRAFTS = "select * from spr_aircraft";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_DESC = "desc";
    private static final String COLUMN_COMPANY_ID = "company_id";
    private static AircraftDb instance;

    private AircraftDb(){
    }

    public static AircraftDb getInstance(){
        if(instance == null) {
            instance = new AircraftDb();
        }
        return instance;
    }

    public Collection<Aircraft> getAircrafts(){
        try {
            return getAircrafts(MainDb.getInstance().getStatement());
        }catch (SQLException e){
            LOG.error(e.getMessage(), e);
        } finally {
            MainDb.getInstance().closeConnection();
        }
        return new ArrayList<>();
    }

    private Collection<Aircraft> getAircrafts(Statement statement) throws SQLException {
        ResultSet resultSet = null;
        Collection<Aircraft> aircrafts = new ArrayList<>();
        try {
            resultSet = statement.executeQuery(SQL_GET_AIRCRAFTS);
            while(resultSet.next()){
                Aircraft aircraft = new Aircraft();
                fillAircraft(aircraft, resultSet);
                aircrafts.add(aircraft);
            }
        } finally {
            closeResultSet(resultSet);
            closeStatement(statement);
        }
        return aircrafts;
    }

    private void fillAircraft(Aircraft aircraft, ResultSet resultSet) throws SQLException {
        aircraft.setId(resultSet.getLong(COLUMN_ID));
        aircraft.setName(resultSet.getString(COLUMN_NAME));
        aircraft.setCompany(CompanyDb.getInstance().
                getCompany(resultSet.getLong(COLUMN_COMPANY_ID)));
        aircraft.setDesc(resultSet.getString(COLUMN_DESC));
    }

}
