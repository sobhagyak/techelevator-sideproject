package com.techelevator.model;

import java.sql.Timestamp;
import java.util.Date;

public class PublishedMessage {

    private String resource;
    private String message;
    private Long messsageId;
    private String username; // user screen name in twitter
    private String hashtag;
    private Date postDate;
    private int favoriteCount;
    private int retweetCount;

    public PublishedMessage(String resource, String message, Long messsageId, String username,
                            String hashtag, Date postDate, int favoriteCount, int retweetCount) {
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

    public Long getMesssageId() {
        return messsageId;
    }

    public String getUsername() {
        return username;
    }

    public String getHashtag() {
        return hashtag;
    }

    public Date getPostDate() {
        return postDate;
    }

    public int getFavoriteCount() {
        return favoriteCount;
    }

    public int getRetweetCount() {
        return retweetCount;
    }

    @Override
    public String toString() {
        return "Resource: "+this.resource +
        "\nStatus: " + this.getMessage() + "\nStatus Id: " + this.getMesssageId() +
        "\nUser: " + this.getUsername() + "\nHashtags" + this.getHashtag() +
        "\nCreated Time: " + this.getPostDate() + "\nFavorite Count: " + this.getFavoriteCount() +
                "\nRetweet Count: " + this.getRetweetCount();
    }
}
