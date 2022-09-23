package com.lunatech.imdb;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class Global {
    public static final String PERSISTENT_NAME = "IMDBPu";

    public static final String AUTH_KEY = "auth_key";

    public static String getPath() {
        return "/Users/<<username>>/Desktop/Configs/api_config.properties";
    }

}
