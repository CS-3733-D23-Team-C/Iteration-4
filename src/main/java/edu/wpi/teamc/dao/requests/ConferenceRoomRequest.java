package edu.wpi.teamc.dao.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConferenceRoomRequest extends AbsServiceRequest {
  private String startTime;
  private String endTime;

  public ConferenceRoomRequest(
      int requestID,
      Requester requester,
      ConferenceRoom conferenceRoom,
      String note,
      String startTime,
      String endTime) {
    super(requestID, requester, conferenceRoom.toString(), note);
    this.startTime = startTime;
    this.endTime = endTime;
  }

  @Override
  void executeRequest() {}
}
