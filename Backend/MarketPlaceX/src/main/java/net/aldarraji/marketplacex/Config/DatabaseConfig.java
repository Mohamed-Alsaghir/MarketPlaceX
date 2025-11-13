package net.aldarraji.marketplacex.Config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DatabaseConfig {

    @Value("${postgres.url}")
    private String postgresURL;

    @Value("${postgres.user}")
    private String postgresUser;

    @Value("${postgres.password}")
    private String postgresPassword;

    private static DatabaseConfig instance;

    public static DatabaseConfig getInstance() {
        //singleton design pattern
        if (instance == null) {
            instance = new DatabaseConfig();
        }
        return instance;
    }


    // Fråga läraren om detta är nödvändigt
    /*
    public static getInstance() {
        //singleton design pattern
        if (instance == null) {
            instance = new DatabaseConfig();
        } else {
            return instance;
        }
    }
    */

    public String getPostgresURL() {
        return postgresURL;
    }

    public String getPostgresUser() {
        return postgresUser;
    }

    public String getPostgresPassword() {
        return postgresPassword;
    }
}

