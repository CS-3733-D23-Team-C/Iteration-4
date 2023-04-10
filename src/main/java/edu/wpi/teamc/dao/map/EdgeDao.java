package edu.wpi.teamc.dao.map;

import edu.wpi.teamc.dao.DBConnection;
import edu.wpi.teamc.dao.IDao;
import java.sql.PreparedStatement;
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
        int startNode = rsEdges.getInt("startNode");
        int endNode = rsEdges.getInt("endNode");
        Edge edge = new Edge(startNode, endNode);
        databaseEdgeList.add(edge);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    db.closeConnection();
    return databaseEdgeList;
  }

  public int updateRow(Edge orm, Edge repl) {
    DBConnection db = new DBConnection();
    try {
      String EDGE = "\"hospitalNode\".edge";
      String queryUpdateEdgesDB =
          "UPDATE  "
              + EDGE
              + " SET \"startNode\"=?, \"endNode\"=? WHERE \"startNode\"=? AND \"endNode\"=?;";

      PreparedStatement ps = db.getConnection().prepareStatement(queryUpdateEdgesDB);

      ps.setInt(1, repl.getStartNode());
      ps.setInt(2, repl.getEndNode());
      ps.setInt(3, orm.getStartNode());
      ps.setInt(4, orm.getEndNode());

      ps.executeUpdate();
    } catch (Exception e) {
      e.printStackTrace();
    }
    db.closeConnection();
    return 1;
  }

  public int addRow(Edge orm) {
    DBConnection db = new DBConnection();
    try {
      String EDGE = "\"hospitalNode\".edge";
      String queryInsertEdgesDB = "INSERT INTO " + EDGE + " VALUES (?,?); ";

      PreparedStatement ps;
      ps = db.getConnection().prepareStatement(queryInsertEdgesDB);

      ps.setInt(1, orm.getStartNode());
      ps.setInt(2, orm.getEndNode());

      ps.executeUpdate();
    } catch (Exception e) {
      e.printStackTrace();
    }
    db.closeConnection();
    return 1;
  }

  public int deleteRow(Edge orm) {
    DBConnection db = new DBConnection();
    try {
      // table names
      String EDGE = "\"hospitalNode\".edge";
      // queries

      String queryInsertEdgesDB = "INSERT INTO " + EDGE + " VALUES (?,?); ";
      String queryDeleteEdgesDB =
          "DELETE FROM " + EDGE + " WHERE \"startNode\"=? AND \"endNode\"=?; ";

      PreparedStatement ps = db.getConnection().prepareStatement(queryDeleteEdgesDB);

      ps.setInt(1, orm.getStartNode());
      ps.setInt(2, orm.getEndNode());

      ps.executeUpdate();
    } catch (Exception e) {
      e.printStackTrace();
    }
    db.closeConnection();
    return 1;
  }
}
