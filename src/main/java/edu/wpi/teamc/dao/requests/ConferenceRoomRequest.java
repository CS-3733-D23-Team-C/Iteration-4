package edu.wpi.teamc.dao.requests;

import lombok.Getter;
import lombok.Setter;

public class ConferenceRoomRequest extends ServiceRequest {

  @Getter private ConferenceRoom conferenceRoom;
  private String startTime;
  private String endTime;

  @Override
  void executeRequest() {}
}
