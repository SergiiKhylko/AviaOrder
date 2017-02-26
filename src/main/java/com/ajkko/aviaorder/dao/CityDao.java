package com.ajkko.aviaorder.dao;

import com.ajkko.aviaorder.objects.City;

import java.util.Collection;

public interface CityDao {

    Collection<City> getCities();

    City getCity(Long id);

    void addCity(City city);
}
