package com.gammadevs.gwt.mpa.tabpanel.client.places;

import com.gammadevs.gwt.mpa.tabpanel.client.TabType;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.web.bindery.requestfactory.shared.RequestFactory;

/**
 * Created by Anton on 3/23/2015.
 */
public class TabPlace extends Place {

    private final RequestFactory requestFactory;
    private final TabModel model;

    public TabPlace(RequestFactory requestFactory, TabModel model) {
        this.requestFactory = requestFactory;
        this.model = model;
    }

    public TabModel getModel() {
        return model;
    }

    public static class Tokenizer implements PlaceTokenizer<TabPlace> {

        @Override
        public TabPlace getPlace(String token) {
            return null;
        }

        @Override
        public String getToken(TabPlace place) {
            return null;
        }
    }
}
