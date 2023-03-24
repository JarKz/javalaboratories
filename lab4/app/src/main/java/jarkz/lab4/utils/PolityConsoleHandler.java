package jarkz.lab4.utils;

import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import jarkz.lab4.entities.Locality;
import jarkz.lab4.entities.State;

public class PolityConsoleHandler {

    private final State state;
    private final Logger logger;

    public PolityConsoleHandler(State state, Logger logger){
        this.state = state;
        this.logger = logger;
    }

    public void showCapital(){
        Locality capital = state.getCapital();
        logger.info("State capital – " + capital.toString());
    }

    public void showTotalRegions(){
        int totalRegions = state.getRegions().size();
        logger.info("Total regions equals " + totalRegions);
    }

    public void showAreaSize(){
        long areaSize = state.getArea();
        logger.info("Area size equals " + areaSize + " km^2");
    }

    public void showRegionCenters(){
        Set<Locality> regionCenters = state.getRegions()
            .stream()
            .map(r -> r.getRegionCenter())
            .collect(Collectors.toSet());
        logger.info("Region centers are " + regionCenters.toString());
    }
}
