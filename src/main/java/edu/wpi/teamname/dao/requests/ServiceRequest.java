package edu.wpi.teamname.dao.requests;

import lombok.Getter;
import lombok.Setter;

abstract class ServiceRequest {
    @Getter
    Requester requester;
    @Getter @Setter
    STATUS status;
    String additionalNotes;

    abstract void executeRequest();
}
