package org.example.task0;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class HandlerIncidents extends DefaultHandler {

    public HandlerIncidents(AccidentList incidentsList) {
        this.incidentsList = incidentsList;
    }

    private AccidentList incidentsList;
    private RoadAccident incidents;
    private double L, W;
    private String region;
    private String district;
    private String elementValue;

    @Override
    public void startDocument() throws SAXException {
        System.out.println("Start Document Processing");
    }

    @Override
    public void endDocument() throws SAXException {
        System.out.println("Stop Document Processing");
        incidentsList.add(incidents);
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if(qName == "regName") {
            region = elementValue;
            incidents = new RoadAccident(region);
        }
        if(qName == "district") district = elementValue;
        if(qName == "COORD_L") L = Double.parseDouble(elementValue);
        if(qName == "COORD_W") W = Double.parseDouble(elementValue);
        if(qName == "tab") {
            Coordinate coordinate = new Coordinate(L, W);
            Location location = new Location(district, coordinate);
            incidents.add(location);
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        elementValue = new String(ch, start, length);
    }

    public AccidentList getIncidentsList() {
        return incidentsList;
    }

}
