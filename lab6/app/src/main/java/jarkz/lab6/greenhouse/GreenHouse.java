package jarkz.lab6.greenhouse;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import jarkz.lab6.plants.BushPlant;
import jarkz.lab6.plants.FlowerPlant;
import jarkz.lab6.plants.Plant;
import jarkz.lab6.plants.PlantType;
import jarkz.lab6.plants.RoomPlant;
import jarkz.lab6.plants.Specie;
import jarkz.lab6.types.Agrofactors;
import jarkz.lab6.types.ClimatZone;
import jarkz.lab6.types.Soil;

public class GreenHouse {

	private int totalPlaces;
	private List<Pot> places = new ArrayList<>();
	private Set<Specie> availableSpecies;

	public GreenHouse(int totalPlaces) {
		this.totalPlaces = totalPlaces;
		availableSpecies = new HashSet<>();
	}

	public void buyNewSpecie(Specie newSpecie) {
		if (newSpecie == null)
			throw new NullPointerException("New specie must be not null.");
		if (availableSpecies.stream().anyMatch(as -> as.getName().equals(newSpecie.getName())))
			throw new IllegalArgumentException("Name of new specie must be unique.");
		availableSpecies.add(newSpecie);
	}

	public void throwToTrashSpecie(Specie specie) {
		availableSpecies.remove(specie);
	}

	public void plantNewPlant(
			String name,
			String specieName,
			Soil soil,
			Agrofactors agrofactors) {
		if (soil == null)
			throw new NullPointerException("Soil instance must be not null.");
		else if (agrofactors == null)
			throw new NullPointerException("Agrofactors instance must be not null.");
		Plant newPlant = createPlant(name, specieName);
		Pot pot;
		Stream<Pot> placesStream = places.stream().filter(p -> p.isEmpty());
		if (placesStream.count() > 0)
			pot = placesStream.findFirst().orElse(null);
		// we don't need null check because important check are above
		else if (places.size() <= totalPlaces)
			pot = new Pot(newPlant, soil, agrofactors.getTemperature(), agrofactors.getLight(),
					agrofactors.getWaterBalance());
		else
			throw new IllegalStateException("Greenhouse is full.");
		places.add(pot);
	}

	private Plant createPlant(String name, String specieName) {
		Plant newPlant;
		Specie specie = checkAndGetSpecie(specieName);
		PlantType type = specie.getType();
		switch (type) {
			case BUSH -> newPlant = new BushPlant(name, specie);
			case ROOM -> newPlant = new RoomPlant(name, specie);
			case FLOWER -> newPlant = new FlowerPlant(name, specie);
			default -> throw new IllegalArgumentException("Unknown plant type.");
		}
		return newPlant;
	}

	public List<Plant> getPlants() {
		return places.stream().map(p -> copyInformationAboutPant(p.getPlant())).collect(Collectors.toList());
	}

	public List<Pot> getBusyPots() {
		return places.stream().filter(p -> !p.isEmpty()).toList();
	}

	public List<Plant> getPlantWithSpecificSpecie(String specieName) {
		Specie specie = checkAndGetSpecie(specieName);
		return places.stream()
				.filter(p -> p.getPlant().getSpecie().equals(specie))
				.map(p -> copyInformationAboutPant(p.getPlant()))
				.collect(Collectors.toList());
	}

	public List<Plant> getPlatnWithSpecificZone(ClimatZone zone) {
		return places.stream()
				.map(p -> p.getPlant())
				.filter(p -> p.getSpecie().getClimatZone() == zone)
				.collect(Collectors.toList());
	}

	private Specie checkAndGetSpecie(String specieName) {
		Specie specie = availableSpecies.stream()
				.filter(s -> s.getName().equals(specieName))
				.findFirst()
				.orElse(null);
		if (specie == null)
			throw new IllegalArgumentException("Entered specie name doesn't contains.");
		return specie;
	}

	private Plant copyInformationAboutPant(Plant plant) {
		Plant newPlant;
		PlantType type = plant.getSpecie().getType();
		switch (type) {
			case BUSH -> newPlant = new BushPlant(plant);
			case ROOM -> newPlant = new RoomPlant(plant);
			case FLOWER -> newPlant = new FlowerPlant(plant);
			default -> throw new IllegalArgumentException("Unknownn plant type.");
		}
		return newPlant;
	}

	public void digOutFromGreenHouse(String specieName) {
		if (specieName == null)
			throw new NullPointerException("Specie name must be not null.");
		else if (specieName.isEmpty() || specieName.isBlank())
			throw new IllegalArgumentException("Specie name must be not empty or blank.");

		Specie specie = availableSpecies.stream()
				.filter(as -> as.getName().equals(specieName))
				.findAny()
				.orElseThrow(() -> new IllegalArgumentException("Name not found!!"));
		if (availableSpecies.remove(specie)) {
			places.forEach(p -> {
				if (p.getPlant().getSpecie().equals(specie)) {
					p.digOutPlant();
				}
			});
		}
	}

	public void nextDay() {
		places.forEach(p -> {
			p.getPlant().growUp(p.getTemperature(), p.getLight(), p.getWaterBalance(), p.getSoil());
		});
	}
}
