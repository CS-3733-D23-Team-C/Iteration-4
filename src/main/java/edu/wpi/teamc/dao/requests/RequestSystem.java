package edu.wpi.teamc.dao.requests;

import edu.wpi.teamc.dao.HospitalSystem;
import edu.wpi.teamc.dao.IOrm;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class RequestSystem {
  @Getter @Setter private List<AbsServiceRequest> requests;

  public RequestSystem(List<AbsServiceRequest> requests) {
    this.requests = requests;
  }

  public RequestSystem() {
    List<AbsServiceRequest> requestTypes = new ArrayList<>();
    requests = new ArrayList<>();
    requestTypes.add(new ConferenceRoomRequest());
    requestTypes.add(new MealRequest());
    requestTypes.add(new FlowerDeliveryRequest());
    requestTypes.add(new FurnitureDeliveryRequest());
    requestTypes.add(new OfficeSuppliesRequest());
    for (AbsServiceRequest request : requestTypes) {
      requests.addAll(
          (Collection<? extends AbsServiceRequest>) HospitalSystem.fetchAllObjects((IOrm) request));
    }
  }

  public List<AbsServiceRequest> filterRequest(String assingedto, STATUS status) {
    List<AbsServiceRequest> filteredRequests = new ArrayList<>();
    for (AbsServiceRequest request1 : requests) {
      boolean assignedToCheck;
      try {
        if (assingedto == null) {
          assignedToCheck = true;
        } else {
          assignedToCheck = request1.getAssignedto().equals(assingedto);
        }
      } catch (Exception e) {
        assignedToCheck = false;
      }

      boolean statusCheck;
      try {
        if (status == null) {
          statusCheck = true;
        } else {
          statusCheck = request1.getStatus().equals(status);
        }
      } catch (Exception e) {
        statusCheck = false;
      }
      System.out.println(
          "assignedToCheck: "
              + assignedToCheck
              + " statusCheck: "
              + statusCheck
              + " request1: "
              + request1.getRequestID());
      if (assignedToCheck && statusCheck) {
        filteredRequests.add(request1);
      }
    }
    return filteredRequests;
  }
}
