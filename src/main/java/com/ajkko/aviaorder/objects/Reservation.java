package com.ajkko.aviaorder.objects;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Calendar;

public class Reservation {

    private long id;
    private Passenger passenger;
    private Flight flight;
    private Place place;
    private String addInfo;
    private Calendar reserveDateTime;
    private String code;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Passenger getPassenger() {
        return passenger;
    }

    public void setPassenger(Passenger passenger) {
        this.passenger = passenger;
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

    public String getAddInfo() {
        return addInfo;
    }

    public void setAddInfo(String addInfo) {
        this.addInfo = addInfo;
    }

    public Calendar getReserveDateTime() {
        return reserveDateTime;
    }

    public void setReserveDateTime(Calendar reserveDateTime) {
        this.reserveDateTime = reserveDateTime;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reservation that = (Reservation) o;
        return new EqualsBuilder()
                .append(id, that.id)
                .append(passenger, that.passenger)
                .append(flight, that.flight)
                .append(place, that.place)
                .append(addInfo, that.addInfo)
                .append(reserveDateTime, that.reserveDateTime)
                .append(code, that.code)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(passenger)
                .append(flight)
                .append(place)
                .append(addInfo)
                .append(reserveDateTime)
                .append(code)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("id", id)
                .append("passenger", passenger)
                .append("flight", flight)
                .append("place", place)
                .append("addInfo", addInfo)
                .append("reserveDateTime", reserveDateTime)
                .append("code", code)
                .toString();
    }
}
