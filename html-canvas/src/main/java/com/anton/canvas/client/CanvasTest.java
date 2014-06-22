package com.anton.canvas.client;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.CanvasPattern;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.event.dom.client.LoadEvent;
import com.google.gwt.event.dom.client.LoadHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;

/**
 * User: Anton Dovzhenko
 */
public class CanvasTest implements EntryPoint {
    
    public void onModuleLoad() {
        final Canvas canvas = Canvas.createIfSupported();
        canvas.setHeight("300");
        canvas.setWidth("500");

        Canvas subCanvas = Canvas.createIfSupported();
        subCanvas.setHeight("15");
        subCanvas.setWidth("15");

        Context2d context = subCanvas.getContext2d();

        context.setFillStyle("red");
        context.fillRect(0, 0, 15, 15);

        context.setStrokeStyle("#CE29CE");

        context.beginPath();
        context.moveTo(0, 0);
        context.lineTo(15, 15);
        context.stroke();

        context.beginPath();
        context.moveTo(3, 0);
        context.lineTo(15, 12);
        context.stroke();

        context.beginPath();
        context.moveTo(6, 0);
        context.lineTo(15, 9);
        context.stroke();

        context.beginPath();
        context.moveTo(9, 0);
        context.lineTo(15, 6);
        context.stroke();

        context.beginPath();
        context.moveTo(12, 0);
        context.lineTo(15, 3);
        context.stroke();

        context.beginPath();
        context.moveTo(0, 3);
        context.lineTo(12, 15);
        context.stroke();

        context.beginPath();
        context.moveTo(0, 6);
        context.lineTo(9, 15);
        context.stroke();

        context.beginPath();
        context.moveTo(0, 9);
        context.lineTo(6, 15);
        context.stroke();

        context.beginPath();
        context.moveTo(0, 12);
        context.lineTo(3, 15);
        context.stroke();


        context = canvas.getContext2d();
        CanvasPattern pattern = context.createPattern(subCanvas.getCanvasElement(), Context2d.Repetition.REPEAT_X);

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                context.drawImage(subCanvas.getCanvasElement(), 15 * i, 15 * j);
            }
        }

        RootPanel.get().add(canvas);
    }

    private void path1() {
        final Canvas canvas = Canvas.createIfSupported();
        canvas.setWidth("500");
        canvas.setHeight("500");
        final Context2d context = canvas.getContext2d();
        final Image image = new Image("http://127.0.0.1:8888/com.anton.canvas.CanvasTest/purple_bar.gif");
        //image.setVisible(false);
        image.addLoadHandler(new LoadHandler() {
            @Override
            public void onLoad(LoadEvent event) {
                ImageElement element = ImageElement.as(image.getElement());
                CanvasPattern pattern = context.createPattern(element, Context2d.Repetition.REPEAT);
                context.setFillStyle(pattern);
                //context.drawImage(element, 0, 0);
                context.fillRect(0, 0, 250, 250);
                RootPanel.get().clear();
                RootPanel.get().add(canvas);
            }
        });
        RootPanel.get().add(image);
    }
    
}
