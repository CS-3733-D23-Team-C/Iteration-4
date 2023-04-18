package edu.wpi.teamc.dao.map;

import java.io.IOException;
import java.sql.Date;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MoveDaoTest {
  @Test
  public void fetchAllObjects() {
    MoveDao moveDao = new MoveDao();
    List<Move> moves = moveDao.fetchAllObjects();

    Assertions.assertNotNull(moves);
  }

  @Test
  public void updateRow() {
    MoveDao moveDao = new MoveDao();
    Move moveOrm = new Move(100, "Hallway 10 Floor L1", new Date(2023 - 1900, 4 - 1, 9));
    Move moveRepl = new Move(100, "Hallway", new Date(2023 - 1900, 4 - 1, 9));

    Assertions.assertEquals(moveRepl, moveDao.updateRow(moveOrm, moveRepl));
  }

  @Test
  public void addRow() {
    MoveDao moveDao = new MoveDao();
    Move moveOrm = new Move(100, "Hallway 4000", new Date(2023 - 1900, 4 - 1, 10));

    Assertions.assertEquals(moveOrm, moveDao.addRow(moveOrm));
  }

  @Test
  public void deleteRow() {
    MoveDao moveDao = new MoveDao();
    Move moveOrm = new Move(100, "Hallway 10 Floor L1", new Date(2023 - 1900, 4 - 1, 9));

    Assertions.assertEquals(moveOrm, moveDao.deleteRow(moveOrm));
  }

  @Test
  public void deleteRow2() {
    MoveDao moveDao = new MoveDao();
    Move moveOrm = new Move(100, "Hallway 10 Floor L1", new Date(2023 - 1900, 4 - 1, 9));

    Assertions.assertEquals(moveOrm, moveDao.deleteRow(5));
  }

  @Test
  public void importCSV() {
    MoveDao moveDao = new MoveDao();
    Assertions.assertEquals(
        true,
        moveDao.importCSV(
            "C:\\Users\\nicky\\Documents\\Iteration-1\\src\\main\\resources\\edu\\wpi\\teamc\\move.csv"));
  }

  @Test
  public void exportCSV() throws IOException {
    MoveDao moveDao = new MoveDao();
    Assertions.assertEquals(
        true,
        moveDao.exportCSV(
            "C:\\Users\\nicky\\Documents\\Iteration-1\\src\\main\\resources\\edu\\wpi\\teamc\\moveExport.csv"));
  }
}
