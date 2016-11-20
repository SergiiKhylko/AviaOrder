package com.ajkko.aviaorder.objects.spr;

public class Place {
    private long id;
    private char row;
    private int seat;
    private FlightClass flightClass;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public char getRow() {
        return row;
    }

    public void setRow(char row) {
        this.row = row;
    }

    public int getSeat() {
        return seat;
    }

    public void setSeat(int seat) {
        this.seat = seat;
    }

    public FlightClass getFlightClass() {
        return flightClass;
    }

    public void setFlightClass(FlightClass flightClass) {
        this.flightClass = flightClass;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Place place = (Place) o;

        if (id != place.id) return false;
        if (row != place.row) return false;
        if (seat != place.seat) return false;
        return !(flightClass != null ? !flightClass.equals(place.flightClass) : place.flightClass != null);

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (int) row;
        result = 31 * result + seat;
        result = 31 * result + (flightClass != null ? flightClass.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Place{" +
                "id=" + id +
                ", row=" + row +
                ", seat=" + seat +
                ", flightClass=" + flightClass +
                '}';
    }
}
