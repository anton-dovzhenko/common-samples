package com.gammadevs.world;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.gammadevs.world.components.CountryComponent;

public class UpdateGeneratorThread extends Thread {
	
	private final List<CountryComponent> countries;
	private final Random generator = new Random();
	
	public UpdateGeneratorThread(Map<String, CountryComponent> countryStorage) {
		this.countries = new ArrayList<>(countryStorage.values());
		setDaemon(true);
	}
	
	@Override
	public void run() {
        while (true) {
            try {
				Thread.sleep(3000);
				CountryComponent countryComponent = countries.get(generator.nextInt(countries.size()));
				countryComponent.sendUpdate(generator.nextInt(999));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
