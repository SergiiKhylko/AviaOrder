package com.ajkko.aviaorder.db;

import com.ajkko.aviaorder.objects.Company;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

import static com.ajkko.aviaorder.utils.DbUtils.getByIdStatement;

public class CompanyDb extends AbstractDb<Company>{

    private static final Logger LOG = LogManager.getLogger(CompanyDb.class);

    private static CompanyDb instance;

    private static final String TABLE_NAME = "company";
    private static final String SQL_GET_COMPANIES = "select * from " + TABLE_NAME;
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_DESC = "desc";
    private static final String SQL_GET_COMPANY = SQL_GET_COMPANIES + " where " + COLUMN_ID + " = ?";
    private static final String SQL_INSERT_COMPANY = "insert into " + TABLE_NAME +
            " ('" + COLUMN_NAME + "', '" + COLUMN_DESC + "') values (?, ?)";

    private CompanyDb(){
    }

    public static CompanyDb getInstance(){
        if(instance == null){
            instance = new CompanyDb();
        }
        return instance;
    }

    public Collection<Company> getCompanies(){
        return getCollection(SQL_GET_COMPANIES);
    }


    public Company getCompany(long id){
        return getByIdAndCloseConnection(id);
    }

    public void addCompany(Company company) {
        addObjectAndCloseConnection(company, SQL_INSERT_COMPANY);
    }

    @Override
    protected void prepareUpdateStatement(PreparedStatement statement, Company company) throws SQLException {
        statement.setString(1, company.getName());
        statement.setString(2, company.getDesc());
    }

    @Override
    protected Company mapFromResultSet(ResultSet resultSet) throws SQLException {
        Company company = new Company();
        company.setId(resultSet.getLong(COLUMN_ID));
        company.setName(resultSet.getString(COLUMN_NAME));
        company.setDesc(resultSet.getString(COLUMN_DESC));
        return company;
    }

    @Override
    protected Company getById(long id) throws SQLException {
        return getObject(getByIdStatement(SQL_GET_COMPANY, id));
    }

    @Override
    protected void logError(Exception e) {
        LOG.error(e.getMessage(), e);
    }
}
