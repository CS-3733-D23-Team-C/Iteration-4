package edu.wpi.teamc.controllers.components;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ClockComponent {
  private String formattedDateTime;

  public ClockComponent() {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, MMMM dd, yyyy - hh:mm:ss a");
    LocalDateTime dateTime = LocalDateTime.now();
    String formattedDateTime = dateTime.format(formatter);
  }

  public String getFormattedDateTime() {
    return formattedDateTime;
  }
}
