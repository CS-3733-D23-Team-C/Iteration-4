package edu.wpi.teamc.dao.requests;

import edu.wpi.teamc.dao.users.IUser;
import lombok.Getter;
import lombok.Setter;

public class FlowerDeliveryRequest extends AbsServiceRequest {
  @Getter private String flower;
  @Getter @Setter private String eta;

  FlowerDeliveryRequest(
      int requestID, IUser requester, String roomName, String flower, String additionalNotes) {
    super(requestID, requester, roomName, additionalNotes);
    this.flower = flower;
  }

  public FlowerDeliveryRequest(
      IUser requester, String roomName, String flower, String additionalNotes) {
    super(requester, roomName, additionalNotes);
    this.flower = flower;
  }
}
