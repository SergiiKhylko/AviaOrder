package com.ajkko.aviaorder.dao.jdbc;

import com.ajkko.aviaorder.dao.CountryDao;
import com.ajkko.aviaorder.db.CountryDb;
import com.ajkko.aviaorder.objects.Country;

import java.util.Collection;

public class CountryJdbcDao implements CountryDao{

    private CountryDb countryDb;

    public CountryJdbcDao(CountryDb countryDb) {
        this.countryDb = countryDb;
    }


    @Override
    public Collection<Country> getCountries() {
        return countryDb.getCountries();
    }

    @Override
    public Country getCountry(Long id) {
        return countryDb.getCountry(id);
    }

    @Override
    public void addCountry(Country country) {

    }
}
