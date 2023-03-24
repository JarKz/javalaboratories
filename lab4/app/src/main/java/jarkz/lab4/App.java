/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package jarkz.lab4;

import java.util.logging.Logger;

import jarkz.lab4.entities.State;
import jarkz.lab4.utils.PolityConsoleHandler;
import jarkz.lab4.utils.Preparer;

public class App {

    public static void main(String[] args) {
        State state = Preparer.prepareBelarussianState();
        Logger logger = Logger.getLogger("STATE LOGGING");
        PolityConsoleHandler consoleHandler = new PolityConsoleHandler(state, logger);

        consoleHandler.showCapital();

        consoleHandler.showTotalRegions();

        consoleHandler.showAreaSize();

        consoleHandler.showRegionCenters();
    }
}