package jarkz.lab4.entities;

import java.util.Set;

import jarkz.lab4.types.LocalityType;

public class District {

    private String name;
    private long population;
    private long area;
    private Locality districtCenter;
    private Set<Locality> localities;

    public District(
        String name,
        long area,
        Set<Locality> localities
        ){
        setName(name);
        setArea(area);
        setLocalities(localities);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null)
            throw new NullPointerException("District name must be not null.");
        else if (name.equals(""))
            throw new IllegalArgumentException("District name must be not empty.");
        this.name = name;
    }

    public long getPopulation() {
        return population;
    }

    public long computePopulation(){
        population = localities.stream().mapToLong(l -> l.getPopulation()).sum();
        return population;
    }

    public long getArea() {
        return area;
    }

    public void setArea(long area) {
        if (area <= 0)
            throw new IllegalArgumentException("Area must be greater than zero.");
        this.area = area;
    }

    public Locality getDistrictCenter() {
        return districtCenter;
    }

    public void setDistrictCenter(Locality districtCenter) {
        this.districtCenter.setType(LocalityType.CITY);

        Set<LocalityType> centerType = Set.of(
            LocalityType.DISTRICT_CENTER,
            LocalityType.REGIONAL_CENTER,
            LocalityType.CAPITAL);
        if (!centerType.contains(districtCenter.getType()))
            districtCenter.setType(LocalityType.DISTRICT_CENTER);
        this.districtCenter = districtCenter;

        if (!localities.contains(districtCenter)){
            localities.add(districtCenter);
            this.population += districtCenter.getPopulation();
        }
    }

    public Set<Locality> getLocalities() {
        return localities;
    }

    public void setLocalities(Set<Locality> localities) {
        if (localities == null)
            throw new NullPointerException("Set of localities must not be null.");
        this.localities = localities;

        Set<LocalityType> centerType = Set.of(
            LocalityType.DISTRICT_CENTER,
            LocalityType.REGIONAL_CENTER,
            LocalityType.CAPITAL);
        for (Locality locality : localities){
            if (centerType.contains(locality.getType())){
                this.districtCenter = locality;
                break;
            }
        }
        if (this.districtCenter == null)
            throw new IllegalArgumentException("In localities not found district center city.");
        else if (localities.stream().filter(l -> centerType.contains(l.getType())).count() > 1)
            throw new IllegalArgumentException("In localities found districe centrer cities more than 1.");

        computePopulation();
    }

    public void addLocality(Locality locality){
        if (locality == null)
            throw new NullPointerException("Locality must be not null.");
        Set<LocalityType> centerType = Set.of(
            LocalityType.DISTRICT_CENTER,
            LocalityType.REGIONAL_CENTER,
            LocalityType.CAPITAL);
        if (centerType.contains(locality.getType()))
            throw new IllegalArgumentException("Locality must not be center of District/Region or capital. For this use setDistrictCenter() method.");
        localities.add(locality);

        computePopulation();
    }

    public void removeLocality(Locality locality){
        if (locality == null)
            throw new NullPointerException("Locality must be not null.");
        Set<LocalityType> centerType = Set.of(
            LocalityType.DISTRICT_CENTER,
            LocalityType.REGIONAL_CENTER,
            LocalityType.CAPITAL);
        if (centerType.contains(locality.getType()))
            throw new IllegalArgumentException("Locality must not be center of District/Region or capital.");
        localities.remove(locality);

        computePopulation();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + name.hashCode();
        result = prime * result + Long.hashCode(area);
        result = prime * result + localities.hashCode();
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || !(obj instanceof District d))
            return false;
        return name.equals(d.getName())
            && area == d.getArea()
            && localities.equals(d.localities);
    }

    @Override
    public String toString() {
        return "District[name=" + name + ", population=" + population + ", area=" + area + ", districtCenter="
                + districtCenter + ", localities=" + localities + "]";
    }
}