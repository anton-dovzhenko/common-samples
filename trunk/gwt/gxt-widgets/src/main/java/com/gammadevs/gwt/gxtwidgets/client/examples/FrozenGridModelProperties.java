package com.gammadevs.gwt.gxtwidgets.client.examples;

import com.google.gwt.editor.client.Editor;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

import java.util.Date;

/**
 * Created by Anton on 3/9/2015.
 */
public interface FrozenGridModelProperties extends PropertyAccess<FrozenGridModel> {

    @Editor.Path("id")
    ModelKeyProvider<FrozenGridModel> key();

    ValueProvider<FrozenGridModel, Long> id();
    ValueProvider<FrozenGridModel, Date> time();
    ValueProvider<FrozenGridModel, Double> metric1();
    ValueProvider<FrozenGridModel, Double> metric2();
    ValueProvider<FrozenGridModel, Double> metric3();
    ValueProvider<FrozenGridModel, Double> metric4();
    ValueProvider<FrozenGridModel, Double> metric5();
    ValueProvider<FrozenGridModel, Double> metric6();
    ValueProvider<FrozenGridModel, Double> metric7();
    ValueProvider<FrozenGridModel, Double> metric8();
    ValueProvider<FrozenGridModel, Double> metric9();
    ValueProvider<FrozenGridModel, Double> metric10();
    ValueProvider<FrozenGridModel, Double> metric11();
    ValueProvider<FrozenGridModel, Double> metric12();
    ValueProvider<FrozenGridModel, Double> metric13();

}
