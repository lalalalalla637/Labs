package org.example.task0;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class MainForDOM {

    public static void main(String[] args) {
        DOMParserXML domParserXML = new DOMParserXML();
        try {
            domParserXML.parse("src/org/example/task0/road_accident.xsd");
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
    }

}
