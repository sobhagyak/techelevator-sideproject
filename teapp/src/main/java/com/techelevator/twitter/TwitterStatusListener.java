package com.techelevator.twitter;

import com.techelevator.dao.KinesisMessagePersister;
import com.techelevator.dao.MessagePersister;
import com.techelevator.model.PublishedMessage;
import twitter4j.*;

public class TwitterStatusListener implements StatusListener {

    private static final String RESOURCE = "TWITTER";
/*
    private MessagePersister messagePersister;

    public TwitterStatusListener(MessagePersister messagePersister) {
        this.messagePersister = messagePersister;
    }
*/

    @Override
    public void onStatus(Status status) {
        System.out.println(status.getUser().getScreenName()+"  "+status.getUser().getName() + " : " + status.getText());

        String hashtags = convertHashEntities(status.getHashtagEntities());

        PublishedMessage message = new PublishedMessage(
                RESOURCE, status.getText(), status.getId(), status.getUser().getScreenName(),
                hashtags, status.getCreatedAt(), status.getFavoriteCount(), status.getRetweetCount());

        System.out.println(message.toString());
        //messagePersister.persist(message);
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

    public String convertHashEntities(HashtagEntity[] hashtagEntities) {
        String hashtags = null;
        if(hashtagEntities.length > 0) {
            hashtags = hashtagEntities[0].getText();
            for (int i = 1; i < hashtagEntities.length; i++) {
                hashtags = hashtags + "," + hashtagEntities[i].getText();
            }
        }
        return hashtags;
    }
}
