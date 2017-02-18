package com.ajkko.aviaorder.dao.jdbc;


import com.ajkko.aviaorder.dao.FlightDao;
import com.ajkko.aviaorder.db.FlightDb;
import com.ajkko.aviaorder.objects.City;
import com.ajkko.aviaorder.objects.Flight;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collection;

public class FlightJdbcDao implements FlightDao{

    private FlightDb flightDb;

    public FlightJdbcDao(FlightDb flightDb) {
        this.flightDb = flightDb;
    }

    @Override
    public Collection<Flight> getFlights() {
        return flightDb.getFlights();
    }

    @Override
    public Collection<Flight> getFlights(City city, LocalDate date) {
        ZoneId zoneId = city.getTimeZone();
        long timeStampFrom = date.atStartOfDay(zoneId).toInstant().toEpochMilli();
        long timeStampTo = date.plusDays(1).atStartOfDay(zoneId).toInstant().toEpochMilli();

        return flightDb.getFlights(city.getId(), timeStampFrom, timeStampTo);
    }

    @Override
    public Flight getFlight(Long id) {
        return flightDb.getFlight(id);
    }
}
