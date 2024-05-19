package org.example.task0;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.FileNotFoundException;

public class MainForSAX {

    public static void main(String[] args) {
        SAXParserXML parserXML = new SAXParserXML();
        AccidentList incidentsList = parserXML.parseFiles("src/org/example/task0/DTPData");
        DOMWriterXML domWriterXML = null;
        try {
            domWriterXML = new DOMWriterXML();
            domWriterXML.write(incidentsList);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }

}
