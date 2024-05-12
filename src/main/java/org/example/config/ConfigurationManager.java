package org.example.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigurationManager {
    private static final String CONFIG_FILE = "/config.properties";

    private ConfigurationManager() {

    }

    public static String getBTCUSDTApiEndpoint() {
        return getProperty("btcusdt.endpoint.url");
    }

    public static String getETHUSDTApiEndpoint() {
        return getProperty("ethusdt.endpoint.url");
    }

    public static Long getVolumeChangeCalculationInterval() {
        String interval = getProperty("volume.change.calculation.interval.in.seconds");
        if(interval == null) {
            return 0L;
        }
        return Long.valueOf(interval);
    }

    private static String getProperty(String key) {
        Properties properties = new Properties();
        try (InputStream inputStream = ConfigurationManager.class.getResourceAsStream(CONFIG_FILE)) {
            properties.load(inputStream);
            return properties.getProperty(key);
        } catch (IOException e) {
            System.err.println("Failed to load config: "+e.getMessage());
            return null;
        }
    }
}

