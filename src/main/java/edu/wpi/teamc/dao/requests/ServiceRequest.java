package edu.wpi.teamc.dao.requests;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

abstract class ServiceRequest {
  @Getter private int requestID;
  @Getter Requester requester;
  @Getter @Setter private String roomName;
  @Getter @Setter STATUS status;
  @Getter @Setter private Date eta;
  @Getter String additionalNotes;

    public ServiceRequest(int requestID, Requester requester, String roomName, String additionalNotes) {
        this.requestID = requestID;
        this.requester = requester;
        this.roomName = roomName;
        this.additionalNotes = additionalNotes;
        this.status = STATUS.PENDING;
    }

  abstract void executeRequest();
}
