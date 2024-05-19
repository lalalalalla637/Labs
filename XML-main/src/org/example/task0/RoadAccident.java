package org.example.task0;

import java.util.ArrayList;

public class RoadAccident {

    private String region;
    private ArrayList<Location> locations;

    public RoadAccident(String region) {
        this.region = region;
        this.locations = new ArrayList<>();
    }

    public void add(Location location){
        locations.add(location);
    }

    public void remove(int index){
        locations.remove(index);
    }

    public String getRegion() {
        return region;
    }

    public ArrayList<Location> getCoordinateList() {
        return locations;
    }

    public void setRegion(String region) {
        this.region = region;
    }

}
