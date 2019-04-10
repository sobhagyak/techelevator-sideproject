package com.techelevator.model;

import java.sql.Timestamp;

public class PublishedMessage {

    private String resource;
    private String message;
    private String messsageId;
    private String username; // user screen name in twitter
    private String hashtag;
    private Timestamp postDate;
    private int favoriteCount;
    private int retweetCount;

    public PublishedMessage(String resource, String message, String messsageId, String username,
                            String hashtag, Timestamp postDate, int favoriteCount, int retweetCount) {
        this.resource = resource;
        this.message = message;
        this.messsageId = messsageId;
        this.username = username;
        this.hashtag = hashtag;
        this.postDate = postDate;
        this.favoriteCount = favoriteCount;
        this.retweetCount = retweetCount;
    }

    public String getResource() {
        return resource;
    }

    public String getMessage() {
        return message;
    }

    public String getMesssageId() {
        return messsageId;
    }

    public String getUsername() {
        return username;
    }

    public String getHashtag() {
        return hashtag;
    }

    public Timestamp getPostDate() {
        return postDate;
    }

    public int getFavoriteCount() {
        return favoriteCount;
    }

    public int getRetweetCount() {
        return retweetCount;
    }
}
