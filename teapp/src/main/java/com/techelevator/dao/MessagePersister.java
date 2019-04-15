package com.techelevator.dao;

import com.techelevator.model.PublishedMessage;

public interface MessagePersister<T> {

    public void initialize(String streamName);

    public void persist(T message);

}
