package com.techelevator.archive;

import com.amazonaws.services.kinesis.AmazonKinesis;
import com.amazonaws.services.kinesis.model.GetRecordsRequest;
import com.amazonaws.services.kinesis.model.Record;
import com.amazonaws.services.kinesis.model.Shard;
import com.amazonaws.services.kinesis.model.StreamDescription;

import java.util.List;

public class KinesisConsumer {

    public static List<Record> returnMessages(AmazonKinesis client, String streamName) {
        StreamDescription sResult = client.describeStream(streamName).getStreamDescription();
        Shard shard = sResult.getShards().get(0);
        String shardIteror = client.getShardIterator(streamName, shard.getShardId(),"TRIM_HORIZON")
                .getShardIterator();
        GetRecordsRequest gRequest = new GetRecordsRequest();
        gRequest.setShardIterator(shardIteror);

        return client.getRecords(gRequest).getRecords();
    }
}
