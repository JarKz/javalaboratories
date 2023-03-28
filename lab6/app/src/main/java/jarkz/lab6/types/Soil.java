package jarkz.lab6.types;

public class Soil {

	public enum Type {
		CLAY(0.6f, 0.6f),
		SANDY(0.1f, 0.3f),
		CALCAREOUS(0.4f, 0.6f),
		PEAT(0.3f, 0.3f);

		private float waterRatio;
		private float nutrientRatio;

		private Type(float waterRatio, float nutrientRatio) {
			this.waterRatio = waterRatio;
			this.nutrientRatio = nutrientRatio;
		}

		public float getWaterRatio(){
			return waterRatio;
		}

		public float getNutrientRatio(){
			return nutrientRatio;
		}
	}

	private Type type;
	private float loseness;

	public Soil(Soil.Type type){
		if (type == null)
			throw new NullPointerException("Soil type instance must be not null.");
		this.type = type;
	}

	public Type getType() {
		return type;
	}

	public float getLoseness() {
		return loseness;
	}

	/**
	 * @param looseness as float number between 0 and 1 inclusive
	 * */
	public void setLooseness(float looseness) {
		if (Float.compare(looseness, 0f) < 0 || Float.compare(1f, looseness) < 0){
			throw new IllegalArgumentException("Loseness must be between 0 and 1 inclusive.");
		}
		this.loseness = looseness;
	}
}
