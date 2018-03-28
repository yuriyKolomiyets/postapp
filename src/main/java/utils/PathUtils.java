package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PathUtils {

    private static Properties appProperties = new Properties();
    private static String pathToProps = "src/main/java/app.properties";


    public static String getUsersDbPath() {

        File file = new File("app.properties");

        try (InputStream io = new FileInputStream(pathToProps)) {
            appProperties.load(io);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }

        return appProperties.getProperty("pathToUsersJSON");
    }

    public static String getOrdersDbPath() {

        try (InputStream io = new FileInputStream(pathToProps)) {
            appProperties.load(io);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }

        return appProperties.getProperty("pathToOrdersJSON");
    }
}
