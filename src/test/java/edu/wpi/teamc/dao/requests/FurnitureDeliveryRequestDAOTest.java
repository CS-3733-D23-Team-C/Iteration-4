package edu.wpi.teamc.dao.requests;

import edu.wpi.teamc.dao.users.PatientUser;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class FurnitureDeliveryRequestDAOTest {

  @Test
  public void fetchAllObjects() {
    FurnitureDeliveryRequestDAO dao = new FurnitureDeliveryRequestDAO();
    List<FurnitureDeliveryRequest> list = dao.fetchAllObjects();
    Assertions.assertNotNull(list);
  }

  @Test
  public void addRow() {
    FurnitureDeliveryRequestDAO dao = new FurnitureDeliveryRequestDAO();
    dao.addRow(
        new FurnitureDeliveryRequest(new PatientUser("Angela"), "Cubicle 7", "None", "Couch"));
  }
}
