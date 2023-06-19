package jarkz.lab6.plants;

import jarkz.lab6.types.LifeCycle;
import jarkz.lab6.types.Soil;
import jarkz.lab6.types.lights.Light;
import jarkz.lab6.types.temperature.Temperature;
import jarkz.lab6.types.waters.WaterBalance;

public class FlowerPlant implements Plant {

	private final int WATER_ABSORPTION_COEFFICIENT = 8;

	private String name;
	private Specie specie;

	private LifeCycle lifeCycle;
	private boolean died;

	public FlowerPlant(
		String name,
		Specie specie
		){
		this.name = name;
		this.specie = specie;
		this.lifeCycle = new LifeCycle(specie.getInfoAboutLifeCycle());
	}

	public FlowerPlant(Plant plant){
		if (!(plant instanceof FlowerPlant flowerPlant))
			throw new ClassCastException("Plant type must be equals flower.");
		name = new String(flowerPlant.getName());
		specie = flowerPlant.getSpecie();
		lifeCycle = flowerPlant.lifeCycle;
		died = flowerPlant.died;
	}

	@Override
	public void setName(String newName) {
		this.name = newName;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public Specie getSpecie() {
		return specie;
	}


	@Override
	public void growUp(Temperature temperature, Light light, WaterBalance waterBalance, Soil soil) {
		if (!died && specie.getInfoAboutTemperature().withinRange(temperature)
			&& specie.getInfoAboutLight().withinRange(light)
			&& specie.getInfoAboutWaterBalance().withinRange(waterBalance)
			&& specie.getInfoAboutSoil().getType() == soil.getType()
			&& lifeCycle.hasNextDay()
			){
			lifeCycle.nextDay();
			int remainingWater = waterBalance.getAsInt() / WATER_ABSORPTION_COEFFICIENT;
			waterBalance.set(remainingWater);
		} else {
			died = true;
		}
	}

	@Override
	public String toString() {
		return "FlowerPlant[name=" + name + ", specie=" + specie + ", lifeCycle=" + lifeCycle + ", died=" + died + "]";
	}

	public boolean isDied(){
		return died;
	}
}
