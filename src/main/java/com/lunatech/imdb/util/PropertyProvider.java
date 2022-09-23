package com.lunatech.imdb.util;

import com.lunatech.imdb.Global;
import lombok.extern.log4j.Log4j2;

import javax.ejb.Singleton;
import javax.ejb.Startup;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

@Startup
@Singleton
@Log4j2
public class PropertyProvider {

    private Properties getProperties() {
        Properties properties = new Properties();
        String path = Global.getPath();

        if (path != null) {
            log.info("Loading Configuration file");
            try (InputStream inputStream = Files.newInputStream(Paths.get(path))) {
                properties.load(inputStream);
            } catch (IOException ex) {
                log.error("Error loading configuration file", ex);
            }
        } else {
            log.error("Failed to load configuration file");
        }
        return properties;
    }

    public String getProperty(String key) {
        return getProperties().getProperty(key);
    }
}
