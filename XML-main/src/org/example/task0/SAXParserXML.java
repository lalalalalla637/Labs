package org.example.task0;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;

public class SAXParserXML {

    private AccidentList incidentsList;

    public SAXParserXML(AccidentList incidentsList) {
        this.incidentsList = incidentsList;
    }

    public SAXParserXML(){
        incidentsList = new AccidentList();
    }

    public AccidentList parseFiles(String directoryPath){
        AccidentList incidentsList = new AccidentList();
        File dir = new File(directoryPath);
        for (File f : dir.listFiles()) {
            if (f.isFile()) {
                if (f.getName().matches(".*\\.xml")) {
                    System.out.println(f.getAbsolutePath());
                    incidentsList = analyzeWithSAX(f.getAbsolutePath(),incidentsList);
                } else if (f.isDirectory()) {
                    System.out.println("\tDirectory: " + f.getName());
                    incidentsList = analyzeWithSAX("\tDirectory: " + f.getName(),incidentsList);
                }
            }
        }
        return incidentsList;
    }

    private AccidentList analyzeWithSAX(String documentPath, AccidentList incidentsList){
        SAXParserFactory sax = SAXParserFactory.newInstance();
        HandlerIncidents handler = new HandlerIncidents(incidentsList);
        System.out.println("Finished handler");
        try {
            SAXParser parser = sax.newSAXParser();
            parser.parse(new File(documentPath), handler);

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return handler.getIncidentsList();
    }

}
