package edu.wpi.teamc.dao.requests;

import edu.wpi.teamc.dao.IOrm;
import edu.wpi.teamc.dao.users.EmployeeUser;
import edu.wpi.teamc.dao.users.IUser;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

public abstract class AbsServiceRequest implements IOrm, IRequest {
  @Getter int requestID;
  @Getter private IUser requester;
  @Getter @Setter private String roomName;
  @Getter @Setter private STATUS status;
  @Getter private String additionalNotes;
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

  public AbsServiceRequest() {}

  public List<FlowerDeliveryRequest> filterRequestFlower(
      List<FlowerDeliveryRequest> requests, STATUS status) {
    List<FlowerDeliveryRequest> filteredRequests = new ArrayList<>();
    for (FlowerDeliveryRequest request : requests) {
      if (request.getStatus().equals(status)) {
        filteredRequests.add(request);
      }
    }
    return filteredRequests;
  }

  public List<ConferenceRoomRequest> filterRequestConference(
      List<ConferenceRoomRequest> requests, STATUS status) {
    List<ConferenceRoomRequest> filteredRequests = new ArrayList<>();
    for (ConferenceRoomRequest request : requests) {
      if (request.getStatus().equals(status)) {
        filteredRequests.add(request);
      }
    }
    return filteredRequests;
  }

  public List<FurnitureDeliveryRequest> filterRequestFurniture(
      List<FurnitureDeliveryRequest> requests, STATUS status) {
    List<FurnitureDeliveryRequest> filteredRequests = new ArrayList<>();
    for (FurnitureDeliveryRequest request : requests) {
      if (request.getStatus().equals(status)) {
        filteredRequests.add(request);
      }
    }
    return filteredRequests;
  }

  public List<MealRequest> filterRequestMeal(List<MealRequest> requests, STATUS status) {
    List<MealRequest> filteredRequests = new ArrayList<>();
    for (MealRequest request : requests) {
      if (request.getStatus().equals(status)) {
        filteredRequests.add(request);
      }
    }
    return filteredRequests;
  }

  public List<OfficeSuppliesRequest> filterRequestOfficeSupplies(
      List<OfficeSuppliesRequest> requests, STATUS status) {
    List<OfficeSuppliesRequest> filteredRequests = new ArrayList<>();
    for (OfficeSuppliesRequest request : requests) {
      if (request.getStatus().equals(status)) {
        filteredRequests.add(request);
      }
    }
    return filteredRequests;
  }

  //    public List<InterpretationRequest> filterRequest(List<InterpretationRequest> requests,
  // STATUS status) {
  //        List<InterpretationRequest> filteredRequests = new ArrayList<>();
  //        for (InterpretationRequest request : requests) {
  //        if (request.getStatus().equals(status)) {
  //            filteredRequests.add(request);
  //        }
  //        }
  //        return filteredRequests;
  //    }

  public List<FlowerDeliveryRequest> filterRequestFlowerEmp(
      List<FlowerDeliveryRequest> requests, EmployeeUser employee) {
    List<FlowerDeliveryRequest> filteredRequests = new ArrayList<>();
    for (FlowerDeliveryRequest request : requests) {
      if (request.getAssignedto().equals(employee.toString())) {
        filteredRequests.add(request);
      }
    }
    return filteredRequests;
  }

  public List<ConferenceRoomRequest> filterRequestConferenceEmp(
      List<ConferenceRoomRequest> requests, EmployeeUser employee) {
    List<ConferenceRoomRequest> filteredRequests = new ArrayList<>();
    for (ConferenceRoomRequest request : requests) {
      if (request.getAssignedto().equals(employee.toString())) {
        filteredRequests.add(request);
      }
    }
    return filteredRequests;
  }

  public List<FurnitureDeliveryRequest> filterRequestFurnitureEmp(
      List<FurnitureDeliveryRequest> requests, EmployeeUser employee) {
    List<FurnitureDeliveryRequest> filteredRequests = new ArrayList<>();
    for (FurnitureDeliveryRequest request : requests) {
      if (request.getAssignedto().equals(employee.toString())) {
        filteredRequests.add(request);
      }
    }
    return filteredRequests;
  }

  public List<MealRequest> filterRequestMealEmp(List<MealRequest> requests, EmployeeUser employee) {
    List<MealRequest> filteredRequests = new ArrayList<>();
    for (MealRequest request : requests) {
      if (request.getAssignedto().equals(employee.toString())) {
        filteredRequests.add(request);
      }
    }
    return filteredRequests;
  }

  public List<OfficeSuppliesRequest> filterRequestOfficeSuppliesEmp(
      List<OfficeSuppliesRequest> requests, EmployeeUser employee) {
    List<OfficeSuppliesRequest> filteredRequests = new ArrayList<>();
    for (OfficeSuppliesRequest request : requests) {
      if (request.getAssignedto().equals(employee.toString())) {
        filteredRequests.add(request);
      }
    }
    return filteredRequests;
  }
}
