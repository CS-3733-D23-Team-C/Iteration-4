package edu.wpi.teamc;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SMSHelper {

  public static final Logger logger =
      Logger.getLogger("SMS Helper"); // need to copy this and then just use logger. commands
  public static final String ACCOUNT_SID = System.getenv("TWILIO_ACCOUNT_SID");
  public static final String AUTH_TOKEN = System.getenv("TWILIO_AUTH_TOKEN");

  public static void sendSMS(String phoneNum, String text) {
    if (ACCOUNT_SID == null
        || ACCOUNT_SID.length() == 0
        || AUTH_TOKEN == null
        || AUTH_TOKEN.length() == 0) {
      logger.severe(
          "You  must send the next environment variables for twilio: TWILIO_ACCOUNT_SID, TWILIO_AUTH_TOKEN");
      throw new IllegalStateException(
          "You  must send the next environment variables for twilio: TWILIO_ACCOUNT_SID, TWILIO_AUTH_TOKEN");
    }

    // Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
    try {
      Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
      Message message =
          Message.creator(
                  new com.twilio.type.PhoneNumber(phoneNum),
                  new com.twilio.type.PhoneNumber("+18559181390"),
                  text)
              .create();

      System.out.println(message.getSid());

      logger.info("message sent to a number " + phoneNum + " message ID " + message.getSid());
    } catch (Exception e) {
      logger.log(
          Level.SEVERE, "Message could not be sent to the following phone number " + phoneNum, e);
    }
  }
}
