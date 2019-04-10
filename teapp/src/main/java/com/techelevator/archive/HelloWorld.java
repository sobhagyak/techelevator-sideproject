package com.techelevator.archive;
import com.amazonaws.services.kinesis.*;
import com.amazonaws.services.kinesis.model.*;

import java.util.*;

public class HelloWorld {


    public static void main(String[] args) {
        System.out.printf("Hey");
    }




    public static void kinesisClient() {
        AmazonKinesis client = AmazonKinesisClientBuilder.standard().build();
        String streamName = "stream-test-1";

        KinesisProducer.publishMessages(client);

        List<Record> records = KinesisConsumer.returnMessages(client, streamName);
        List<String> messages = new ArrayList<String>();

        for(Record r : records){
            messages.add(new String(r.getData().array()));
            System.out.println("sequenceNumber id: " + r.getSequenceNumber()
                    + " Message " + messages.get(messages.size() - 1));
        }

        System.out.println ("Sending message "+(messages.get(messages.size() - 1)));

        SmsSender.sendMessage("You are receiving a message from teapp :" +
                        messages.get(messages.size() - 1) + "\nRegards, Sobe",
                "+18147778437");
        // testing out async client
        AmazonKinesisAsync clienta = AmazonKinesisAsyncClientBuilder.standard().build();
        //.EndpointConfiguration("vv","dd");
        AmazonKinesis client1a = AmazonKinesisClientBuilder.standard().build();
        //= AmazonKinesisClientBuilder.standard().build();

    }
}
