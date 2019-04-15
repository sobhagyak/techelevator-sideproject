package com.techelevator.archive;
// Install the Java helper library from twilio.com/docs/libraries/java
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class SmsSender {
    // Find your Account Sid and Auth Token at twilio.com/console
    public static String ACCOUNT_SID;
    public static String AUTH_TOKEN;

    public static void sendMessage(String msg, String phone) {
        loadProperties();
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        Message message = Message
                .creator(new PhoneNumber(phone), // to
                        new PhoneNumber("+18317775889"), // from
                        msg)
                .create();

        System.out.println(msg);
    }

    private static void loadProperties() {
        Properties prop = new Properties();
        InputStream input = null;

        try {
            input = new FileInputStream("teapp\\src\\main\\resources\\twilioConfig.properties");
            prop.load(input);

            ACCOUNT_SID=prop.getProperty("ACCOUNT_SID");
            AUTH_TOKEN=prop.getProperty("AUTH_TOKEN");

        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

