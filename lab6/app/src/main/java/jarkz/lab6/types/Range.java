package jarkz.lab6.types;

public class Range<T extends Measurement> {

	private T low;
	private T high;

	public Range(T low, T high) {
		if (low == null || high == null)
			throw new NullPointerException("Objects must be not null.");
		if (low.compareTo(high) > 0){
			T oldLow = low;
			low = high;
			high = oldLow;
		}

		this.low = low;
		this.high = high;
	}

	public T getLow(){
		return low;
	}

	public T getHigh(){
		return high;
	}

	public boolean withinRange(T externalSource){
		if (low.compareTo(externalSource) <= 0 && high.compareTo(externalSource) >= 0){
			return true;
		}
		return false;
	}
}
