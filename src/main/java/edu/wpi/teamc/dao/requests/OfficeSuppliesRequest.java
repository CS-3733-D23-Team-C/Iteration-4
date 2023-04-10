package edu.wpi.teamc.dao.requests;

import lombok.Getter;
import lombok.Setter;

public class OfficeSuppliesRequest extends AbsServiceRequest {
@Getter
  private String supplies;

@Getter @Setter
    private String eta;
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
