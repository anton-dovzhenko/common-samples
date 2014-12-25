package com.gammadevs.inverview.samples;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Anton on 11/11/2014.
 */
public class XmlToJson {

    public String convert(InputStream xml) throws ParserConfigurationException, SAXException, IOException {
        StringBuilder sb = new StringBuilder();
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser saxParser = factory.newSAXParser();
        saxParser.parse(xml, new XmlHandler(sb));
        return sb.toString();
    }

    private static class XmlHandler extends DefaultHandler {

        private final StringBuilder sb;

        private boolean root = true;
        private boolean hasAttributes;

        private XmlHandler(StringBuilder sb) {
            this.sb = sb;
        }

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes)
                throws SAXException {
            if (root) {
                sb.append("{");
            } else {
                sb.append("\"").append(qName).append("\"").append(": {");
            }
            for (int i = 0; i < attributes.getLength(); i++) {
                sb.append("\"").append(attributes.getQName(i)).append("\": ")
                        .append("\"").append(attributes.getValue(i)).append("\",");
            }
            hasAttributes = attributes.getLength() > 0;
            root = false;
        }

        @Override
        public void endElement(String uri, String localName, String qName)
                throws SAXException {
            sb.append("}");
        }

        @Override
        public void characters(char ch[], int start, int length) throws SAXException {
            sb.append("_text: \"").append(new String(ch, start, length)).append("\"");
        }

    }
}
