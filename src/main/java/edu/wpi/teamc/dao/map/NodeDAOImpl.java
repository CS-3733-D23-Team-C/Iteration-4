package edu.wpi.teamc.dao.map;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NodeDAOImpl implements NodeDAO {
  public static List<Node> databaseNodeList = new ArrayList<Node>();

  @Override
  public List<Node> getAll() throws SQLException {

    return null;
  }

  public Node get(String NodeID) throws SQLException {

    return null;
  }

  @Override
  public int insert(Node type) throws SQLException {
    return 0;
  }

  @Override
  public int delete(Node type) {
    return 0;
  }

  @Override
  public int update(Node type) throws SQLException {
    return 0;
  }
}
