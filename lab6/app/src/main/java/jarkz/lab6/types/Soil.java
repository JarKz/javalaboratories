package jarkz.lab6.types;

public class Soil {

	public enum Type {
		CLAY(0.6f, 0.6f, 0.0f),
		SANDY(0.1f, 0.3f, 0.6f),
		CALCAREOUS(0.4f, 0.6f, 0.5f),
		PEAT(0.3f, 0.3f, 0.4f);

		private float waterRatio;
		private float nutrientRatio;
		private float loseness;

		private Type(float waterRatio, float nutrientRatio, float loseness) {
			this.waterRatio = waterRatio;
			this.nutrientRatio = nutrientRatio;
			this.loseness = loseness;;
		}

		public float getWaterRatio(){
			return waterRatio;
		}

		public float getNutrientRatio(){
			return nutrientRatio;
		}

		public float getLoseness(){
			return loseness;
		}
	}

	private Type type;

	public Soil(Soil.Type type){
		if (type == null)
			throw new NullPointerException("Soil type instance must be not null.");
		this.type = type;
	}

	public Type getType() {
		return type;
	}

	public float getLoseness() {
		return type.loseness;
	}

	/**
	 * @param looseness as float number between 0 and 1 inclusive
	 * */
	public void setLooseness(float looseness) {
		if (Float.compare(looseness, 0f) < 0 || Float.compare(1f, looseness) < 0){
			throw new IllegalArgumentException("Loseness must be between 0 and 1 inclusive.");
		}
		this.type.loseness = looseness;
	}

	@Override
	public String toString() {
		return "Soil[type=" + type + ", loseness=" + type.loseness + "]";
	}
}
