package com.ilupper.util;

import org.apache.log4j.PropertyConfigurator;

public class LogInitializer {

	static void initializeLogger() {
		PropertyConfigurator.configure("src/main/resources/log4j.properties");
	}
}
