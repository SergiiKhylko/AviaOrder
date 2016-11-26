package com.ajkko.aviaorder.objects;

import com.ajkko.aviaorder.objects.spr.Aircraft;
import com.ajkko.aviaorder.objects.spr.City;

import java.util.Calendar;

public class Flight {
    private long id;
    private String code;
    private Calendar dateDepart;
    private Calendar dateCome;
    private Aircraft aircraft;
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

    public Aircraft getAircraft() {
        return aircraft;
    }

    public void setAircraft(Aircraft aircraft) {
        this.aircraft = aircraft;
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

    public Calendar getDateDepart() {
        return dateDepart;
    }

    public void setDateDepart(Calendar dateDepart) {
        this.dateDepart = dateDepart;
    }

    public Calendar getDateCome() {
        return dateCome;
    }

    public void setDateCome(Calendar dateCome) {
        this.dateCome = dateCome;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Flight flight = (Flight) o;

        if (id != flight.id) return false;
        if (code != null ? !code.equals(flight.code) : flight.code != null) return false;
        if (dateDepart != null ? !dateDepart.equals(flight.dateDepart) : flight.dateDepart != null) return false;
        if (dateCome != null ? !dateCome.equals(flight.dateCome) : flight.dateCome != null) return false;
        if (aircraft != null ? !aircraft.equals(flight.aircraft) : flight.aircraft != null) return false;
        if (cityFrom != null ? !cityFrom.equals(flight.cityFrom) : flight.cityFrom != null) return false;
        return !(cityTo != null ? !cityTo.equals(flight.cityTo) : flight.cityTo != null);

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (code != null ? code.hashCode() : 0);
        result = 31 * result + (dateDepart != null ? dateDepart.hashCode() : 0);
        result = 31 * result + (dateCome != null ? dateCome.hashCode() : 0);
        result = 31 * result + (aircraft != null ? aircraft.hashCode() : 0);
        result = 31 * result + (cityFrom != null ? cityFrom.hashCode() : 0);
        result = 31 * result + (cityTo != null ? cityTo.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Flight{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", dateDepart=" + dateDepart +
                ", dateCome=" + dateCome +
                ", aircraft=" + aircraft +
                ", cityFrom=" + cityFrom +
                ", cityTo=" + cityTo +
                '}';
    }
}
