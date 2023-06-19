package jarkz.lab6.plants;

import jarkz.lab6.types.Soil;
import jarkz.lab6.types.lights.Light;
import jarkz.lab6.types.temperature.Temperature;
import jarkz.lab6.types.waters.WaterBalance;

public interface Plant extends Cloneable {
	public String getName();
	public void setName(String newName);

	public Specie getSpecie();

	public void growUp(Temperature temperature, Light light, WaterBalance waterBalance, Soil soil);

	public boolean isDied();
}
