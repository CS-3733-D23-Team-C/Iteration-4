package edu.wpi.teamc.dao.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConferenceRoom {
  private String longName;
  private String shortName;
  private Boolean availability;

  public ConferenceRoom(String longName, String shortName, Boolean availability) {
    this.longName = longName;
    this.shortName = shortName;
    this.availability = availability;
  }

  public ConferenceRoom(String longName) {
    this.longName = longName;
  }

  @Override
  public String toString() {
    return this.longName;
  }
}
