package com.gammadevs.inverview.samples.common;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anton on 11/10/2014.
 */
public class MainInnerListSample {

    public static void main(String[] args) {
        List a = new ArrayList();
        List b = new ArrayList();

        a.add(a);
        b.add(b);

        System.out.println(a.equals(b));
    }

}
