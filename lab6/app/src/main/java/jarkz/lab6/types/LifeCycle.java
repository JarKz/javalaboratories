package jarkz.lab6.types;

public class LifeCycle {

	private int maxDays;
	private int currentDay;

	private boolean ended;

	public LifeCycle(int maxDays){
		this.maxDays = maxDays;
	}

	public LifeCycle(LifeCycle lifeCycle){
		maxDays = lifeCycle.maxDays;
	}

	public boolean nextDay(){
		if (hasNextDay()){
			currentDay++;
			return true;
		}
		ended = true;
		return false;
	}

	public boolean hasNextDay(){
		return currentDay + 1 <= maxDays;
	}

	public boolean isEnded(){
		return ended;
	}

	public int currentDay(){
		return currentDay;
	}

	@Override
	public String toString() {
		return "LifeCycle[maxDays=" + maxDays + ", currentDay=" + currentDay + ", ended=" + ended + "]";
	}
}
