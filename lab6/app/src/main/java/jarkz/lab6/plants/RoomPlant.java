package jarkz.lab6.plants;

import jarkz.lab6.types.LifeCycle;
import jarkz.lab6.types.Soil;
import jarkz.lab6.types.lights.Light;
import jarkz.lab6.types.temperature.Temperature;
import jarkz.lab6.types.waters.WaterBalance;

public class RoomPlant implements Plant {

	private final int WATER_ABSORPTION_COEFFICIENT = 5;

	private String name;
	private Specie specie;

	private LifeCycle lifeCycle;
	private boolean died;

	public RoomPlant(
		String name,
		Specie specie
		){
		this.name = name;
		this.specie = specie;
		this.lifeCycle = new LifeCycle(specie.getInfoAboutLifeCycle());
	}

	public RoomPlant(Plant plant){
		if (!(plant instanceof RoomPlant roomPlant))
			throw new ClassCastException("Plant type must be equals room.");
		name = new String(roomPlant.getName());
		specie = roomPlant.getSpecie();
		lifeCycle = roomPlant.lifeCycle;
		died = roomPlant.died;
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
		return "RoomPlant[name=" + name + ", specie=" + specie + ", lifeCycle=" + lifeCycle + ", died=" + died + "]";
	}

	public boolean isDied(){
		return died;
	}
}
