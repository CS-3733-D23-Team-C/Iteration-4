package edu.wpi.teamc.dao.users;

import edu.wpi.teamc.dao.DBConnection;
import edu.wpi.teamc.dao.IDao;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class EmployeeUserDao implements IDao<EmployeeUser, Integer> {

  public List<EmployeeUser> fetchAllObjects() {
    List<EmployeeUser> returnList = new ArrayList<>();
    DBConnection db = new DBConnection();
    try {
      Statement stmt = db.getConnection().createStatement();
      // Table Name
      String table = "\"users\".\"employee\"";
      // Query
      String query = "SELECT * FROM " + table;

      ResultSet rs = stmt.executeQuery(query);

      while (rs.next()) {
        // Get all the data from the table
        int id = rs.getInt("id");
        String userName = rs.getString("username");
        String name = rs.getString("name");
        String department = rs.getString("department");
        String position = rs.getString("position");
        EmployeeUser employee = new EmployeeUser(id, userName, name, department, position);
        returnList.add(employee);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return returnList;
  }

  public EmployeeUser addRow(EmployeeUser orm) {
    DBConnection db = new DBConnection();
    try {
      String query =
          "INSERT INTO \"users\".\"employee\" (id, username, name, department, position) VALUES (?,?,?,?,?)";
      PreparedStatement ps =
          db.getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

      ps.setInt(1, orm.getId());
      ps.setString(2, orm.getUsername());
      ps.setString(3, orm.getName());
      ps.setString(4, orm.getDepartment());
      ps.setString(5, orm.getPosition());
      ps.executeUpdate();

      ResultSet rs = ps.getGeneratedKeys();
      rs.next();
      int id = rs.getInt("id");
      orm =
          new EmployeeUser(
              id, orm.getName(), orm.getUsername(), orm.getDepartment(), orm.getPosition());
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return orm;
  }

  public EmployeeUser updateRow(EmployeeUser orm, EmployeeUser repl) {
    DBConnection db = new DBConnection();
    try {
      // table names
      String table = "\"users\".\"employee\"";
      // queries
      String query =
          "UPDATE "
              + table
              + " SET "
              + "name = ?, "
              + "department = ?, "
              + "position = ? "
              + " WHERE empid = ?";

      PreparedStatement ps = db.getConnection().prepareStatement(query);
      ps.setString(1, repl.getName());
      ps.setString(2, repl.getDepartment());
      ps.setString(3, repl.getPosition());
      ps.setInt(4, orm.getId());
      ps.execute();
      db.closeConnection();
    } catch (Exception e) {
      e.printStackTrace();
    }
    db.closeConnection();
    return repl;
  }

  public EmployeeUser deleteRow(EmployeeUser orm) {
    DBConnection db = new DBConnection();
    try {
      Statement stmtNode = db.getConnection().createStatement();
      // table names
      String table = "\"user\".\"employee\"";
      // queries
      String query = "DELETE FROM " + table + " WHERE empID = " + orm.getId();
      stmtNode.executeUpdate(query);
    } catch (Exception e) {
      e.printStackTrace();
    }
    db.closeConnection();
    return orm;
  }

  @Override
  public EmployeeUser fetchObject(Integer key) throws SQLException {
    return null;
  }
}
