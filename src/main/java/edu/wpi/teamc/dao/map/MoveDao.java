package edu.wpi.teamc.dao.map;

import edu.wpi.teamc.dao.IDao;
import java.util.ArrayList;
import java.util.List;

public class MoveDao implements IDao<Move> {
  public List<Move> fetchAllObjects() {
    return new ArrayList<>();
  }

  public int updateRow(Move orm, Move repl) {
    return 0;
  }

  public int addRow(Move orm) {
    return 0;
  }

  public int deleteRow(Move orm) {
    return 0;
  }
}
