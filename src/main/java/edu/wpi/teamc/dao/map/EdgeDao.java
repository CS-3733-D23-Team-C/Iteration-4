package edu.wpi.teamc.dao.map;

import edu.wpi.teamc.dao.DBConnection;
import edu.wpi.teamc.dao.IDao;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class EdgeDao implements IDao<Edge> {
  public List<Edge> fetchAllObjects() {
    List<Edge> databaseEdgeList = new ArrayList<>();
    DBConnection db = new DBConnection();
    try {
      Statement stmtEdge = db.getConnection().createStatement();
      // table names
      String EDGE = "\"hospitalNode\".edge";
      // queries
      String queryDisplayEdges = "SELECT * FROM " + EDGE;

      ResultSet rsEdges = stmtEdge.executeQuery(queryDisplayEdges);
      while (rsEdges.next()) {
        String startNodeString = rsEdges.getString("startNode");
        String endNodeString = rsEdges.getString("endNode");
        Edge edge = new Edge(startNodeString, endNodeString);
        databaseEdgeList.add(edge);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    db.closeConnection();
    return databaseEdgeList;
  }

  public int updateRow(Edge orm, Edge repl) {
    return 0;
  }

  public int addRow(Edge orm) {
    return 0;
  }

  public int deleteRow(Edge orm) {
    return 0;
  }
}
