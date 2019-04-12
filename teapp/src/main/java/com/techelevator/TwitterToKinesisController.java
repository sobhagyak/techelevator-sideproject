package com.techelevator;

import com.techelevator.dao.KinesisMessagePersister;
import com.techelevator.dao.MessagePersister;
import com.techelevator.twitter.TwitterReader;
import com.techelevator.twitter.TwitterStatusListener;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import twitter4j.TwitterFactory;

// Controller to read from twitter and publish to Kinesis
// TwitterReader reads a set of tweets from twitter and calls the Kinesis message persister function to
// persist relevant data from the tweets on kinesis
// Twitter reader should run as as service/microservice
public class TwitterToKinesisController {

    private static final String[] SUPPORTED_STREAMING_SERVICES = {"KINESIS"};

    private TwitterReader reader;
    private MessagePersister persister;
    private TwitterStatusListener twitterStatusListener;

    private static final Log LOG = LogFactory.getLog(TwitterToKinesisController.class);
    private static final String STREAM_NAME = "test-sk";

    public void setUp(String streamType) {
        reader = new TwitterReader();

        if (validatePersisterType(streamType)) {
            persister = createMessagePersister(streamType);

        } else {
            LOG.fatal("Incorrect Streaming Service provided");
            System.exit(-1);
        }

        twitterStatusListener = new TwitterStatusListener(persister);
    }

    public void startPublishing() {
        reader.startTwitterReader(twitterStatusListener);
    }

    private MessagePersister createMessagePersister(String persisterType) {
        if (persisterType.equals("KINESIS")) {
            MessagePersister persister = new KinesisMessagePersister();
            persister.initialize(STREAM_NAME);
            return persister;
        }
        return null;
    }

    public boolean validatePersisterType(String persisterType) {
        for (String persister : SUPPORTED_STREAMING_SERVICES) {
            if (persister.equals(persisterType)) {
                return true;
            }
        }
        return false;
    }
}
