package com.ajkko.aviaorder.dao.jdbc;

import com.ajkko.aviaorder.dao.CityDao;
import com.ajkko.aviaorder.db.CityDb;
import com.ajkko.aviaorder.objects.City;

import java.util.Collection;

public class CityJdbcDao implements CityDao{

    private CityDb cityDb;

    public CityJdbcDao(CityDb cityDb) {
        this.cityDb = cityDb;
    }

    @Override
    public Collection<City> getCities() {
        return cityDb.getCities();
    }

    @Override
    public City getCity(Long id) {
        return cityDb.getCity(id);
    }

    @Override
    public void addCity(City city) {
        cityDb.addCity(city);
    }
}
