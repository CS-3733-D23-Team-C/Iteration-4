package edu.wpi.teamc.dao.requests;

import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ConferenceRoomRequestDAOTest {

  @Test
  public void fetchAllObjects() {
    ConferenceRoomRequestDAO dao = new ConferenceRoomRequestDAO();
    List<ConferenceRoomRequest> confs = dao.fetchAllObjects();
    Assertions.assertNotNull(confs);
  }

  @Test
  public void addRow() {
    ConferenceRoomRequestDAO dao = new ConferenceRoomRequestDAO();
    dao.addRow(
        new ConferenceRoomRequest(
            0,
            new Requester(0, "Bob"),
            new ConferenceRoom("Office Room", "", false),
            "",
            "now",
            "then",
            STATUS.COMPLETE));
  }

  @Test
  public void updateRow() {
    ConferenceRoomRequestDAO dao = new ConferenceRoomRequestDAO();
    dao.updateRow(
        new ConferenceRoomRequest(
            10,
            new Requester(0, "Bob"),
            new ConferenceRoom("Office Room", "", false),
            "",
            "now",
            "then",
            STATUS.COMPLETE),
        new ConferenceRoomRequest(
            10,
            new Requester(0, "Abby"),
            new ConferenceRoom("Office Room B4", "", false),
            "",
            "then",
            "now",
            STATUS.PENDING));
  }

  @Test
  public void deleteRow() {}
}
