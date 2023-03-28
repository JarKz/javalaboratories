package jarkz.lab6.types.lights;

import jarkz.lab6.types.Measurement;

public class Light implements Measurement {

	private int value;

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "Light [value=" + value + "lux]";
	}

	@Override
	public int compareTo(Object o) {
		if (o == null)
			throw new NullPointerException("Object must be not null.");
		else if (!(o instanceof Light l))
			throw new ClassCastException("Received class must be instance of class Light.");
		if (this.value > l.value)
			return 1;
		else if (this.value < l.value)
			return -1;
		else
			return 0;
	}
}
