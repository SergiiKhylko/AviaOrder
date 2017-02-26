package com.ajkko.aviaorder.dao.jdbc;

import com.ajkko.aviaorder.dao.CompanyDao;
import com.ajkko.aviaorder.db.CompanyDb;
import com.ajkko.aviaorder.objects.Company;

import java.util.Collection;

public class CompanyJdbcDao implements CompanyDao{

    private CompanyDb companyDb;

    public CompanyJdbcDao(CompanyDb companyDb) {
        this.companyDb = companyDb;
    }

    @Override
    public Collection<Company> getCompanies() {
        return companyDb.getCompanies();
    }

    @Override
    public Company getCompany(Long id) {
        return companyDb.getCompany(id);
    }

    @Override
    public void addCompany(Company company) {
        companyDb.addCompany(company);
    }
}
