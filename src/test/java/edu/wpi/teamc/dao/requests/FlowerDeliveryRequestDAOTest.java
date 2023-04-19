package edu.wpi.teamc.dao.requests;

import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class FlowerDeliveryRequestDAOTest {

  @Test
  public void fetchAllObjects() {
    FlowerDeliveryRequestDAO dao = new FlowerDeliveryRequestDAO();
    List<FlowerDeliveryRequest> list = dao.fetchAllObjects();
    Assertions.assertNotNull(list);
  }

  @Test
  public void updateRow() {}

  @Test
  public void addRow() {
    FlowerDeliveryRequestDAO dao = new FlowerDeliveryRequestDAO();
    dao.addRow(new FlowerDeliveryRequest(0, new IUser(0, "Charles"), "Med Bay 2", "Rose", "None"));
  }

  @Test
  public void deleteRow() {}
}
