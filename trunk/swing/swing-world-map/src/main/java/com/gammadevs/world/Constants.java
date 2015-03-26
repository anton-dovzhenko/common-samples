package com.gammadevs.world;

import java.awt.*;
import java.awt.geom.Point2D;

public interface Constants {

    Color COUNTRY_DEFAULT_COLOR = new Color(80, 80, 80);
    Color COUNTRY_SELECTED_COLOR = new Color(100, 0, 190);
    Color COUNTRY_UPDATE_COLOR = new Color(255, 99, 71);
    Color COUNTRY_BORDER_COLOR = new Color(50, 50, 50);
    Color NUMBER_COMP_INNER_CIRCLE = new Color(0, 75, 0);

    int ANIMATION_TICK_PERIOD_IN_MS = 10;

    int WIDTH = 982;
    int HEIGHT = 670;
    int WORLD_MAP_HEIGHT = 624;
    int WORLD_MAP_WIDTH = 950;
    int CIRCLE_RADIUS = 34;
    double SCALE_INCREMENT = 1.2;
    String TITLE = "World Map";
    String SVG_FILE = "/World_map_-_low_resolution.svg";
    //String SVG_FILE = "/World_map_-_low_resolution_italy.svg";

    char COORDINATE_DELIMITER = ',';
    Point2D.Double CLOSE_PATH_VERTEX = new Point2D.Double(Integer.MIN_VALUE, Integer.MIN_VALUE);
    
    int MOUSE_WHEEL_MOVED_THRESHOLD = 20;
    
}
