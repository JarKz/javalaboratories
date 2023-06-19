package jarkz.lab6.types.waters;

import jarkz.lab6.types.Measurement;

public class WaterBalance implements Measurement {

	private int percentage;

	public WaterBalance(int waterPercentage){
		set(waterPercentage);
	}

	public int getAsInt() {
		return percentage;
	}

	public String get() {
		return percentage + "%";
	}

	public void set(int waterPercentage) {
		if (waterPercentage < 0 || 100 < waterPercentage)
			throw new IllegalArgumentException("Input water percentage must be between 0 and 100.");
		percentage = waterPercentage;
	}

	@Override
	public int compareTo(Object o) {
		if (o == null)
			throw new NullPointerException("Object must be not null.");
		if (!(o instanceof WaterBalance wb))
			throw new ClassCastException("Object must be instance of WaterBalance class.");

		if (percentage > wb.percentage) {
			return 1;
		} else if (percentage < wb.percentage) {
			return -1;
		} else {
			return 0;
		}
	}

	@Override
	public String toString() {
		return "WaterBalance[percentage=" + percentage + "%]";
	}
}
