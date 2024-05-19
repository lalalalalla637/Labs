package org.example.task0;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class DOMParserXML {

    public void parse(String documentPath) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document document = documentBuilder.parse(new File(documentPath));
        Element element = document.getDocumentElement();
        NodeList nodeList = element.getElementsByTagName("x");
        printElement(nodeList, 0);

    }

    private void printElement(NodeList nodeList, int tab){
        for (int i = 0; i < nodeList.getLength(); i++) {
            if(nodeList.item(i) instanceof Element){
                System.out.print("\n" + getTab(tab) + ((Element)nodeList.item(i)).getTagName());
                if(((Element)nodeList.item(i)).hasAttribute("name")){
                    System.out.print(" \"" + ((Element)nodeList.item(i)).getAttribute("name") + "\"");
                }
                if(nodeList.item(i).hasChildNodes()){
                    printElement(nodeList.item(i).getChildNodes(), ++tab);
                }
            }
        }
    }

    private String getTab(int tab){
        String str = "";
        for(int i = 0; i < tab; i++){
            str += "\t";
        }
        return str;
    }

}
