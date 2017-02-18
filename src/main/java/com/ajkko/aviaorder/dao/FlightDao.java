package com.ajkko.aviaorder.dao;

import com.ajkko.aviaorder.objects.City;
import com.ajkko.aviaorder.objects.Flight;

import java.time.LocalDate;
import java.util.Collection;

public interface FlightDao {

    Collection<Flight> getFlights();

    Collection<Flight> getFlights(City city, LocalDate date);

    Flight getFlight(Long id);
}
