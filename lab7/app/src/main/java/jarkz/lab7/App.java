package jarkz.lab7;

import java.util.logging.Logger;

public class App {

	public static void main(String[] args) {
		int enteredNumber = 0;
		try {
			String firstArg = args[0];
			enteredNumber = Integer.parseInt(firstArg);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Predicate function = (number -> number % 13 == 0);

		// int validNumber = 726_113;
		// int invalidNumber = 234_324;
		//
		Logger logger = Logger.getLogger("LOGGER");
		// logger.info("Validation of valid number " + validNumber + "\nResult: " +
		// function.apply(validNumber));
		// logger.info("Validation of invalid number " + invalidNumber + "\nResult: " +
		// function.apply(invalidNumber));

		logger.info("Your number validation: " + enteredNumber + " \nResult: " + function.apply(enteredNumber));
	}
}
