package com.ajkko.aviaorder.db;

import com.ajkko.aviaorder.objects.Aircraft;
import com.ajkko.aviaorder.utils.DbUtils;
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

public class AircraftDb {

    private static final Logger LOG = LogManager.getLogger(AircraftDb.class);

    private static final String SQL_GET_AIRCRAFTS = "select * from spr_aircraft";
    private static final String SQL_GET_AIRCRAFT = "select * from spr_aircraft where id = ?";
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
                aircrafts.add(getMappedAircraft(resultSet));
            }
        } finally {
            closeResultSet(resultSet);
            closeStatement(statement);
        }
        return aircrafts;
    }

    public Aircraft getAircraft(long id){
        try {
            return get(id);
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        } finally {
            MainDb.getInstance().closeConnection();
        }
        return null;
    }

    protected Aircraft get(long id) throws SQLException {
        return getAircraft(DbUtils.getByIdStatement(SQL_GET_AIRCRAFT, id));
    }

    private Aircraft getAircraft(PreparedStatement statement) throws SQLException {
        ResultSet resultSet = null;
        try {
            resultSet = statement.executeQuery();
            resultSet.next();
            return getMappedAircraft(resultSet);
        } finally {
            closeStatement(statement);
            closeResultSet(resultSet);
        }
    }

    private Aircraft getMappedAircraft(ResultSet resultSet) throws SQLException {
        Aircraft aircraft = new Aircraft();
        aircraft.setId(resultSet.getLong(COLUMN_ID));
        aircraft.setName(resultSet.getString(COLUMN_NAME));
        aircraft.setCompany(CompanyDb.getInstance().
                get(resultSet.getLong(COLUMN_COMPANY_ID)));
        aircraft.setDesc(resultSet.getString(COLUMN_DESC));
        return aircraft;
    }

}
