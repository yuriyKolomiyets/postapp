package utils;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class Log4JApp {

    public static Logger getLogger(Class classForLogging) {
        return Logger.getLogger(classForLogging);
    }
    public static void configLogger(String pathToProperties) {
        PropertyConfigurator.configure(pathToProperties);
    }

}