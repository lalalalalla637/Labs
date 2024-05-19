package xml;

import mybeans.Data;
import mybeans.DataSheet;
import org.xml.sax.Attributes;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

public class DataHandler extends DefaultHandler {

    private boolean isX, isY;
    private Data tmpData;
    private DataSheet dataSheet;

    public DataSheet getDataSheet(){
        return dataSheet;
    }

    @Override
    public void startDocument() {
        System.out.println("Start Document Parsing Process...");
        if (dataSheet == null){
            dataSheet = new DataSheet();
        }
    }

    @Override
    public void endDocument(){
        System.out.println("End Document Parsing Process...");
        dataSheet.calculateCoefficient();
        System.out.println(dataSheet.toString());
    }

    @Override
    public void startElement (String uri, String localName, String qName, Attributes attributes) throws org.xml.sax.SAXException {
        System.out.println("Начало обработки элемента: " + qName);
        switch (qName) {
            case "data":
                tmpData = new Data();
                if (attributes.getLength() > 0)
                    tmpData.setDate(attributes.getValue(0));
                break;
            case "x":
                isX = true;
                break;
            case "y":
                isY = true;
                break;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName){
        System.out.println("Конец обработки елемента: " + qName);
        switch (qName) {
            case "x":
                isX = false;
                break;
            case "y":
                isY = false;
                break;
            case "data":
                dataSheet.addData(tmpData);
                tmpData = null;
                break;
        }
    }

    @Override
    public void characters(char[] ch, int start, int length){
        String str = new String(ch, start, length).trim();
        if (isX)
            tmpData.setX(Double.parseDouble(str));
        else if (isY)
            tmpData.setY(Double.parseDouble(str));
    }

    @Override
    public void warning(SAXParseException ex){
        System.err.println("Warning: " + ex);
        System.err.println("line = " + ex.getLineNumber() + " column = " + ex.getColumnNumber());
    }

    @Override
    public void error(SAXParseException ex){
        System.err.println("Error: " + ex);
        System.err.println("line = " + ex.getLineNumber() + " column = " + ex.getColumnNumber());
    }

    @Override
    public void fatalError(SAXParseException ex){
        System.err.println("Fatal error: " + ex);
        System.err.println("line = " + ex.getLineNumber() + " column = " + ex.getColumnNumber());
    }

}
