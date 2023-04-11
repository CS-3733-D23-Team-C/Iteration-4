package edu.wpi.teamc.dao.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FurnitureDeliveryRequest extends AbsServiceRequest {
  private String furniture;
  private String eta;

  public FurnitureDeliveryRequest(
      int requestID,
      Requester requester,
      String roomName,
      String additionalNotes,
      String furniture,
      String eta) {
    super(requestID, requester, roomName, additionalNotes);
    this.furniture = furniture;
    this.eta = eta;
  }

  public FurnitureDeliveryRequest(
      int requestID,
      Requester requester,
      String roomName,
      String additionalNotes,
      String furniture) {
    super(requestID, requester, roomName, additionalNotes);
    this.furniture = furniture;
  }

  @Override
  void executeRequest() {}
}
