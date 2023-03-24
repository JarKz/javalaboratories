package jarkz.lab4.entities;

import java.util.Set;

import jarkz.lab4.types.LocalityType;

public class State {

    private String name;
    private long population;
    private long area;
    private Locality capital;
    private Set<Region> regions;

    public State(
        String name,
        Set<Region> regions
        ){
        setName(name);
        setRegions(regions);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null)
            throw new NullPointerException("State name must be not null.");
        else if (name.equals(""))
            throw new IllegalArgumentException("State name must be not empty.");
        this.name = name;
    }

    public long getPopulation() {
        return population;
    }

    public long computePopulation() {
        population = regions.stream().mapToLong(r -> {
                r.computePopulation();
                return r.getPopulation();
            }).sum();
        return population;
    }

    public long getArea() {
        return area;
    }

    public long computeArea() {
        this.area = regions.stream().mapToLong(r -> {
                r.computeArea();
                return r.getArea();
            }).sum();
        return area;
    }

    public Locality getCapital() {
        return capital;
    }

    public void setCapital(Locality capital) {
        if (!regions.stream().anyMatch(r -> {
                Locality regionalCenter = r.getRegionCenter();
                return regionalCenter.getName().equals(capital.getName())
                    && regionalCenter.getPopulation() == capital.getPopulation();
            }))
            throw new IllegalArgumentException("Not found region center for increase up to capital.");
        else if (regions.stream().filter(r -> r.getRegionCenter().equals(capital)).count() > 1)
            throw new IllegalStateException("In regions, inputed capital equals found region centers more than 1 times.");

        this.capital.setType(LocalityType.REGIONAL_CENTER);

        if (LocalityType.CAPITAL != capital.getType())
            capital.setType(LocalityType.CAPITAL);
        this.capital = capital;
    }

    public Set<Region> getRegions() {
        return regions;
    }

    public void setRegions(Set<Region> regions) {
        if (regions == null)
            throw new NullPointerException("Set of districts must not be null.");
        this.regions = regions;

        for (Region region : regions){
            if (LocalityType.CAPITAL == region.getRegionCenter().getType()){
                this.capital = region.getRegionCenter();
                break;
            }
        }

        if (this.capital == null)
            throw new IllegalArgumentException("In regions not found capital locality.");
        else if (regions.stream().filter(r -> LocalityType.CAPITAL == r.getRegionCenter().getType()).count() > 1)
            throw new IllegalArgumentException("In regions found capital more than 1 times.");

        computePopulation();
        computeArea();
    }

    public void addRegion(Region region){
        if (region == null)
            throw new NullPointerException("Region must be not null.");
        if (LocalityType.CAPITAL == region.getRegionCenter().getType())
            throw new IllegalArgumentException("Region center must be not capital. Intead this method use setCapital().");

        regions.add(region);

        computePopulation();
        computeArea();
    }

    public void removeRegion(Region region){
        if (region == null)
            throw new NullPointerException("Region must be not null.");
        if (LocalityType.CAPITAL == region.getRegionCenter().getType())
            throw new IllegalArgumentException("Region center must be not capital.");

        regions.remove(region);

        computePopulation();
        computeArea();

    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + name.hashCode();
        result = prime * result + regions.hashCode();
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || !(obj instanceof State s))
            return false;
        return name.equals(s.getName())
            && regions.equals(s.getRegions());
    }

    @Override
    public String toString() {
        return "State[name=" + name + ", population=" + population + ", area=" + area + ", capital=" + capital
                + ", regions=" + regions + "]";
    }
}
