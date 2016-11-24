package com.ajkko.aviaorder.db;

import com.ajkko.aviaorder.objects.spr.Company;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

import static com.ajkko.aviaorder.utils.DbUtils.closeResultSet;
import static com.ajkko.aviaorder.utils.DbUtils.closeStatement;

public class CompanyDb {

    private static final Logger LOG = LogManager.getLogger(CompanyDb.class);

    private static CompanyDb instance;
    private static final String SQL_GET_COMPANIES = "select * from FlightDB.spr_company";
    private static final String SQL_GET_COMPANY = "select * from FlightDB.spr_company where id = ?";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_DESC = "desc";

    private CompanyDb(){
    }

    public static CompanyDb getInstance(){
        if(instance == null){
            instance = new CompanyDb();
        }
        return instance;
    }

    public Collection<Company> getCompanies(){
        try {
            return getCompanies(MainDb.getInstance().getStatement());
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        } finally {
            MainDb.getInstance().closeConnection();
        }
        return new ArrayList<>();
    }

    private Collection<Company> getCompanies(Statement statement) throws SQLException {
        ResultSet resultSet =  statement.executeQuery(SQL_GET_COMPANIES);
        Collection<Company> companies = new ArrayList<>();
        while(resultSet.next()){
            Company company = new Company();
            company.setId(resultSet.getLong(COLUMN_ID));
            company.setName(resultSet.getString(COLUMN_NAME));
            company.setDesc(resultSet.getString(COLUMN_DESC));
            companies.add(company);
        }
        return companies;
    }

    public Company getCompany(long id){
        try {
            return getCompany(getCompanyStatement(id));
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        } finally {
            //MainDb.getInstance().closeConnection(); //todo
        }
        return null;
    }

    private PreparedStatement getCompanyStatement(long id) throws SQLException {
        Connection connection = MainDb.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement(SQL_GET_COMPANY);
        statement.setLong(1, id);
        return statement;
    }

    private Company getCompany(PreparedStatement statement) throws SQLException {
        Company company = new Company();
        ResultSet resultSet = null;
        try {
            resultSet = statement.executeQuery();
            resultSet.next();
            company.setId(resultSet.getLong(COLUMN_ID));
            company.setName(resultSet.getString(COLUMN_NAME));
            company.setDesc(resultSet.getString(COLUMN_DESC));
        } finally {
            closeResultSet(resultSet);
            closeStatement(statement);
        }
        return company;
    }
}
