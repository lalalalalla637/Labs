package org.example.task0;

public class Location {

    private String district;
    private Coordinate coordinates;

    public Location(String district, double x, double y) {
        this.district = district;
        this.coordinates = new Coordinate(x, y);
    }

    public Location(String district, Coordinate coordinates) {
        this.district = district;
        this.coordinates = coordinates;
    }

    public String getDistrict() {
        return district;
    }

    public Coordinate getCoordinates() {
        return coordinates;
    }

}
