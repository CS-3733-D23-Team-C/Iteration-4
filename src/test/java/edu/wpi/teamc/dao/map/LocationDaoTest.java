package edu.wpi.teamc.dao.map;

import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class LocationDaoTest {

  @Test
  public void fetchAllObjects() {
    LocationDao locDao = new LocationDao();
    List<LocationName> locations = locDao.fetchAllObjects();

    Assertions.assertNotNull(locations);
  }

  @Test
  public void updateRow() {
    LocationDao locDao = new LocationDao();
    LocationName locName =
        new LocationName("15 Francis Security Desk Floor 2", "Information Desk", "INFO");
    LocationName repl = new LocationName("15 Francis Desk", "Information Desk", "INFO");

    Assertions.assertEquals(1, locDao.updateRow(locName, repl));
  }

  @Test
  public void addRow() {
    LocationDao locDao = new LocationDao();
    LocationName locName = new LocationName("15 Francis Info Center", "Information Center", "INFO");

    Assertions.assertEquals(1, locDao.addRow(locName));
  }

  @Test
  public void deleteRow() {
    LocationDao locDao = new LocationDao();
    LocationName locName = new LocationName("15 Francis Desk", "Information Desk", "INFO");

    Assertions.assertEquals(1, locDao.deleteRow(locName));
  }
}
