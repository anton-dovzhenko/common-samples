package com.gammadevs.world;

import java.awt.Rectangle;

import java.awt.geom.Point2D;

public class Country {

    private String id;
    private String path;
    private Point2D.Double[] vertices;
    private Rectangle countryBounds;

    public Country(String id, String path) {
        assert id != null;
        assert path != null;
        this.id = id;
        this.path = path;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Point2D.Double[] getVertices() {
        return vertices;
    }

    public void setVertices(Point2D.Double[] vertices) {
        this.vertices = vertices;
    }

    public void setCountryBounds(Rectangle countryBounds) {
        this.countryBounds = countryBounds;
    }

    public Rectangle getCountryBounds() {
        return countryBounds;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Country[").append("\n")
                .append("id: ").append(id).append("\n")
                .append("path: ").append(path).append("\n");
        if (countryBounds != null) {
            sb.append("; x: ").append(countryBounds.x).append("\n")
                    .append("; y: ").append(countryBounds.y).append("\n")
                    .append("; width: ").append(countryBounds.width).append("\n")
                    .append("; height: ").append(countryBounds.height).append("\n");
        }
        sb.append("]");
        return sb.toString();
    }

}
