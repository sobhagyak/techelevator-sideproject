package com.techelevator.twitter;

import twitter4j.TwitterStreamFactory;
import twitter4j.conf.ConfigurationBuilder;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class CustomTwitterStreamFactory {
    private static String CONSUMER_KEY;
    private static String CONSUMER_KEY_SECRET;
    private static String ACCESS_TOKEN_SECRET;
    private static String ACCESS_TOKEN;

    public TwitterStreamFactory newTwitterConfig() {
        loadProperties();
        ConfigurationBuilder twitterConfig = new ConfigurationBuilder();

        twitterConfig.setOAuthConsumerKey(CONSUMER_KEY)
                .setOAuthConsumerSecret(CONSUMER_KEY_SECRET)
                .setOAuthAccessToken(ACCESS_TOKEN)
                .setOAuthAccessTokenSecret(ACCESS_TOKEN_SECRET);

        TwitterStreamFactory twitterFactory = new TwitterStreamFactory(twitterConfig.build());

        return twitterFactory;
    }

    private void loadProperties() {
        Properties prop = new Properties();
        InputStream input = null;

        try {
            input = new FileInputStream("teapp\\src\\main\\resources\\twitterConfig.properties");
            prop.load(input);

            ACCESS_TOKEN=prop.getProperty("ACCESS_TOKEN");
            ACCESS_TOKEN_SECRET=prop.getProperty("ACCESS_TOKEN_SECRET");
            CONSUMER_KEY=prop.getProperty("CONSUMER_KEY");
            CONSUMER_KEY_SECRET=prop.getProperty("CONSUMER_KEY_SECRET");

        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
