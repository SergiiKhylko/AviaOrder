package com.ajkko.aviaorder.objects;

import com.ajkko.aviaorder.objects.spr.Aircraft;
import com.ajkko.aviaorder.objects.spr.City;

import java.util.Calendar;

public class Flight {
    private long id;
    private String code;
    private Calendar flightDate;
    private Calendar flightTime;
    private Aircraft aircraft;
    private long duration;
    private City cityFrom;
    private City cityTo;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Calendar getFlightDate() {
        return flightDate;
    }

    public void setFlightDate(Calendar flightDate) {
        this.flightDate = flightDate;
    }

    public Calendar getFlightTime() {
        return flightTime;
    }

    public void setFlightTime(Calendar flightTime) {
        this.flightTime = flightTime;
    }

    public Aircraft getAircraft() {
        return aircraft;
    }

    public void setAircraft(Aircraft aircraft) {
        this.aircraft = aircraft;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public City getCityFrom() {
        return cityFrom;
    }

    public void setCityFrom(City cityFrom) {
        this.cityFrom = cityFrom;
    }

    public City getCityTo() {
        return cityTo;
    }

    public void setCityTo(City cityTo) {
        this.cityTo = cityTo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Flight flight = (Flight) o;

        if (id != flight.id) return false;
        if (duration != flight.duration) return false;
        if (code != null ? !code.equals(flight.code) : flight.code != null) return false;
        if (flightDate != null ? !flightDate.equals(flight.flightDate) : flight.flightDate != null) return false;
        if (flightTime != null ? !flightTime.equals(flight.flightTime) : flight.flightTime != null) return false;
        if (aircraft != null ? !aircraft.equals(flight.aircraft) : flight.aircraft != null) return false;
        if (cityFrom != null ? !cityFrom.equals(flight.cityFrom) : flight.cityFrom != null) return false;
        return !(cityTo != null ? !cityTo.equals(flight.cityTo) : flight.cityTo != null);

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (code != null ? code.hashCode() : 0);
        result = 31 * result + (flightDate != null ? flightDate.hashCode() : 0);
        result = 31 * result + (flightTime != null ? flightTime.hashCode() : 0);
        result = 31 * result + (aircraft != null ? aircraft.hashCode() : 0);
        result = 31 * result + (int) (duration ^ (duration >>> 32));
        result = 31 * result + (cityFrom != null ? cityFrom.hashCode() : 0);
        result = 31 * result + (cityTo != null ? cityTo.hashCode() : 0);
        return result;
    }
}
