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
     RequestSystem(List<AbsServiceRequest> requests) {
        this.requests = requests;
    }
    public RequestSystem() {
         List<AbsServiceRequest> requestTypes = new ArrayList<>();
         requestTypes.add(new ConferenceRoomRequest());
        requestTypes.add(new MealRequest());
        requestTypes.add(new FlowerDeliveryRequest());
        requestTypes.add(new FurnitureDeliveryRequest());
        requestTypes.add(new OfficeSuppliesRequest());
        for (AbsServiceRequest request : requestTypes) {
            requests.addAll((Collection<? extends AbsServiceRequest>) HospitalSystem.fetchAllObjects((IOrm) request));
        }
    }

public void addRequest(IRequest request) {
    HospitalSystem.addRow((IOrm) request);

}

public void removeRequest(IRequest request) {
    HospitalSystem.deleteRow((IOrm) request);
}

public void updateRequest(IRequest request) {
    HospitalSystem.updateRow((IOrm) request);
}

public List<AbsServiceRequest> filterRequest(STATUS status) {
    List<AbsServiceRequest> filteredRequests = new ArrayList<>();
    for (AbsServiceRequest request : requests) {
        if (request.getStatus().equals(status)) {
            filteredRequests.add(request);
        }
    }
    return filteredRequests;
}

public List<AbsServiceRequest> filterRequest(String assingedto) {
    List<AbsServiceRequest> filteredRequests = new ArrayList<>();
    for (AbsServiceRequest request : requests) {
        if (request.getAssignedto().equals(assingedto)) {
            filteredRequests.add(request);
        }
    }
    return filteredRequests;
}

    public List<AbsServiceRequest> filterRequest(IRequest requestType) {
        List<AbsServiceRequest> filteredRequests = new ArrayList<>();
        for (AbsServiceRequest request : requests) {
            if (request.getClass().equals(requestType.getClass())) {
                filteredRequests.add(request);
            }
        }
        return filteredRequests;
    }

public List<AbsServiceRequest> filterRequest(String assingedto, STATUS status) {
    List<AbsServiceRequest> filteredRequests = new ArrayList<>();
    for (AbsServiceRequest request : requests) {
        if (request.getAssignedto().equals(assingedto) && request.getStatus().equals(status)) {
            filteredRequests.add(request);
        }
    }
    return filteredRequests;
}

public List<AbsServiceRequest> filterRequest(String assingedto, STATUS status, IRequest request) {
    List<AbsServiceRequest> filteredRequests = new ArrayList<>();
    for (AbsServiceRequest request1 : requests) {
        if (request1.getAssignedto().equals(assingedto) && request1.getStatus().equals(status) && request1.getClass().equals(request.getClass())) {
            filteredRequests.add(request1);
        }
    }
    return filteredRequests;
}



}
