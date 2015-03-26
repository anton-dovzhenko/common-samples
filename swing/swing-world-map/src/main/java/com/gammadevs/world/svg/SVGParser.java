package com.gammadevs.world.svg;

import com.gammadevs.world.Constants;
import com.gammadevs.world.Country;
import com.gammadevs.world.exceptions.IllegalPathCommandException;
import java.awt.Rectangle;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.awt.geom.Point2D;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

public class SVGParser {

    public static String[] getPathCommands(String path) {
        final StringTokenizer st = new StringTokenizer(path, "MmLlHhVvZz", true);
        final List<String> commands = new LinkedList<>();
        while (st.hasMoreTokens()) {
            String token = st.nextToken();
            if (st.hasMoreTokens()) {
                token += st.nextToken();
            }
            commands.add(token);
        }
        return commands.toArray(new String[]{});
    }

    public static Point2D.Double getNextPoint(String command, Point2D.Double previousPoint) throws IllegalPathCommandException {
        final char direction = command.charAt(0);
        int indexOfDelimiter = command.indexOf(Constants.COORDINATE_DELIMITER);
        switch (direction) {
            case 'z':
            case 'Z':
                return Constants.CLOSE_PATH_VERTEX;
            case 'm':
            case 'M':
                return new Point2D.Double(Double.parseDouble(command.substring(1, indexOfDelimiter))
                        , Double.parseDouble(command.substring(indexOfDelimiter + 1)));
            case 'h':
                return new Point2D.Double(
                        previousPoint.getX() + Double.parseDouble(command.substring(1))
                        , previousPoint.getY());
            case 'H':
                return new Point2D.Double(
                        Double.parseDouble(command.substring(1))
                        , previousPoint.getY());
            case 'v':
                return new Point2D.Double(
                        previousPoint.getX()
                        , previousPoint.getY() + Double.parseDouble(command.substring(1)));
            case 'V':
                return new Point2D.Double(
                        previousPoint.getX()
                        , Double.parseDouble(command.substring(1)));
            case 'l':
                if (indexOfDelimiter < 1) {
                    indexOfDelimiter = command.lastIndexOf('-');
                    return new Point2D.Double(
                            previousPoint.getX() + Double.parseDouble(command.substring(1, indexOfDelimiter))
                            , previousPoint.getY() + Double.parseDouble(command.substring(indexOfDelimiter)));
                } else {
                    return new Point2D.Double(
                            previousPoint.getX() + Double.parseDouble(command.substring(1, indexOfDelimiter))
                            , previousPoint.getY() + Double.parseDouble(command.substring(indexOfDelimiter + 1)));
                }
            case 'L':
                if (indexOfDelimiter < 1) {
                    indexOfDelimiter = command.lastIndexOf('-');
                    return new Point2D.Double(Double.parseDouble(command.substring(1, indexOfDelimiter))
                            , Double.parseDouble(command.substring(indexOfDelimiter)));
                } else {
                    return new Point2D.Double(
                             Double.parseDouble(command.substring(1, indexOfDelimiter))
                            , Double.parseDouble(command.substring(indexOfDelimiter + 1)));
                }
        }
        throw new IllegalPathCommandException(String.format("Unknown command: %s", command));
    }

    public static Point2D.Double[] getPolygonVertices(String path) throws IllegalPathCommandException {
        String[] commands = getPathCommands(path);
        Point2D.Double[] vertices = new Point2D.Double[commands.length];
        Point2D.Double previousVertex = null;
        for (int i = 0; i < commands.length; i++) {
            previousVertex = getNextPoint(commands[i], previousVertex);
            vertices[i] = previousVertex;
        }
        return vertices;
    }

    public static List<Country> retrieveCountries(InputStream fileIS) throws ParserConfigurationException, SAXException, IOException {
        final SVGDefaultHandler handler = new SVGDefaultHandler();
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser saxParser = factory.newSAXParser();
        saxParser.parse(fileIS, handler);
        return handler.getCountries();
    }

    public static void initCountryVerticesAndPosition(List<Country> countries) {
        for (Country country : countries) {
            try {
                Point2D.Double[] vertices = getPolygonVertices(country.getPath());
                Rectangle countryBoundsRectangle = calculateCountryBounds(vertices);
                for (Point2D.Double vertex : vertices) {
                    if (vertex == Constants.CLOSE_PATH_VERTEX) {
                        continue;
                    }
                    vertex.setLocation(vertex.getX() - countryBoundsRectangle.x, vertex.getY() - countryBoundsRectangle.y);
                }
                country.setCountryBounds(countryBoundsRectangle);
                country.setVertices(vertices);
            } catch (IllegalPathCommandException e) {
                System.out.println(e.getMessage());
                System.out.println(String.format("Country %s has illegal path", country));
            }
        }
    }

    public static Rectangle calculateCountryBounds(Point2D.Double[] vertices) throws IllegalPathCommandException {
        double minX = Double.POSITIVE_INFINITY;
        double minY = Double.POSITIVE_INFINITY;
        double maxX = Double.NEGATIVE_INFINITY;
        double maxY = Double.NEGATIVE_INFINITY;
        for (Point2D.Double vertex : vertices) {
            if (vertex == Constants.CLOSE_PATH_VERTEX) {
                continue;
            }
            if (vertex.getX() < minX) {
                minX = vertex.getX();
            }
            if (vertex.getX() > maxX) {
                maxX = vertex.getX();
            }
            if (vertex.getY() < minY) {
                minY = vertex.getY();
            }
            if (vertex.getY() > maxY) {
                maxY = vertex.getY();
            }
        }
        return new Rectangle((int) minX, (int) minY, (int) Math.ceil(maxX - minX) + 1, (int) Math.ceil(maxY - minY) + 1);
    }
}
