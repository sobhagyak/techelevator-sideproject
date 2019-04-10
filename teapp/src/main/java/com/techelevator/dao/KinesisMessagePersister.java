package com.techelevator.dao;


import com.amazonaws.AmazonClientException;
import com.amazonaws.services.kinesis.AmazonKinesis;
import com.amazonaws.services.kinesis.AmazonKinesisClient;
import com.amazonaws.services.kinesis.AmazonKinesisClientBuilder;
import com.amazonaws.services.kinesis.model.PutRecordRequest;
import com.amazonaws.services.kinesis.model.PutRecordResult;
import com.techelevator.model.PublishedMessage;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;
import java.nio.ByteBuffer;

public class KinesisMessagePersister implements MessagePersister<PublishedMessage> {

    private static final Log LOG = LogFactory.getLog(KinesisMessagePersister.class);
    private AmazonKinesis publisherClient;
    private String streamName;

    public void initialize(String streamName) {
        this.publisherClient = AmazonKinesisClientBuilder.standard().build();
        this.streamName = streamName;
    }

    // gets a twitter message and persists it on kinesis
    public void persist(PublishedMessage message) {

        // validate message fields
        if (!validateMessage(message)) {
            return;
        }

        PutRecordRequest recordRequest = new PutRecordRequest();
        recordRequest.setStreamName(streamName);

        // convert PublishMessage to byte array
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try (ObjectOutput out = new ObjectOutputStream(bos)) {
            out.writeObject(message);                                         // Alternatively use JACKSON library
            out.flush();                                                      // ObjectMapper JSON = new ObjectMapper();
            recordRequest.setData(ByteBuffer.wrap(bos.toByteArray()));        // recordRequest.setData(JSON.writeValueAsBytes(message))
        } catch (Exception e) {
            LOG.error("Something went wrong serializing the message " + message.getMessage());
        } finally {
            try {
                bos.close();
            } catch (Exception e) {
                LOG.error("Something went wrong closing the ByteArrayOutputStream");
            }
        }

        // Push to kinesis
        try {
            PutRecordResult result = publisherClient.putRecord(recordRequest);
            LOG.info("Message Published to Kinesis Stream " + streamName + " Message: " + result);
        } catch (AmazonClientException ex) {
            LOG.error("Error sending message to client ", ex);
        }
    }

    public boolean validateMessage(PublishedMessage message) {
        for (Field field : getClass().getDeclaredFields()) {
            try {
                if (field.get(this) == null) {
                    return false;
                }
            } catch (Exception e) {
                LOG.error("Message has null field: " + field.getName());
            }
        }
        return true;
    }
}
