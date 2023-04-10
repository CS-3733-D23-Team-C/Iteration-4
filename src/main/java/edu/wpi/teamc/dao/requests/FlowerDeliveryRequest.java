package edu.wpi.teamc.dao.requests;

public class FlowerDeliveryRequest extends AbsServiceRequest {
  private String flower;

  public FlowerDeliveryRequest(
      int requestID, Requester requester, String roomName, String flower, String additionalNotes) {
    super(requestID, requester, roomName, additionalNotes);
    this.flower = flower;
  }

  @Override
  void executeRequest() {}
}
