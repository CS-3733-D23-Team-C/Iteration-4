package edu.wpi.teamc.dao.map;

import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class LocationDaoTest {

  @Test
  public void fetchAllObjects() {
    LocationNameDao locDao = new LocationNameDao();
    List<LocationName> locations = locDao.fetchAllObjects();

    Assertions.assertNotNull(locations);
  }

  @Test
  public void updateRow() {
    LocationNameDao locDao = new LocationNameDao();
    LocationName locName =
        new LocationName("15 Francis Security Desk Floor 2", "Information Desk", "INFO");
    LocationName repl = new LocationName("15 Francis Desk", "Information Desk", "INFO");

    Assertions.assertEquals(repl, locDao.updateRow(locName, repl));
  }

  @Test
  public void addRow() {
    LocationNameDao locDao = new LocationNameDao();
    LocationName locName = new LocationName("15 Francis Info Center", "Information Center", "INFO");

    Assertions.assertEquals(locName, locDao.addRow(locName));
  }

  @Test
  public void deleteRow() {
    LocationNameDao locDao = new LocationNameDao();
    LocationName locName = new LocationName("15 Francis Desk", "Information Desk", "INFO");

    Assertions.assertEquals(locName, locDao.deleteRow(locName));
  }

  @Test
  public void importCSV() {
    LocationNameDao locationDao = new LocationNameDao();
    Assertions.assertEquals(
        true,
        locationDao.importCSV(
            "C:\\Users\\nicky\\Documents\\Iteration-1\\src\\main\\resources\\edu\\wpi\\teamc\\LocationName.csv"));
  }

  @Test
  public void exportCSV() throws IOException {
    LocationNameDao locationDao = new LocationNameDao();
    Assertions.assertEquals(
        true,
        locationDao.exportCSV(
            "C:\\Users\\nicky\\Documents\\Iteration-1\\src\\main\\resources\\edu\\wpi\\teamc\\locationNameExport.csv"));
  }
}
