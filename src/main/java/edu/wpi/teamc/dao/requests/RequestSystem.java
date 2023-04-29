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

public List<AbsServiceRequest> filterRequest(String assingedto, STATUS status, IRequest request) {
    List<AbsServiceRequest> filteredRequests = new ArrayList<>();
    for (AbsServiceRequest request1 : requests) {
        boolean assignedToCheck = request1.getAssignedto().equals(assingedto);
        if (assingedto == null) {assignedToCheck = true;}
        boolean statusCheck = request1.getStatus().equals(status);
        if (status == null) {statusCheck = true;}
        boolean requestCheck = request1.getClass().equals(request.getClass());
        if (request == null) {requestCheck = true;}
        if (assignedToCheck &&  statusCheck && requestCheck) {
            filteredRequests.add(request1);
        }
    }
    return filteredRequests;
}



}
