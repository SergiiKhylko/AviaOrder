package com.ajkko.aviaorder.objects;

import com.ajkko.aviaorder.objects.Place;
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

        if (id != that.id) return false;
        if (!passenger.equals(that.passenger)) return false;
        if (!flight.equals(that.flight)) return false;
        if (!place.equals(that.place)) return false;
        if (!addInfo.equals(that.addInfo)) return false;
        if (!reserveDateTime.equals(that.reserveDateTime)) return false;
        return code.equals(that.code);

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + passenger.hashCode();
        result = 31 * result + flight.hashCode();
        result = 31 * result + place.hashCode();
        result = 31 * result + addInfo.hashCode();
        result = 31 * result + reserveDateTime.hashCode();
        result = 31 * result + code.hashCode();
        return result;
    }
}
