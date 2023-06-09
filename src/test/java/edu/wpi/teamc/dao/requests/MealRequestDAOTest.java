package edu.wpi.teamc.dao.requests;

import edu.wpi.teamc.dao.users.PatientUser;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MealRequestDAOTest {

  @Test
  public void fetchAllObjects() {
    MealRequestDAO dao = new MealRequestDAO();
    List<MealRequest> list = dao.fetchAllObjects();
    Assertions.assertNotNull(list);
  }

  @Test
  public void addRow() {
    MealRequestDAO dao = new MealRequestDAO();
    dao.addRow(
        new MealRequest(new PatientUser("Bob"), "Morgue", "None", new Meal("Burger", "Water")));
  }
}
