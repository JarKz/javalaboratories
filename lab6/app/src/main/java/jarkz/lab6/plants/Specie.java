package jarkz.lab6.plants;

import jarkz.lab6.types.ClimatZone;
import jarkz.lab6.types.LifeCycle;
import jarkz.lab6.types.Range;
import jarkz.lab6.types.Soil;
import jarkz.lab6.types.lights.Light;
import jarkz.lab6.types.temperature.Temperature;
import jarkz.lab6.types.waters.WaterBalance;

public class Specie {

	private String name;
	private PlantType type;

	private Soil soil;
	private ClimatZone zone;

	private Range<Temperature> temperatureRange;
	private Range<Light> lightRange;
	private Range<WaterBalance> waterBalanceRange;

	private LifeCycle lifeCycle;

	private Specie(){}

	public static Builder newBuilder(){
		return new Specie().new Builder();
	}

	public class Builder{
		private Specie instance;

		public Builder(){
			instance = new Specie();
		}

		public Builder setName(String name){
			instance.setName(name);
			return this;
		}
		
		public Builder setPlantType(PlantType type){
			instance.setPlantType(type);
			return this;
		}

		public Builder setSoil(Soil soil){
			instance.setSoil(soil);
			return this;
		}

		public Builder setClimatZone(ClimatZone climatZone){
			instance.setClimatZone(climatZone);
			return this;
		}

		public Builder setTemperatureRange(Range<Temperature> temperatureRange){
			instance.setTemperatureRange(temperatureRange);
			return this;
		}

		public Builder setLightRange(Range<Light> lightRange){
			instance.setLightRange(lightRange);
			return this;
		}
		
		public Builder setWaterBalanceRange(Range<WaterBalance> waterBalanceRange){
			instance.setWaterBalanceRange(waterBalanceRange);
			return this;
		}

		public Builder setLifeCycle(LifeCycle lifeCycle){
			instance.setLifeCycle(lifeCycle);
			return this;
		}

		public Specie build(){
			if (!allArgsEntered())
				throw new IllegalAccessError("All args not entered.");
			return instance;
		}

		private boolean allArgsEntered(){
			return instance.name != null
				&& instance.type != null
				&& instance.soil != null
				&& instance.zone != null
				&& instance.temperatureRange != null
				&& instance.lightRange != null
				&& instance.waterBalanceRange != null
				&& instance.lifeCycle != null;
		}
	}

	private void setClimatZone(ClimatZone zone) {
		if (zone == null)
			throw new NullPointerException("Climat zone instance must be not null.");
		this.zone = zone;
	}

	public ClimatZone getClimatZone() {
		return zone;
	}

	private void setSoil(Soil soil) {
		if (soil == null)
			throw new NullPointerException("Soil instance must be not null.");
		this.soil = soil;
	}

	private void setName(String name) {
		if (name == null)
			throw new NullPointerException("Name must be not null.");
		else if (name.isBlank() || name.isEmpty())
			throw new IllegalArgumentException("Name must be not blank or empty");
		this.name = name;
	}

	private void setPlantType(PlantType type) {
		if (type == null)
			throw new NullPointerException("Plant must be not null.");
		this.type = type;
	}

	private void setTemperatureRange(Range<Temperature> temperatureRange) {
		if (temperatureRange == null)
			throw new NullPointerException("Temperature range must be not null.");
		this.temperatureRange = temperatureRange;
	}

	private void setLightRange(Range<Light> lightRange) {
		if (lightRange == null)
			throw new NullPointerException("Light range must be not null.");
		this.lightRange = lightRange;
	}

	private void setWaterBalanceRange(Range<WaterBalance> waterBalanceRange) {
		if (waterBalanceRange == null)
			throw new NullPointerException("Water balance range must be not null.");
		this.waterBalanceRange = waterBalanceRange;
	}

	private void setLifeCycle(LifeCycle lifeCycle) {
		if (lifeCycle == null)
			throw new NullPointerException("Lifecycle must be not null.");
		this.lifeCycle = lifeCycle;
	}

	public String getName() {
		return name;
	}

	public PlantType getType() {
		return type;
	}

	public Soil getInfoAboutSoil() {
		return soil;
	}

	public Range<Temperature> getInfoAboutTemperature() {
		return temperatureRange;
	}

	public Range<Light> getInfoAboutLight() {
		return lightRange;
	}

	public Range<WaterBalance> getInfoAboutWaterBalance() {
		return waterBalanceRange;
	}

	public LifeCycle getInfoAboutLifeCycle() {
		return lifeCycle;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + name.hashCode();
		result = prime * result + type.hashCode();
		result = prime * result + soil.hashCode();
		result = prime * result + zone.hashCode();
		result = prime * result + temperatureRange.hashCode();
		result = prime * result + lightRange.hashCode();
		result = prime * result + waterBalanceRange.hashCode();
		result = prime * result + lifeCycle.hashCode();
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || !(obj instanceof Specie s)) {
			return false;
		}
		return name.equals(s.name)
				&& type == s.type
				&& soil.equals(s.soil)
				&& zone.equals(s.zone)
				&& temperatureRange.equals(s.temperatureRange)
				&& lightRange.equals(s.lightRange)
				&& waterBalanceRange.equals(s.waterBalanceRange)
				&& lifeCycle.equals(s.lifeCycle);
	}

	@Override
	public String toString() {
		return "Specie[name=" + name + ", type=" + type + ", soil=" + soil + ", zone=" + zone + ", temperatureRange="
				+ temperatureRange + ", lightRange=" + lightRange + ", waterBalanceRange=" + waterBalanceRange
				+ ", lifeCycle=" + lifeCycle + "]";
	}
}
