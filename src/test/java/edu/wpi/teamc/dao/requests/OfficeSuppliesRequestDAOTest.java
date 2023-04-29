package edu.wpi.teamc.dao.requests;

import edu.wpi.teamc.dao.users.PatientUser;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class OfficeSuppliesRequestDAOTest {

  @Test
  public void fetchAllObjects() {
    OfficeSuppliesRequestDAO dao = new OfficeSuppliesRequestDAO();
    List<OfficeSuppliesRequest> list = dao.fetchAllObjects();
    Assertions.assertNotNull(list);
  }

  @Test
  public void addRow() {
    OfficeSuppliesRequestDAO dao = new OfficeSuppliesRequestDAO();
    dao.addRow(new OfficeSuppliesRequest(new PatientUser("Chonk"), "Office Five", "Pencil", "1"));
  }
}
