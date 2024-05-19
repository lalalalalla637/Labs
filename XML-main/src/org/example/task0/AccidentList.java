package org.example.task0;

import java.util.ArrayList;

public class AccidentList {

    private ArrayList<RoadAccident> incidentsList;

    public AccidentList() {
        this.incidentsList = new ArrayList<>();
    }

    public void add(RoadAccident incidents) {
        incidentsList.add(incidents);
    }

    public RoadAccident remove(String region) {
        for (int i = 0; i < incidentsList.size(); i++) {
            if (incidentsList.get(i).getRegion() == region) return incidentsList.remove(i);
        }
        return null;
    }

    public RoadAccident remove(int index) {
        return incidentsList.remove(index);
    }

    public RoadAccident get(int i) {
        return incidentsList.get(i);
    }

    public int size(){
        return incidentsList.size();
    }

}
