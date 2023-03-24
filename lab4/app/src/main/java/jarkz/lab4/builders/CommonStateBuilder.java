package jarkz.lab4.builders;

import java.util.HashSet;
import java.util.Set;

import jarkz.lab4.entities.District;
import jarkz.lab4.entities.Locality;
import jarkz.lab4.entities.Region;
import jarkz.lab4.entities.State;
import jarkz.lab4.types.LocalityType;

public class CommonStateBuilder {

    private DistrictBuilder districtBuilder;
    private RegionBuilder regionBuilder;
    private StateBuilder stateBuilder;

    private CommonStateBuilder(){}

    public static LocalityBuilder newAscendingBuilder(){
        CommonStateBuilder commonBuilder = new CommonStateBuilder();
        return commonBuilder.new LocalityBuilder();
    }
    public static StateBuilder newDescendingBuilder(){
        CommonStateBuilder commonBuilder = new CommonStateBuilder();
        return commonBuilder.new StateBuilder();
    }

    public class LocalityBuilder{

        private Set<Locality> localities = new HashSet<>();

        public LocalityBuilder newLocality(String name, LocalityType type, long population){
            localities.add(new Locality(name, population, type, false));
            return this;
        }

        public DistrictBuilder groupToDistrict(String districtName, long area){
            if (districtBuilder == null)
                districtBuilder = new DistrictBuilder();
            districtBuilder.group(districtName, area, localities);
            return districtBuilder;
        }

        public Set<Locality> build(){
            return localities;
        }
    }
    
    public class DistrictBuilder {

        private Set<District> districts = new HashSet<>();

        private DistrictBuilder group(String name, long area, Set<Locality> localities){
            districts.add(new District(name, area, localities));
            return this;
        }

        public LocalityBuilder newDistrict(){
            return new LocalityBuilder();
        }

        public RegionBuilder groupToRegion(String name){
            if (regionBuilder == null)
                regionBuilder = new RegionBuilder();
            regionBuilder.group(name, districts);
            districtBuilder = null;
            return regionBuilder;
        }

        public Set<District> build(){
            return districts;
        }
    }

    public class RegionBuilder {

        private Set<Region> regions = new HashSet<>();

        private RegionBuilder group(String name, Set<District> districts){
            regions.add(new Region(name, districts));
            return this;
        }

        public DistrictBuilder newRegion(){
            return new DistrictBuilder();
        }

        public StateBuilder groupToState(String name){
            if (stateBuilder == null)
                stateBuilder = new StateBuilder();
            stateBuilder.group(name, regions);
            regionBuilder = null;
            return stateBuilder;
        }

        public Set<Region> build(){
            return regions;
        }
    }

    public class StateBuilder {

        private State state;

        public StateBuilder(){
            stateBuilder = this;
        }

        private StateBuilder group(String name, Set<Region> regions){
            state = new State(name, regions);
            return this;
        }

        public RegionBuilder newState(){
            return new RegionBuilder();
        }

        public State build(){
            return state;
        }
    }
}
