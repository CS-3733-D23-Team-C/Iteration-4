package edu.wpi.teamc.dao.requests;

public class FlowerDeliveryRequest extends ServiceRequest {

  public FlowerDeliveryRequest(int requestID, Requester requester, String additionalNotes) {
    super(requestID, requester, additionalNotes);
  }
  @Override
  void executeRequest() {}
}
