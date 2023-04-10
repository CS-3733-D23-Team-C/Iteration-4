package edu.wpi.teamc.dao.requests;

public class OfficeSuppliesRequest extends AbsServiceRequest {

  private String supplies;

  public OfficeSuppliesRequest(
      int requestID,
      Requester requester,
      String roomName,
      String supplies,
      String additionalNotes) {
    super(requestID, requester, roomName, additionalNotes);
    this.supplies = supplies;
  }

  @Override
  void executeRequest() {}
}
