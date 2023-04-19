package edu.wpi.teamc.dao.requests;

import lombok.Getter;
import lombok.Setter;

public class OfficeSuppliesRequest extends AbsServiceRequest {
  @Getter private String officesupplytype;

  @Getter @Setter private String eta;

  public OfficeSuppliesRequest(
      Requester requester, String roomName, String officesupplytype, String additionalNotes) {
    super(requester, roomName, additionalNotes);
    this.officesupplytype = officesupplytype;
  }

  OfficeSuppliesRequest(
      int requestID,
      Requester requester,
      String roomname,
      String officesupplytype,
      String additionalnotes) {
    super(requestID, requester, roomname, additionalnotes);
    this.officesupplytype = officesupplytype;
  }

  @Override
  void executeRequest() {}
}
