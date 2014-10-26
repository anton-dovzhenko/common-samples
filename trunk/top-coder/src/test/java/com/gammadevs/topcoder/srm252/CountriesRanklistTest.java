package com.gammadevs.topcoder.srm252;

import org.junit.Test;

import static org.junit.Assert.*;

public class CountriesRanklistTest {

    @Test
    public void testFindPlaces() throws Exception {
        CountriesRanklist countriesRanklist = new CountriesRanklist();
        assertArrayEquals(new String[] {"Poland 1 1", "Ukraine 2 2"}
                , countriesRanklist.findPlaces(new String[] {"Poland Krzysztof 101", "Ukraine Evgeni 30", "Ukraine Ivan 24"}));

        assertArrayEquals(new String[] {"CzechRep 1 2", "Poland 1 2" }
                , countriesRanklist.findPlaces(new String[] {"Poland Krzysztof 100", "CzechRep Martin 30", "CzechRep Jirka 25"}));

        assertArrayEquals(new String[] {"Germany 2 4", "Hungary 2 4", "Poland 2 2", "Slovakia 1 1"}
                , countriesRanklist.findPlaces(new String[] {"Slovakia Marian 270", "Hungary Istvan 24", "Poland Krzysztof 100",
                "Hungary Gyula 30", "Germany Tobias 27", "Germany Juergen 27"}));

        assertArrayEquals(new String[] {"USA 2 2", "Zimbabwe 3 3", "usa 1 1"}
                , countriesRanklist.findPlaces(new String[] {"usa Jack 14", "USA Jim 10", "USA Jim 10", "USA Jim 10",
                "USA Jim 10", "usa Jack 14", "usa Jack 14", "Zimbabwe Jack 10"}));
        assertArrayEquals(new String[] {"A 1 1", "B 2 2", "C 2 2", "D 4 4" }
                , countriesRanklist.findPlaces(new String[] {"A a 9", "A b 9", "A c 9", "A d 9",
                "B e 9", "B f 9", "B g 8", "B h 8",
                "C i 9", "C j 9", "C k 9", "C l 7",
                "D m 1", "D n 1", "D o 1", "D p 1"}));
    }
}