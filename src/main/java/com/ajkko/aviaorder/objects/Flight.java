package com.ajkko.aviaorder.objects;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.time.Duration;
import java.time.ZonedDateTime;

public class Flight {
    private long id;
    private String code;
    private ZonedDateTime dateDepart;
    private ZonedDateTime dateCome;
    private Aircraft aircraft;
    private City cityFrom;
    private City cityTo;

    public Duration getFlightDuration(){
        return Duration.between(dateDepart, dateCome);
    }

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

    public ZonedDateTime getDateDepart() {
        return dateDepart;
    }

    public void setDateDepart(ZonedDateTime dateDepart) {
        this.dateDepart = dateDepart;
    }

    public ZonedDateTime getDateCome() {
        return dateCome;
    }

    public void setDateCome(ZonedDateTime dateCome) {
        this.dateCome = dateCome;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Flight flight = (Flight) o;
        return new EqualsBuilder()
                .append(id, flight.id)
                .append(code, flight.code)
                .append(dateDepart, flight.dateDepart)
                .append(dateCome, flight.dateCome)
                .append(aircraft, flight.aircraft)
                .append(cityFrom, flight.cityFrom)
                .append(cityTo, flight.cityTo)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(code)
                .append(dateDepart)
                .append(dateCome)
                .append(aircraft)
                .append(cityFrom)
                .append(cityTo)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("id", id)
                .append("code", code)
                .append("dateDepart", dateDepart)
                .append("dateCome", dateCome)
                .append("aircraft", aircraft)
                .append("cityFrom", cityFrom)
                .append("cityTo", cityTo)
                .toString();
    }
}
