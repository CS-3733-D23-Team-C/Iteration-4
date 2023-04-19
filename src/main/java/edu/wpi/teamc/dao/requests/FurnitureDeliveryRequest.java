package edu.wpi.teamc.dao.requests;

import edu.wpi.teamc.dao.users.IUser;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FurnitureDeliveryRequest extends AbsServiceRequest {

  private String furnitureType;
  private String eta;

  FurnitureDeliveryRequest(
      int requestID,
      IUser requester,
      String roomName,
      String additionalNotes,
      String furnitureType,
      String eta) {
    super(requestID, requester, roomName, additionalNotes);
    this.furnitureType = furnitureType;
    this.eta = eta;
  }

  public FurnitureDeliveryRequest(
      IUser requester, String roomName, String additionalNotes, String furnitureType) {
    super(requester, roomName, additionalNotes);
    this.furnitureType = furnitureType;
  }
}
