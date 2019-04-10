package com.techelevator.archive;

import java.io.IOException;

import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

import static twitter4j.TwitterMethod.UPDATE_STATUS;

public class NamexTweet {
    private final static String CONSUMER_KEY = "JcnbR2Hr6zlLE7t0lhJS2cPRA";
    private final static String CONSUMER_KEY_SECRET = "k8wmZL3iYKbMETlZYCG3jebmEsVMdbakNofqmyYqTwTq2pQJie";
    private final static String ACCESS_TOKEN_SECRET="lRnNxHbtHAEo8IAKx7qn4gbhwdtg52kpEReRvwLGfo6XI";
    private final static String ACCESS_TOKEN="1110976645807116288-RS7e0ymXf6Kxgmgc9P7zRedzco7a3x";
    private final static String PIN = "4501401";

    private static final Object LOCK = new Object();


    public void start() throws TwitterException, IOException, InterruptedException {
        ConfigurationBuilder twitterSobeKConfig = new ConfigurationBuilder();
        twitterSobeKConfig.setOAuthConsumerKey(CONSUMER_KEY)
                .setOAuthConsumerSecret(CONSUMER_KEY_SECRET)
                .setOAuthAccessToken(ACCESS_TOKEN)
                .setOAuthAccessTokenSecret(ACCESS_TOKEN_SECRET);

        TwitterFactory twitterFactory = new TwitterFactory(twitterSobeKConfig.build());
        Twitter twitter = twitterFactory.getInstance();

/*        System.out.println("Access Token: " + accessToken.getToken());
        System.out.println("Access Token Secret: "
                + accessToken.getTokenSecret());*/

        //twitter.updateStatus("AutoTweet with config builder!");

        Query testQuery = new Query("#shelovesmatlab");
        testQuery.setLang("en");
        testQuery.setSince("2017-01-01");
        testQuery.setUntil("2019-04-01");
//        testQuery.setCount(5);
        QueryResult queryResult = twitter.search(testQuery);

        int i = 0;
/*
        for (Status status : queryResult.getTweets()) {
            System.out.println((i++)+"@" + status.getUser().getScreenName() + ":" + status.getText());
        }
*/

    }


    public static void main(String[] args) throws Exception {
        new NamexTweet().start();// run the Twitter client


        ConfigurationBuilder twitterSobeASyncConfig = new ConfigurationBuilder();
        twitterSobeASyncConfig.setOAuthConsumerKey(CONSUMER_KEY)
                .setOAuthConsumerSecret(CONSUMER_KEY_SECRET)
                .setOAuthAccessToken(ACCESS_TOKEN)
                .setOAuthAccessTokenSecret(ACCESS_TOKEN_SECRET);
        AsyncTwitterFactory factory = new AsyncTwitterFactory(twitterSobeASyncConfig.build());
        AsyncTwitter twitterAsyn = factory.getInstance();
        twitterAsyn.addListener(new TwitterAdapter() {
            @Override
            public void updatedStatus(Status status) {
                System.out.println("Successfully updated the status to [" +
                        status.getText() + "].");
                synchronized (LOCK) {
                    LOCK.notify();
                }
            }

            @Override
            public void onException(TwitterException e, TwitterMethod method) {
                if (method == UPDATE_STATUS) {
                    e.printStackTrace();
                    synchronized (LOCK) {
                        LOCK.notify();
                    }
                } else {
                    synchronized (LOCK) {
                        LOCK.notify();
                    }
                    throw new AssertionError("Should not happen");
                }
            }
        });
        twitterAsyn.updateStatus("Hello 1");
        synchronized (LOCK) {
            LOCK.wait();
        }
    }
}