package edu.wpi.teamc.dao.requests;

import edu.wpi.teamc.dao.users.IUser;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FurnitureDeliveryRequest extends AbsServiceRequest {

  private String furnituretype;
  private String eta;

  FurnitureDeliveryRequest(
      int requestID,
      IUser requester,
      String roomName,
      String additionalNotes,
      String furnituretype,
      String eta) {
    super(requestID, requester, roomName, additionalNotes);
    this.furnituretype = furnituretype;
    this.eta = eta;
  }

  FurnitureDeliveryRequest(
      int requestID,
      IUser requester,
      String roomName,
      STATUS status,
      String additionalNotes,
      String furnituretype,
      String eta) {
    super(requestID, requester, roomName, additionalNotes);
    this.furnituretype = furnituretype;
    this.eta = eta;
  }

  public FurnitureDeliveryRequest(
      IUser requester, String roomName, String additionalNotes, String furnituretype) {
    super(requester, roomName, additionalNotes);
    this.furnituretype = furnituretype;
  }

  public FurnitureDeliveryRequest() {}
}
