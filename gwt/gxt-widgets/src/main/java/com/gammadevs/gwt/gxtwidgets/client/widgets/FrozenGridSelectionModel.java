package com.gammadevs.gwt.gxtwidgets.client.widgets;

import com.sencha.gxt.widget.core.client.grid.GridSelectionModel;

import java.util.List;

class FrozenGridSelectionModel<M> extends GridSelectionModel<M> {

    @Override
    public void doSelect(List<M> models, boolean keepExisting, boolean suppressEvent) {
        super.doSelect(models, keepExisting, suppressEvent);
    }

}