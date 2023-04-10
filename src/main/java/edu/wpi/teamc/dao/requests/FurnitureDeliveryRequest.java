package edu.wpi.teamc.dao.requests;

public class FurnitureDeliveryRequest extends AbsServiceRequest {
  private String furniture;

  public FurnitureDeliveryRequest(
      int requestID,
      Requester requester,
      String roomName,
      String furniture,
      String additionalNotes) {
    super(requestID, requester, roomName, additionalNotes);
    this.furniture = furniture;
  }

  @Override
  void executeRequest() {}
}
