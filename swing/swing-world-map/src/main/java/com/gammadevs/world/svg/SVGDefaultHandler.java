package com.gammadevs.world.svg;

import com.gammadevs.world.Country;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.LinkedList;
import java.util.List;

public class SVGDefaultHandler extends DefaultHandler {

    private final String PATH_TAG = "path";
    private final String ID_ATTR = "id";
    private final String DISTANCE_ATTR = "d";
    private final List<Country> countries = new LinkedList<Country>();

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (PATH_TAG.equals(qName)) {
            countries.add(new Country(attributes.getValue(ID_ATTR), attributes.getValue(DISTANCE_ATTR)));
        }
        super.startElement(uri, localName, qName, attributes);
    }

    public List<Country> getCountries() {
        return countries;
    }
}
