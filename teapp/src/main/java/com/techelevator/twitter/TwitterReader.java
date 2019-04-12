package com.techelevator.twitter;

// twitter reader returns a set of tweets

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import twitter4j.*;

public class TwitterReader {

    private static final Log LOG = LogFactory.getLog(TwitterReader.class);
    private static final String DEFAULT_QUERY = "SobeTestingStuff";
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

    public void startTwitterReader(StatusListener statusListener) {
        twitterStream.addListener(statusListener);
        twitterStream.filter(searchQuery);
    }

}
