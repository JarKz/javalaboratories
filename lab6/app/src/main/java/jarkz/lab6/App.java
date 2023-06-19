package jarkz.lab6;

import java.util.logging.Logger;

import jarkz.lab6.greenhouse.GreenHouse;
import jarkz.lab6.plants.PlantType;
import jarkz.lab6.plants.Specie;
import jarkz.lab6.types.Agrofactors;
import jarkz.lab6.types.ClimatZone;
import jarkz.lab6.types.LifeCycle;
import jarkz.lab6.types.Range;
import jarkz.lab6.types.Soil;
import jarkz.lab6.types.lights.Light;
import jarkz.lab6.types.temperature.Temperature;
import jarkz.lab6.types.temperature.Temperature.MeasurementType;
import jarkz.lab6.types.waters.WaterBalance;

public class App {

	private static Logger logger = Logger.getLogger(App.class.getName());

	public static void main(String[] args) {
		GreenHouse house = new GreenHouse(10);
		house.buyNewSpecie(Specie.newBuilder()
				.setClimatZone(ClimatZone.TEMPERATE)
				.setLifeCycle(new LifeCycle(135))
				.setLightRange(new Range<Light>(new Light(10_000), new Light(20_000)))
				.setTemperatureRange(new Range<Temperature>(new Temperature(-5, MeasurementType.CELSIUS),
						new Temperature(35, MeasurementType.CELSIUS)))
				.setWaterBalanceRange(new Range<WaterBalance>(new WaterBalance(30), new WaterBalance(70)))
				.setPlantType(PlantType.FLOWER)
				.setSoil(new Soil(Soil.Type.CLAY))
				.setName("Rose")
				.build());
		Agrofactors agrofactorsForRose = new Agrofactors(new Temperature(20, MeasurementType.CELSIUS),
				new Light(12_000),
				new WaterBalance(50));
		house.plantNewPlant("First Plant", "Rose", new Soil(Soil.Type.CLAY), agrofactorsForRose);

		house.buyNewSpecie(Specie.newBuilder()
				.setClimatZone(ClimatZone.SUBTROPICAL)
				.setLifeCycle(new LifeCycle(365 * 8))
				.setLightRange(new Range<Light>(new Light(1_000), new Light(5_000)))
				.setTemperatureRange(new Range<Temperature>(new Temperature(10, MeasurementType.CELSIUS),
						new Temperature(37, MeasurementType.CELSIUS)))
				.setWaterBalanceRange(new Range<WaterBalance>(new WaterBalance(50), new WaterBalance(80)))
				.setPlantType(PlantType.ROOM)
				.setSoil(new Soil(Soil.Type.SANDY))
				.setName("Ficus")
				.build());
		Agrofactors agrofactorsForFicus = new Agrofactors(new Temperature(25, MeasurementType.CELSIUS),
				new Light(3_000),
				new WaterBalance(65));
		house.plantNewPlant("Second Plant", "Ficus", new Soil(Soil.Type.SANDY), agrofactorsForFicus);

		house.buyNewSpecie(Specie.newBuilder()
				.setClimatZone(ClimatZone.TEMPERATE)
				.setLifeCycle(new LifeCycle(365 * 15))
				.setLightRange(new Range<Light>(new Light(2_000), new Light(3_000)))
				.setTemperatureRange(new Range<Temperature>(new Temperature(-10, MeasurementType.CELSIUS),
						new Temperature(30, MeasurementType.CELSIUS)))
				.setWaterBalanceRange(new Range<WaterBalance>(new WaterBalance(20), new WaterBalance(60)))
				.setPlantType(PlantType.BUSH)
				.setSoil(new Soil(Soil.Type.PEAT)) // Торфяная
				.setName("Barberry") // Барбарис
				.build());
		Agrofactors agrofactorsForBarberry = new Agrofactors(new Temperature(15, MeasurementType.CELSIUS),
				new Light(2_300),
				new WaterBalance(44));
		house.plantNewPlant("Third Plant", "Barberry", new Soil(Soil.Type.PEAT), agrofactorsForBarberry);

		house.getBusyPots().forEach(p -> {
			logger.info(p.toString());
		});

		house.nextDay();

		house.getBusyPots().forEach(p -> {
			logger.info(p.toString());
		});

		house.getBusyPots().stream()
				.filter(p -> p.getPlant().getName().equals("Third Plant"))
				.findFirst()
				.ifPresent(p -> p.setWaterBalance(new WaterBalance(55)));

		house.nextDay();

		if (house.getPlants().stream().filter(p -> p.isDied()).count() == 3)
			logger.info("All plants is died!");

		house.digOutFromGreenHouse("Fialki");
	}
}
