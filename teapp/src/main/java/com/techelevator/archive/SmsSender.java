package com.techelevator.archive;
// Install the Java helper library from twilio.com/docs/libraries/java
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

public class SmsSender {
    // Find your Account Sid and Auth Token at twilio.com/console
    public static final String ACCOUNT_SID =
            "ACca2793870222a66ee41013795f8448a9";
    public static final String AUTH_TOKEN =
            "7871319fe18769f74bedfaec0334a110";

    public static void sendMessage(String msg, String phone) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        Message message = Message
                .creator(new PhoneNumber(phone), // to
                        new PhoneNumber("+18317775889"), // from
                        msg)
                .create();

        System.out.println(msg);
    }
}

