package com.gammadevs.world;

import com.gammadevs.world.exceptions.IllegalPathCommandException;
import com.gammadevs.world.svg.SVGParser;
import junit.framework.Assert;
import org.junit.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.awt.geom.Point2D;
import java.io.IOException;
import java.util.List;

public class SVGParserTest {

    private static final double DELTA = 0.0000001;
    private static final String USA_PATH = "M148.76,158.34l-1,4.02l-3.49-2.26h-1.74l-1,4.27l-12.21,27.36l3.24,23.84l3.99,2.01l0.75,6.53h8.22l7.97,6.02l15.69,1.51l1.74,8.03l2.49,1.76l3.49-3.51l2.74,1.25l2.49,11.54l4.23,2.76l3.49-6.53l10.71-7.78l6.97,3.26l5.98,0.5l0.25-3.76l12.45,0.25l2.49,2.76l0.5,6.27l-1.49,3.51l1.74,6.02h3.74l3.74-5.77l-1.49-2.76l-1.49-6.02l2.24-6.78l10.21-8.78l7.72-2.26l-1-7.28l10.71-11.55l10.71-1.76L272.8,199l10.46-6.02v-8.03l-1-0.5l-3.74,1.25l-0.5,4.92l-12.43,0.15l-9.74,6.47l-15.29,5l-2.44-2.99l6.94-10.5l-3.43-3.27l-2.33-4.44l-4.83-3.88l-5.25-0.44l-9.92-6.77L148.76,158.34L148.76,158.34z";
    private static final String SVG_FILE_LOCATION_RELATIVE= "/World_map_-_low_resolution.svg";

    @Test
    public void testGetPathCommands() {
        String[] commands = SVGParser.getPathCommands(USA_PATH);
        Assert.assertEquals(57, commands.length);
        Assert.assertEquals("M148.76,158.34", commands[0]);
        Assert.assertEquals("l-1,4.02", commands[1]);
        Assert.assertEquals("l-3.49-2.26", commands[2]);
        Assert.assertEquals("h-1.74", commands[3]);
        Assert.assertEquals("l-1,4.27", commands[4]);
        Assert.assertEquals("l-12.21,27.36", commands[5]);
        Assert.assertEquals("l3.24,23.84", commands[6]);
        Assert.assertEquals("l3.99,2.01", commands[7]);
        Assert.assertEquals("l0.75,6.53", commands[8]);
        Assert.assertEquals("h8.22", commands[9]);
        Assert.assertEquals("l7.97,6.02", commands[10]);
        Assert.assertEquals("l15.69,1.51", commands[11]);
        Assert.assertEquals("l1.74,8.03", commands[12]);

        Assert.assertEquals("L148.76,158.34", commands[54]);
        Assert.assertEquals("L148.76,158.34", commands[55]);
        Assert.assertEquals("z", commands[56]);
    }

    @Test
    public void testGetNextPoint() throws IllegalPathCommandException {
        Assert.assertEquals(new Point2D.Double(148.76, 158.34), SVGParser.getNextPoint("M148.76,158.34", null));
        Assert.assertEquals(new Point2D.Double(148.76, 158.34), SVGParser.getNextPoint("M148.76,158.34", new Point2D.Double(1, 1)));
        Assert.assertEquals(new Point2D.Double(147.76, 162.36), SVGParser.getNextPoint("l-1,4.02", new Point2D.Double(148.76, 158.34)));
        Assert.assertEquals(new Point2D.Double(147.76, 154.32), SVGParser.getNextPoint("l-1-4.02", new Point2D.Double(148.76, 158.34)));
        Assert.assertEquals(new Point2D.Double(156.98, 158.34), SVGParser.getNextPoint("h8.22", new Point2D.Double(148.76, 158.34)));
        Assert.assertEquals(new Point2D.Double(148.76, 150.31), SVGParser.getNextPoint("v-8.03", new Point2D.Double(148.76, 158.34)));
    }

    @Test
    public void testGetPolygonVertices() throws IllegalPathCommandException {
        Point2D.Double[] vertices = SVGParser.getPolygonVertices(USA_PATH);
        Assert.assertEquals(57, vertices.length);
        TestUtils.assertEquals(new Point2D.Double(148.76, 158.34), vertices[0], DELTA);
        TestUtils.assertEquals(new Point2D.Double(147.76, 162.36), vertices[1], DELTA);
        TestUtils.assertEquals(new Point2D.Double(144.27, 160.1), vertices[2], DELTA);
        TestUtils.assertEquals(new Point2D.Double(142.53, 160.1), vertices[3], DELTA);
    }

    @Test
    public void testRetrieveCountries() throws IOException, SAXException, ParserConfigurationException {
        List<Country> countries = SVGParser.retrieveCountries(ClassLoader.class.getResourceAsStream(SVG_FILE_LOCATION_RELATIVE));
        Assert.assertEquals(291, countries.size());
        Assert.assertEquals("kalimantan", countries.get(0).getId());
        Assert.assertEquals("M781.68,324.4l-2.31,8.68l-12.53,4.23l-3.75-4.4l-1.82,0.5l3.4,13.12l5.09,0.57l6.79,2.57v2.57l3.11-0.57l4.53-6.27v-5.13l2.55-5.13l2.83,0.57l-3.4-7.13l-0.52-4.59L781.68,324.4L781.68,324.4z", countries.get(0).getPath());
        Assert.assertEquals("crete", countries.get(290).getId());
        Assert.assertEquals("M516.76,230.59l1.63,0.05l0.68,1.01h2.37l1.58-0.58l0.53,0.64l-1.05,1.38l-4.63,0.16l-0.84-1.11l-0.89-0.53L516.76,230.59L516.76,230.59z", countries.get(290).getPath());
    }

}
