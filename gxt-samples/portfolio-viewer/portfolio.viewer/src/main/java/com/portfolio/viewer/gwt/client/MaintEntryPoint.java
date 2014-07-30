package com.portfolio.viewer.gwt.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.RootLayoutPanel;

public class MaintEntryPoint implements EntryPoint {

	public void onModuleLoad() {
		Button button = new Button("TEST BUTTON");
		button.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				Window.alert("Click!!!");
			}
		});
		
		RootLayoutPanel.get().add(button);
	}

}
