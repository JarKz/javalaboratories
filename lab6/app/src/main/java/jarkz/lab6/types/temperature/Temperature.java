package jarkz.lab6.types.temperature;

import jarkz.lab6.types.Measurement;

public class Temperature implements Measurement{

	public enum MeasurementType {
		CELSIUS,
		KELVIN,
		FAHRENHEIT;
	}

	private int value;
	private MeasurementType type;

	public Temperature(int value, MeasurementType type){
		this.value = value;
		this.type = type;
	}

	public MeasurementType getType() {
		return type;
	}

	public void setMeasurementType(MeasurementType type){
		MeasurementType oldMeasurementType = this.type;
		this.type = type;

		convertValue(oldMeasurementType, type);
	}

	private void convertValue(MeasurementType from, MeasurementType to){
		switch (from) {
			case CELSIUS -> {
				if (to == MeasurementType.KELVIN){
					value += 273;
				} else {
					value = 9/5 * value + 32;
				}
			}
			case KELVIN -> {
				if (to == MeasurementType.CELSIUS){
					value -= 273;
				} else {
					int celsius = value - 273;
					value = 9/5 * celsius + 32;
				}
			}
			case FAHRENHEIT -> {
				if (to == MeasurementType.CELSIUS){
					value = 5/9 * (value - 32);
				} else {
					int celsius = 5/9 * (value - 32);
					value = celsius + 273;
				}
			}
		}
	}

	public int get() {
		return value;
	}

	public void set(int value) {
		this.value = value;
	}

	@Override
	public int compareTo(Object o){
		if (o == null)
			throw new NullPointerException("Object must be not null.");
		if (!(o instanceof Temperature t))
			throw new ClassCastException("Object must be instance of WaterBalance class.");
		if (type != t.type)
			t.setMeasurementType(type);
		if (value > t.value)
			return 1;
		else if (value < t.value)
			return -1;
		else
			return 0;
	}

	@Override
	public String toString() {
		return "Temperature[value=" + value + ", type=" + type + "]";
	}
}
