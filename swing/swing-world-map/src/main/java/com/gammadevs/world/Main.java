package com.gammadevs.world;

import com.gammadevs.world.animation.AnimationTickActionListener;
import com.gammadevs.world.components.CountryComponent;
import com.gammadevs.world.components.NumberComponent;
import com.gammadevs.world.listeners.CountryComponentMouseListener;
import com.gammadevs.world.listeners.WorldMapMouseListener;
import com.gammadevs.world.svg.SVGParser;
import com.gammadevs.world.utils.LookAndFeelUtils;

import org.xml.sax.SAXException;

import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;

import java.awt.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
	
	private static Map<String, CountryComponent> countryStorage = new HashMap<>();
	private static Map<String, NumberComponent> numbersStorage = new HashMap<>();

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable(){

            @Override
            public void run() {
                LookAndFeelUtils.setNimbusLokAndFeel();
                LookAndFeelUtils.turnOnAntialiasing();
                JFrame frame = new JFrame();
                frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                frame.setResizable(false);
                frame.setTitle(Constants.TITLE);
                frame.setSize(Constants.WIDTH, Constants.HEIGHT);
                final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
                frame.setLocation((screenSize.width - Constants.WIDTH) / 2, (screenSize.height - Constants.HEIGHT) / 2);
                initWorldMap(frame);
                frame.setVisible(true);
                new Timer(Constants.ANIMATION_TICK_PERIOD_IN_MS, AnimationTickActionListener.INSTANCE).start();
                new UpdateGeneratorThread(countryStorage).start();
            }

        });
    }

    private static void initWorldMap(final JFrame frame) {
        try {
            final List<Country> countries = SVGParser.retrieveCountries(ClassLoader.class.getResourceAsStream(Constants.SVG_FILE));
            SVGParser.initCountryVerticesAndPosition(countries);

            final JPanel worldMapPanel = new JPanel();
            worldMapPanel.setPreferredSize(new Dimension(Constants.WORLD_MAP_WIDTH, Constants.WORLD_MAP_HEIGHT));
            worldMapPanel.setLayout(null);

            final JScrollPane scrollPane = new JScrollPane(worldMapPanel);
            frame.getContentPane().add(scrollPane);

            for (final Country country : countries) {
                final CountryComponent component = new CountryComponent(country);
                component.setToolTipText(country.getId());
                final NumberComponent numberComponent = new NumberComponent(country, Constants.CIRCLE_RADIUS, 123);

                countryStorage.put(country.getId(), component);
                numbersStorage.put(country.getId(), numberComponent);

                component.addNumberComponent(numberComponent);
                component.setToolTipText(country.getId());
                component.setOpaque(false);
                CountryComponentMouseListener countryComponentMouseListener = new CountryComponentMouseListener(scrollPane, component);
                component.addMouseListener(countryComponentMouseListener);
                component.addMouseMotionListener(countryComponentMouseListener);
            }
            for (NumberComponent numberComponent : numbersStorage.values()) {
                worldMapPanel.add(numberComponent);
            }
            for (CountryComponent countryComponent : countryStorage.values()) {
                worldMapPanel.add(countryComponent);
            }
            final WorldMapMouseListener worldMapMouseListener = new WorldMapMouseListener(1, scrollPane, countryStorage.values());
            worldMapPanel.addMouseListener(worldMapMouseListener);
            worldMapPanel.addMouseMotionListener(worldMapMouseListener);
            worldMapPanel.addMouseWheelListener(worldMapMouseListener);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
