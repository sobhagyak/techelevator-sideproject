package com.techelevator.archive;

import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

import static twitter4j.TwitterMethod.UPDATE_STATUS;

/**
 * <p>This is a code example of Twitter4J async API.<br>
 * Usage: java twitter4j.examples.AsyncUpdate <i>TwitterID</i> <i>TwitterPassword</i> <i>text</i><br>
 * </p>
 *
 * @author Yusuke Yamamoto - yusuke at mac.com
 */
public final class AsyncUpdate {
    /**
     * Main entry for this application.
     */
    private static final Object LOCK = new Object();
    private final static String CONSUMER_KEY = "JcnbR2Hr6zlLE7t0lhJS2cPRA";
    private final static String CONSUMER_KEY_SECRET = "k8wmZL3iYKbMETlZYCG3jebmEsVMdbakNofqmyYqTwTq2pQJie";
    private final static String ACCESS_TOKEN_SECRET="lRnNxHbtHAEo8IAKx7qn4gbhwdtg52kpEReRvwLGfo6XI";
    private final static String ACCESS_TOKEN="1110976645807116288-RS7e0ymXf6Kxgmgc9P7zRedzco7a3x";

    public static void main(String[] args) throws InterruptedException {
        /*if (args.length < 1) {
            System.out.println("Usage: java twitter4j.examples.AsyncUpdate text");
            System.exit(-1);
        }*/

        ConfigurationBuilder twitterSobeASyncConfig = new ConfigurationBuilder();
        twitterSobeASyncConfig.setOAuthConsumerKey(CONSUMER_KEY)
                .setOAuthConsumerSecret(CONSUMER_KEY_SECRET)
                .setOAuthAccessToken(ACCESS_TOKEN)
                .setOAuthAccessTokenSecret(ACCESS_TOKEN_SECRET);

        AsyncTwitterFactory factory = new AsyncTwitterFactory(twitterSobeASyncConfig.build());
        AsyncTwitter twitter = factory.getInstance();

        twitter.addListener(new TwitterAdapter() {
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
        twitter.updateStatus("Hola");
        synchronized (LOCK) {
            LOCK.wait();
        }
    }

}