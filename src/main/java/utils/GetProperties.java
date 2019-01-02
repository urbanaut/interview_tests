package utils;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class GetProperties {

    private static final String TEST_PROPERTIES_FILE = "src/main/resources/properties/test.properties";
    private static final String PRIVATE_PROPERTIES_FILE="src/main/resources/properties/private.properties";


    public static final String BASE_URL = getProperty("BASE_URL"); //https://phptravels.com/demo/

    public static final String CHROME_BINARY = getProperty("CHROME_BINARY");
    public static final String CHROME_DRIVER_PATH = getProperty("CHROME_DRIVER_PATH");
    public static final String FIREFOX_DRIVER_PATH = getProperty("FIREFOX_DRIVER_PATH");
    public static final String MS_DRIVER_PATH = getProperty("MS_DRIVER_PATH");
    public static final String IE_DRIVER_PATH = getProperty("IE_DRIVER_PATH");
    public static final String OPERA_BINARY = getProperty("OPERA_BINARY");
    public static final String OPERA_DRIVER_PATH = getProperty("OPERA_DRIVER_PATH");

    public static final String DB_DRIVER = getProperty("DB_DRIVER");
    public static final String DB_URL = getProperty("DB_URL");
    public static final String DB_USER = getProperty("DB_USER");
    public static final String DB_PASSWORD = getProperty("DB_PASSWORD");

    public static final String DEVICE_NAME = getProperty("DEVICE_NAME");
    public static final int DEVICE_WIDTH = getIntegerProperty("DEVICE_WIDTH");
    public static final int DEVICE_HEIGHT = getIntegerProperty("DEVICE_HEIGHT");
    public static final float DEVICE_PIXEL_RATIO = getFloatProperty("DEVICE_PIXEL_RATIO");


    private static String getProperty(String property) {
        Properties props = new Properties();
        try (InputStream input = new FileInputStream(TEST_PROPERTIES_FILE)) {
            props.load(input);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return props.getProperty(property);
    }

    private static String getPrivateProperty(String property) {
        Properties props = new Properties();
        try (InputStream input = new FileInputStream(PRIVATE_PROPERTIES_FILE)) {
            props.load(input);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return props.getProperty(property);
    }

    private static boolean getBooleanProperty(String property) {
        Properties props = new Properties();
        try (InputStream input = new FileInputStream(TEST_PROPERTIES_FILE)){
            props.load(input);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Boolean.valueOf(props.getProperty(property));
    }

    private static int getIntegerProperty(String property) {
        Properties props = new Properties();
        try (InputStream input = new FileInputStream(TEST_PROPERTIES_FILE)){
            props.load(input);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Integer.valueOf(props.getProperty(property));
    }

    private static float getFloatProperty(String property) {
        Properties props = new Properties();
        try (InputStream input = new FileInputStream(TEST_PROPERTIES_FILE)){
            props.load(input);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Float.valueOf(props.getProperty(property));
    }

}
