package edu.wpi.teamc.dao.map;

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

    Assertions.assertEquals(1, moveDao.updateRow(moveOrm, moveRepl));
  }

  @Test
  public void addRow() {
    MoveDao moveDao = new MoveDao();
    Move moveOrm = new Move(100, "Hallway 4000", new Date(2023 - 1900, 4 - 1, 10));

    Assertions.assertEquals(1, moveDao.addRow(moveOrm));
  }

  @Test
  public void deleteRow() {
    MoveDao moveDao = new MoveDao();
    Move moveOrm = new Move(100, "Hallway 10 Floor L1", new Date(2023 - 1900, 4 - 1, 9));

    Assertions.assertEquals(1, moveDao.deleteRow(moveOrm));
  }
}
