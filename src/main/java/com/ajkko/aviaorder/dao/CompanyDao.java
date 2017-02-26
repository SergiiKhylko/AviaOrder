package com.ajkko.aviaorder.dao;

import com.ajkko.aviaorder.objects.Company;

import java.util.Collection;

public interface CompanyDao {

    Collection<Company> getCompanies();

    Company getCompany(Long id);

    void addCompany(Company company);
}
