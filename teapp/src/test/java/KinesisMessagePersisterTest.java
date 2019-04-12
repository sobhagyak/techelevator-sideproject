import com.techelevator.dao.KinesisMessagePersister;
import com.techelevator.model.PublishedMessage;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.nio.ByteBuffer;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Date;


public class KinesisMessagePersisterTest {

    KinesisMessagePersister kinesisMessagePersister;

    @Before
    public void setup () {
        kinesisMessagePersister = new KinesisMessagePersister();
    }

    @Test
    public void messageToByteArrayConverterTest() {
        LocalDate localDate = LocalDate.now();
        Instant date = localDate.atStartOfDay(ZoneId.systemDefault()).toInstant();
        PublishedMessage testMessage = new PublishedMessage("TestClass",
                "Testing makes life easier for sure",
                12234324l, "UserSK",
                "#ha #test #cool", date.atZone(ZoneId.systemDefault()).toLocalDate(), 5, 3);

        ByteBuffer byteBuffer = kinesisMessagePersister.messageToByteArrayConverter(testMessage);

        PublishedMessage read = null;
        ByteArrayInputStream bis = new ByteArrayInputStream(byteBuffer.array());
        ObjectInput in = null;
        try {
            in = new ObjectInputStream(bis);
            Object o = in.readObject();
            read = (PublishedMessage) o;
        } catch (Exception e) {

        }
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                // ignore close exception
            }
        }

        Assert.assertTrue(read.getResource().equals("TestClass"));

    }

/*    @Test
    public void validateMessageTest() {
        LocalDate localDate = LocalDate.now();
        Instant date = localDate.atStartOfDay(ZoneId.systemDefault()).toInstant();
        PublishedMessage testMessage = new PublishedMessage("TestClass",
                "Testing makes life easier for sure",
                12234324l,null,
                "#ha #test #cool", date.atZone(ZoneId.systemDefault()).toLocalDate(), 5, 3);

        boolean test = kinesisMessagePersister.validateMessage(testMessage);
        Assert.assertTrue("Message cannot have nulls", !test);

        PublishedMessage testMessage1 = new PublishedMessage("TestClass1",
                "Testing makes life easier for sure 2",
                12234324l,"user1",
                "#ha #test #cool", date.atZone(ZoneId.systemDefault()).toLocalDate(), 5, 3);

        boolean test2 = kinesisMessagePersister.validateMessage(testMessage1);
        Assert.assertTrue(test2);


    }*/

}
