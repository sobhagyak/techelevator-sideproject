package com.techelevator.twitter;

import twitter4j.TwitterFactory;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.ConfigurationBuilder;

public class CustomTwitterStreamFactory {
    private final static String CONSUMER_KEY = "JcnbR2Hr6zlLE7t0lhJS2cPRA";
    private final static String CONSUMER_KEY_SECRET = "k8wmZL3iYKbMETlZYCG3jebmEsVMdbakNofqmyYqTwTq2pQJie";
    private final static String ACCESS_TOKEN_SECRET="lRnNxHbtHAEo8IAKx7qn4gbhwdtg52kpEReRvwLGfo6XI";
    private final static String ACCESS_TOKEN="1110976645807116288-RS7e0ymXf6Kxgmgc9P7zRedzco7a3x";

    public TwitterStreamFactory newTwitterConfig() {
        ConfigurationBuilder twitterConfig = new ConfigurationBuilder();

        twitterConfig.setOAuthConsumerKey(CONSUMER_KEY)
                .setOAuthConsumerSecret(CONSUMER_KEY_SECRET)
                .setOAuthAccessToken(ACCESS_TOKEN)
                .setOAuthAccessTokenSecret(ACCESS_TOKEN_SECRET);

        TwitterStreamFactory twitterFactory = new TwitterStreamFactory(twitterConfig.build());

        return twitterFactory;
    }
}
