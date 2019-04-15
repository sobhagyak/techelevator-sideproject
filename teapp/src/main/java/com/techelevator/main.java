package com.techelevator;

import com.techelevator.archive.KinesisConsumer;
import com.techelevator.twitter.TwitterStatusListener;

public class main {
    public static void main(String[] args) {
        TwitterToKinesisController twitterToKinesisController = new TwitterToKinesisController();
        twitterToKinesisController.setUp("KINESIS");
        twitterToKinesisController.startPublishing();


    }
}
