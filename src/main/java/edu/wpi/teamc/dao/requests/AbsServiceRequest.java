package edu.wpi.teamc.dao.requests;

import edu.wpi.teamc.dao.IOrm;
import lombok.Getter;
import lombok.Setter;

abstract class AbsServiceRequest implements IOrm {
  @Getter @Setter private int requestID;
  @Getter Requester requester;
  @Getter @Setter private String roomName;
  @Getter @Setter STATUS status;
  @Getter String additionalNotes;

   AbsServiceRequest(
          Requester requester, String roomName, String additionalNotes) {
    this.requester = requester;
    this.roomName = roomName;
    this.additionalNotes = additionalNotes;
    this.status = STATUS.PENDING;
  }
   AbsServiceRequest(
      int requestID, Requester requester, String roomName, String additionalNotes) {
    this.requestID = requestID;
    this.requester = requester;
    this.roomName = roomName;
    this.additionalNotes = additionalNotes;
    this.status = STATUS.PENDING;
  }

   AbsServiceRequest(
      int requestID, Requester requester, String roomName, String additionalNotes, STATUS status) {
    this.requestID = requestID;
    this.requester = requester;
    this.roomName = roomName;
    this.additionalNotes = additionalNotes;
    this.status = status;
  }

  abstract void executeRequest();
}
