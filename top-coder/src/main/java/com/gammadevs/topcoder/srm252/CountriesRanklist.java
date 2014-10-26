package com.gammadevs.topcoder.srm252;

import java.util.*;

/**
 * 271.94 of 900
 * Created by Anton on 10/26/2014.
 */
public class CountriesRanklist {

    public String[] findPlaces(String[] knownPoints) {
        Map<String, Country> countries = new HashMap<String, Country>();
        int lastScore = Integer.MAX_VALUE;
        for (String point : knownPoints) {
            String[] data = point.split(" ");
            String name = data[0];
            Integer score = Integer.parseInt(data[2]);
            if (!countries.containsKey(name)) {
                countries.put(name, new Country(name));
            }
            Country country = countries.get(name);
            country.addScore(score);
            if (score < lastScore) {
                lastScore = score;
            }
        }
        for (Country country : countries.values()) {
            country.setUpperBound(lastScore);
        }
        for (Country country : countries.values()) {
            country.computeBestAndWorst(countries.values());
        }
        List<Country> list = new ArrayList<Country>(countries.values());
        Collections.sort(list);
        String[] result = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            result[i] = list.get(i).toString();
        }
        return result;
    }

    private static class Country implements Comparable<Country> {

        private final String name;
        private final List<Integer> scores = new ArrayList<Integer>();
        private int upperBound;
        private int best = 1;
        private int worst = 1;

        private Country(String country) {
            this.name = country;
        }

        private int getMaxScore() {
            int maxScore = 0;
            for (Integer score : scores) {
                maxScore += score;
            }
            maxScore += (4 - scores.size()) * (upperBound - 1);
            return maxScore;
        }

        private int getMinScore() {
            int minScore = 0;
            for (Integer score : scores) {
                minScore += score;
            }
            return minScore;
        }

        public void setUpperBound(int upperBound) {
            this.upperBound = upperBound;
        }

        public void addScore(int score) {
            scores.add(score);
        }

        public void computeBestAndWorst(Collection<Country> countries) {
            for (Country country : countries) {
                if (this != country) {
                    if (!canBeHigherThan(country)) {
                        best++;
                    }
                    if (canBeLowerThan(country)) {
                        worst++;
                    }
                }
            }
        }

        private boolean canBeHigherThan(Country country) {
            int thisMaxScore = getMaxScore();
            int thatMinScore = country.getMinScore();
            if (thisMaxScore >= thatMinScore) {
                return true;
            } else {
                return false;
            }
        }

        private boolean canBeLowerThan(Country country) {
            int thisMinScore = getMinScore();
            int thatMaxScore = country.getMaxScore();
            if (thisMinScore < thatMaxScore) {
                return true;
            } else {
                return false;
            }
        }

        @Override
        public int compareTo(Country o) {
            return name.compareTo(o.name);
        }

        @Override
        public String toString() {
            return name + " " + best + " " + worst;
        }
    }
}
