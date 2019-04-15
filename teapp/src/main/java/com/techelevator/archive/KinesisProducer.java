package com.techelevator.archive;

import com.amazonaws.services.kinesis.AmazonKinesis;
import com.amazonaws.services.kinesis.AmazonKinesisClientBuilder;
import com.amazonaws.services.kinesis.model.PutRecordRequest;
import com.amazonaws.services.kinesis.model.PutRecordResult;
import com.techelevator.dao.KinesisMessagePersister;

import java.nio.ByteBuffer;
import java.sql.Timestamp;

public class KinesisProducer {

    public static void publishMessages(AmazonKinesis client) {
        String streamName = "stream-test-1";
        PutRecordRequest request = new PutRecordRequest();
        request.setStreamName(streamName);
        request.setPartitionKey("partition1");

        String message = "Hello data "
                + new Timestamp(System.currentTimeMillis());
        System.out.println("Message Sent: "+ message);

        request.setData(ByteBuffer.wrap(message.getBytes()));
        PutRecordResult result = client.putRecord(request); 
    }
}
