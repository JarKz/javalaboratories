package jarkz.lab4.entities;

import java.util.Set;

import jarkz.lab4.types.LocalityType;

public class Region {

    private String name;
    private long population;
    private long area;
    private Locality regionCenter;
    private Set<District> districts;

    public Region(
        String name,
        Set<District> districts
        ){
        setName(name);
        setDistricts(districts);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null)
            throw new NullPointerException("Region name must be not null.");
        else if (name.equals(""))
            throw new IllegalArgumentException("Region name must be not empty.");
        this.name = name;
    }

    public long getPopulation() {
        return population;
    }

    public long computePopulation(){
        population = districts.stream().mapToLong(d -> {
                d.computePopulation();
                return d.getPopulation();
            }).sum();
        return population;
    }

    public long getArea() {
        return area;
    }

    public long computeArea() {
        this.area = districts.stream().mapToLong(d -> d.getArea()).sum();
        return area;
    }

    public Locality getRegionCenter() {
        return regionCenter;
    }

    public void setRegionCenter(Locality regionCenter) {
        if (!districts.stream().anyMatch(d -> {
                Locality districtCenter = d.getDistrictCenter();
                return districtCenter.getName().equals(regionCenter.getName())
                    && districtCenter.getPopulation() == regionCenter.getPopulation();
            }))
            throw new IllegalArgumentException("Not found district center for increase up to region center.");
        else if (districts.stream().filter(d -> d.getDistrictCenter().equals(regionCenter)).count() > 1)
            throw new IllegalStateException("In districts, inputed region center equals found district centers more than 1 times.");

        this.regionCenter.setType(LocalityType.DISTRICT_CENTER);

        Set<LocalityType> centerType = Set.of(
            LocalityType.REGIONAL_CENTER,
            LocalityType.CAPITAL);
        if (!centerType.contains(regionCenter.getType()))
            regionCenter.setType(LocalityType.REGIONAL_CENTER);
        this.regionCenter = regionCenter;
    }

    public Set<District> getDistricts() {
        return districts;
    }

    public void setDistricts(Set<District> districts) {
        if (districts == null)
            throw new NullPointerException("Set of districts must not be null.");
        this.districts = districts;

        Set<LocalityType> centerType = Set.of(
            LocalityType.REGIONAL_CENTER,
            LocalityType.CAPITAL);
        for (District district : districts){
            if (centerType.contains(district.getDistrictCenter().getType())){
                this.regionCenter = district.getDistrictCenter();
                break;
            }
        }

        if (this.regionCenter == null)
            throw new IllegalArgumentException("In districts not found region center locality.");
        else if (districts.stream().filter(d -> centerType.contains(d.getDistrictCenter().getType())).count() > 1)
            throw new IllegalArgumentException("In districts found region center more than 1 times.");

        computePopulation();
        computeArea();
    }

    public void addDistrict(District district){
        if (district == null)
            throw new NullPointerException("District must be not null.");
        Set<LocalityType> centerType = Set.of(
            LocalityType.REGIONAL_CENTER,
            LocalityType.CAPITAL);
        if (centerType.contains(district.getDistrictCenter().getType()))
            throw new IllegalArgumentException("District center must be not center of Region or capital. For replacing use method setRegionCenter().");

        districts.add(district);

        computePopulation();
        computeArea();
    }

    public void removeDistrict(District district){
        if (district == null)
            throw new NullPointerException("District must be not null.");
        Set<LocalityType> centerType = Set.of(
            LocalityType.REGIONAL_CENTER,
            LocalityType.CAPITAL);
        if (centerType.contains(district.getDistrictCenter().getType()))
            throw new IllegalArgumentException("District center must be not center of Region or capital.");

        districts.remove(district);

        computePopulation();
        computeArea();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + name.hashCode();
        result = prime * result + districts.hashCode();
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || !(obj instanceof Region r))
            return false;
        return name.equals(r.getName())
            && districts.equals(r.getDistricts());
    }

    @Override
    public String toString() {
        return "Region[name=" + name + ", population=" + population + ", area=" + area + ", regionCenter="
                + regionCenter + ", districts=" + districts + "]";
    }
}
