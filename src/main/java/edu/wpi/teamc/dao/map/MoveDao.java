package edu.wpi.teamc.dao.map;

import edu.wpi.teamc.dao.DBConnection;
import edu.wpi.teamc.dao.IDao;
import java.sql.*;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MoveDao implements IDao<Move> {
  public List<Move> fetchAllObjects() {
    List<Move> databaseMoveList = new ArrayList<>();
    DBConnection db = new DBConnection();
    try {
      Statement stmtMove = db.getConnection().createStatement();
      // table names
      String MOVE = "\"hospitalNode\".\"move\"";
      // queries
      String queryDisplayMoves = "SELECT * FROM " + MOVE;

      ResultSet rsMoves = stmtMove.executeQuery(queryDisplayMoves);

      while (rsMoves.next()) {
        String nodeID = rsMoves.getString("nodeID");
        String longName = rsMoves.getString("longName");
        Date date = rsMoves.getDate("moveDate");
        databaseMoveList.add(new Move(nodeID, longName, date));
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

    db.closeConnection();
    return databaseMoveList;
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
