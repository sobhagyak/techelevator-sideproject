package com.techelevator.twitter;

// twitter reader returns a set of tweets

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import twitter4j.*;

public class TwitterReader {

    private static final Log LOG = LogFactory.getLog(TwitterReader.class);
    private static final String DEFAULT_QUERY = "ChaseBank";
    private static final String DEFAULT_LANGUAGE = "en";

    private TwitterStream twitterStream;
    private FilterQuery searchQuery;

    public TwitterReader() {
        CustomTwitterStreamFactory customTwitterStreamFactory = new CustomTwitterStreamFactory();
        twitterStream = customTwitterStreamFactory.newTwitterConfig().getInstance();
        searchQuery = new FilterQuery();
        //searchQuery.language(DEFAULT_LANGUAGE);
        searchQuery.track(DEFAULT_QUERY);
    }

    public TwitterReader(String customQuery) {
        CustomTwitterStreamFactory customTwitterStreamFactory = new CustomTwitterStreamFactory();
        twitterStream = customTwitterStreamFactory.newTwitterConfig().getInstance();
        searchQuery = new FilterQuery();
        searchQuery.language(DEFAULT_LANGUAGE);
        searchQuery.track(DEFAULT_QUERY);

    }

    public void startTwitterReader() {
        StatusListener statusListener = new StatusListener() {
            @Override
            public void onStatus(Status status) {
                System.out.println(status.getUser().getName() + " : " + status.getText());
            }

            @Override
            public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {

            }

            @Override
            public void onTrackLimitationNotice(int i) {

            }

            @Override
            public void onScrubGeo(long l, long l1) {

            }

            @Override
            public void onStallWarning(StallWarning stallWarning) {

            }

            @Override
            public void onException(Exception e) {

            }
        };

        twitterStream.addListener(statusListener);
        twitterStream.filter(searchQuery);
    }

    public static void main(String[] args) {
        TwitterReader twitterReader = new TwitterReader();
        twitterReader.startTwitterReader();
    }
}
