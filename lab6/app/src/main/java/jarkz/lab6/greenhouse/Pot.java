package jarkz.lab6.greenhouse;

import jarkz.lab6.plants.Plant;
import jarkz.lab6.types.Soil;
import jarkz.lab6.types.lights.Light;
import jarkz.lab6.types.temperature.Temperature;
import jarkz.lab6.types.waters.WaterBalance;

public class Pot {

	private Plant plant;

	private Soil soil;

	private Light light;
	private Temperature temperature;
	private WaterBalance waterBalance;

	public Pot(
		Plant plant,
		Soil soil,
		Temperature temperature,
		Light light,
		WaterBalance waterBalance
		){
		setPlant(plant);
		setSoil(soil);
		setTemperature(temperature);
		setLight(light);
		setWaterBalance(waterBalance);
	}

	public Plant getPlant() {
		return plant;
	}

	public void setPlant(Plant plant) {
		if (plant == null)
			throw new NullPointerException("Plant instance must be not null.");
		this.plant = plant;
	}

	public Soil getSoil() {
		return soil;
	}

	public void setSoil(Soil soil) {
		if (soil == null)
			throw new NullPointerException("Soil instance must be not null.");
		this.soil = soil;
	}

	public Light getLight() {
		return light;
	}

	public void setLight(Light light) {
		if (light == null)
			throw new NullPointerException("Light instance must be not null.");
		this.light = light;
	}

	public Temperature getTemperature() {
		return temperature;
	}

	public void setTemperature(Temperature temperature) {
		if (temperature == null)
			throw new NullPointerException("Temperature instance must be not null.");
		this.temperature = temperature;
	}

	public WaterBalance getWaterBalance() {
		return waterBalance;
	}

	public void setWaterBalance(WaterBalance waterBalance) {
		if (waterBalance == null)
			throw new NullPointerException("WaterBalance instance must be not null.");
		this.waterBalance = waterBalance;
	}

	public void digOutPlant(){
		plant = null;
	}

	public boolean isEmpty(){
		return plant == null;
	}
}
