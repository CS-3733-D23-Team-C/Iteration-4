package edu.wpi.teamc.dao.requests;

import lombok.Getter;
import lombok.Setter;

abstract class ServiceRequest {
  @Getter private int requestID;
  @Getter Requester requester;
  @Getter @Setter STATUS status;
  String additionalNotes;

    public ServiceRequest(int requestID, Requester requester, String additionalNotes) {
        this.requestID = requestID;
        this.requester = requester;
        this.additionalNotes = additionalNotes;
    }

  abstract void executeRequest();
}
