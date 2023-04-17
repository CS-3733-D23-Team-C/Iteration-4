package edu.wpi.teamc.dao.map;

import java.sql.Timestamp;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MapHistoryDaoTest {

  @Test
  public void fetchAllObjects() {}

  @Test
  public void updateRow() {}

  @Test
  public void addRow() {
    MapHistoryDao mapHistoryDao = new MapHistoryDao();
    MapHistory mapHistory =
        new MapHistory("action", "nodepk", "table", new Timestamp(System.currentTimeMillis()));
    Assertions.assertEquals(mapHistory, mapHistoryDao.addRow(mapHistory));
  }

  @Test
  public void deleteRow() {}
}
