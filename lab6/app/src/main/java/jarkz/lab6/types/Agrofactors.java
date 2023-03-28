package jarkz.lab6.types;

import jarkz.lab6.types.lights.Light;
import jarkz.lab6.types.temperature.Temperature;
import jarkz.lab6.types.waters.WaterBalance;

public class Agrofactors {

	private Temperature temperature;
	private Light light;
	private WaterBalance waterBalance;

	public Agrofactors(
			Temperature temperature,
			Light light,
			WaterBalance waterBalance) {
		setTemperature(temperature);
		setLight(light);
		setWaterBalance(waterBalance);
	}

	public Temperature getTemperature() {
		return temperature;
	}

	public void setTemperature(Temperature temperature) {
		if (temperature == null)
			throw new NullPointerException("Temperature instance must be not null");
		this.temperature = temperature;
	}

	public Light getLight() {
		return light;
	}

	public void setLight(Light light) {
		if (light == null)
			throw new NullPointerException("Light instance must be not null");
		this.light = light;
	}

	public WaterBalance getWaterBalance() {
		return waterBalance;
	}

	public void setWaterBalance(WaterBalance waterBalance) {
		if (waterBalance == null)
			throw new NullPointerException("WaterBalance instance must be not null");
		this.waterBalance = waterBalance;
	}

}
