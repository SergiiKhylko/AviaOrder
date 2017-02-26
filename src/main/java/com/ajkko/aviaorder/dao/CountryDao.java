package com.ajkko.aviaorder.dao;

import com.ajkko.aviaorder.objects.Country;

import java.util.Collection;

public interface CountryDao {

    Collection<Country> getCountries();

    Country getCountry(Long id);

    void addCountry(Country country);
}
