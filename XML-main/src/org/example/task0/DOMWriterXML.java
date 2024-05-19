package org.example.task0;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class DOMWriterXML {

    private DocumentBuilderFactory documentBuilderFactory;
    private DocumentBuilder documentBuilder;
    private Document document;

    public DOMWriterXML() throws ParserConfigurationException {
        documentBuilderFactory = DocumentBuilderFactory.newInstance();
        documentBuilder = documentBuilderFactory.newDocumentBuilder();
        document = documentBuilder.newDocument();
    }

    public void write(AccidentList incidentsList) throws FileNotFoundException, TransformerException {
        Element root = document.createElement("road_accident");
        document.appendChild(root);
        for (int i = 0; i < incidentsList.size(); i++) {
            writeAccident(incidentsList.get(i), root);
        }
        Transformer t = TransformerFactory.newInstance().newTransformer();
        t.setOutputProperty(OutputKeys.INDENT, "yes");
        File file = new File("src/org/example/task0/roadAccident.xml");
        t.transform(new DOMSource(document), new StreamResult(new FileOutputStream(file)));
    }

    private void writeAccident(RoadAccident accident, Element element) {
        Element ragName = document.createElement("ragName");
        Text textRagName = document.createTextNode(accident.getRegion());
        element.appendChild(ragName);
        ragName.appendChild(textRagName);
        for (int i = 0; i < accident.getCoordinateList().size(); i++) {
            writeCoordinate(accident.getCoordinateList().get(i), ragName);
        }
    }

    private void writeCoordinate(Location location, Element element) {
        Element district = document.createElement("district");
        Text textDistrict = document.createTextNode(location.getDistrict());
        Element tab = document.createElement("tab");
        district.appendChild(textDistrict);
        Element coordinate = document.createElement("coordinate");
        Element coordL = document.createElement("COORD_L");
        Text textL = document.createTextNode(Double.toString(location.getCoordinates().getL()));
        Element coordW = document.createElement("COORD_W");
        Text textW = document.createTextNode(Double.toString(location.getCoordinates().getW()));
        element.appendChild(tab);
        tab.appendChild(district);
        tab.appendChild(coordinate);
        district.appendChild(textDistrict);
        coordinate.appendChild(coordL);
        coordinate.appendChild(coordW);
        coordL.appendChild(textL);
        coordW.appendChild(textW);
    }

}
