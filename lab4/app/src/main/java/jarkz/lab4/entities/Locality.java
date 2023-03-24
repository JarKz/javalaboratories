package jarkz.lab4.entities;

import jarkz.lab4.types.LocalityType;

public class Locality {

    private String name;
    private long population;
    private LocalityType type;

    public Locality(
        String name,
        long population,
        LocalityType type,
        boolean isCaptiale
        ){
        if (name == null)
            throw new NullPointerException("Locality name must be not null.");
        else if (name.equals(""))
            throw new IllegalArgumentException("Locality name must be not empty.");
        if (type == null)
            throw new NullPointerException("Locality type must be not null.");
        this.name = name;
        this.population = population;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null)
            throw new NullPointerException("Locality name must be not null.");
        else if (name.equals(""))
            throw new IllegalArgumentException("Locality name must be not empty.");
        this.name = name;
    }

    public long getPopulation() {
        return population;
    }

    public void setPopulation(long population) {
        this.population = population;
    }

    public LocalityType getType() {
        return type;
    }

    public void setType(LocalityType type) {
        if (name == null)
            throw new NullPointerException("Locality type must be not null.");
        this.type = type;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + name.hashCode();
        result = prime * result + Long.hashCode(population);
        result = prime * result + type.hashCode();
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || !(obj instanceof Locality c))
            return false;
        return name.equals(c.getName())
            && population == c.getPopulation()
            && type == c.getType();
    }

    @Override
    public String toString() {
        return "Locality[name=" + name + ", population=" + population + ", type=" + type + "]";
    }
}