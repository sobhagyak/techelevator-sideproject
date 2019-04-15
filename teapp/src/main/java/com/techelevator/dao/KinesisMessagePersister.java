package com.techelevator.dao;


import com.amazonaws.AmazonClientException;
import com.amazonaws.services.kinesis.AmazonKinesis;
import com.amazonaws.services.kinesis.AmazonKinesisClientBuilder;
import com.amazonaws.services.kinesis.model.PutRecordRequest;
import com.amazonaws.services.kinesis.model.PutRecordResult;
import com.techelevator.model.PublishedMessage;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
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

        // validate message fields - nahhhhh

        PutRecordRequest recordRequest = new PutRecordRequest();
        recordRequest.setStreamName(streamName);
        // --------------DECIDE ON PARTITION STRATEGY-------------------
        recordRequest.setPartitionKey(message.getMessage());

        // convert PublishMessage to byte array and set it as a kinesis message
        try {
            recordRequest.setData(messageToByteArrayConverter(message));        // recordRequest.setData(JSON.writeValueAsBytes(message))
            System.out.println("Successfuly published message to Kinesis YAY ");
        } catch (Exception e) {
            LOG.error("Something went wrong publishing the message " + message.getMessage());
        }

        // Push to kinesis
        try {
            PutRecordResult result = publisherClient.putRecord(recordRequest);
            LOG.info("Message Published to Kinesis Stream " + streamName + " Message: " + result);
        } catch (AmazonClientException ex) {
            LOG.error("Error sending message to client ", ex);
        }
    }


/*    public boolean validateMessage(PublishedMessage message) {
        try {
            return Stream.of(message)
                    .allMatch(Objects::nonNull);
        } catch (Exception e) {
            return false;
        }
    }*/

    public ByteBuffer messageToByteArrayConverter(PublishedMessage message) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try (ObjectOutput out = new ObjectOutputStream(bos)) {
            out.writeObject(message);                                         // Alternatively use JACKSON library
            out.flush();                                                      // ObjectMapper JSON = new ObjectMapper();
        } catch (Exception e) {
            LOG.error("Something went wrong serializing the message: " + message.getMessage());
        } finally {
            try {
                bos.close();
            } catch (Exception e) {
                LOG.error("Something went wrong closing the ByteArrayOutputStream");
            }
        }
        return ByteBuffer.wrap(bos.toByteArray());
    }
}
