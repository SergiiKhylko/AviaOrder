package com.ajkko.aviaorder.db;

import com.ajkko.aviaorder.objects.Company;
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

public class CompanyDb {

    private static final Logger LOG = LogManager.getLogger(CompanyDb.class);

    private static CompanyDb instance;
    private static final String SQL_GET_COMPANIES = "select * from FlightDB.company";
    private static final String SQL_GET_COMPANY = SQL_GET_COMPANIES + " where id = ?";
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
            companies.add(getMappedCompany(resultSet));
        }
        return companies;
    }

    public Company getCompany(long id){
        try {
            return get(id);
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        } finally {
            MainDb.getInstance().closeConnection();
        }
        return null;
    }

    protected Company get(long id) throws SQLException {
        return getCompany(DbUtils.getByIdStatement(SQL_GET_COMPANY, id));
    }

    private Company getCompany(PreparedStatement statement) throws SQLException {
        ResultSet resultSet = null;
        try {
            resultSet = statement.executeQuery();
            resultSet.next();
            return getMappedCompany(resultSet);
        } finally {
            closeResultSet(resultSet);
            closeStatement(statement);
        }
    }

    private Company getMappedCompany(ResultSet resultSet) throws SQLException {
        Company company = new Company();
        company.setId(resultSet.getLong(COLUMN_ID));
        company.setName(resultSet.getString(COLUMN_NAME));
        company.setDesc(resultSet.getString(COLUMN_DESC));
        return company;
    }
}
