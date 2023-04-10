package edu.wpi.teamc.dao.requests;

public class FurnitureDeliveryRequest extends AbsServiceRequest {
private String furniture;
private String eta;
  public FurnitureDeliveryRequest(int requestID, Requester requester, String roomName, String additionalNotes, String furniture, String eta) {
    super(requestID,  requester,  roomName,  additionalNotes);
    this.furniture = furniture;
    this.eta = eta;
  }
  @Override
  void executeRequest() {}
}
