package edu.wpi.teamc.dao.requests;

import edu.wpi.teamc.dao.IOrm;
import edu.wpi.teamc.dao.users.IUser;
import lombok.Getter;
import lombok.Setter;

public abstract class AbsServiceRequest implements IOrm, IRequest {
  @Getter int requestID;
  @Getter @Setter private IUser requester;
  @Getter @Setter private String roomName;
  @Getter @Setter private STATUS status;
  @Getter @Setter private String additionalNotes;
  @Getter @Setter private String assignedto;

  AbsServiceRequest(IUser requester, String roomName, String additionalNotes) {
    this.requester = requester;
    this.roomName = roomName;
    this.additionalNotes = additionalNotes;
    this.status = STATUS.PENDING;
  }

  AbsServiceRequest(int requestID, IUser requester, String roomName, String additionalNotes) {
    this.requestID = requestID;
    this.requester = requester;
    this.roomName = roomName;
    this.additionalNotes = additionalNotes;
    this.status = STATUS.PENDING;
  }

  AbsServiceRequest(
      int requestID,
      IUser requester,
      String roomName,
      String additionalNotes,
      STATUS status,
      String assignedto) {
    this.requestID = requestID;
    this.requester = requester;
    this.roomName = roomName;
    this.additionalNotes = additionalNotes;
    this.status = status;
    this.assignedto = assignedto;
  }

  AbsServiceRequest() {}
}
