package edu.wpi.teamc.dao.map;

import edu.wpi.teamc.dao.IDao;
import java.util.ArrayList;
import java.util.List;

public class NodeDao implements IDao<Node> {
  public List<Node> fetchAllObjects() {
    return new ArrayList<>();
  }

  public int updateRow(Node orm, Node repl) {
    return 0;
  }

  public int addRow(Node orm) {
    return 0;
  }

  public int deleteRow(Node orm) {
    return 0;
  }
}
