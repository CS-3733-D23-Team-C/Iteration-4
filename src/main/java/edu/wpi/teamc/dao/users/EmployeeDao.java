package edu.wpi.teamc.dao.users;

import edu.wpi.teamc.dao.DBConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDao {

  public List<Employee> fetchAllObjects() {
    List<Employee> returnList = new ArrayList<>();
    DBConnection db = new DBConnection();
    try {
      Statement stmt = db.getConnection().createStatement();
      // Table Name
      String table = "\"ServiceRequests\".\"Employee\"";
      // Query
      String query = "SELECT * FROM " + table;

      ResultSet rs = stmt.executeQuery(query);

      while (rs.next()) {
        // Get all the data from the table
        int empid = rs.getInt("empid");
        String name = rs.getString("name");
        String department = rs.getString("department");
        String position = rs.getString("position");

        Employee employee = new Employee(empid, name, department, position);
        returnList.add(employee);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return returnList;
  }

  public Employee addRow(Employee orm) {
    DBConnection db = new DBConnection();
    try {
      String query =
          "INSERT INTO \"ServiceRequests\".\"Employee\" (name ,department, position) VALUES (?,?,?)";
      PreparedStatement ps =
          db.getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

      ps.setString(1, orm.getName());
      ps.setString(2, orm.getDepartment());
      ps.setString(3, orm.getPosition());
      ps.executeUpdate();

      ResultSet rs = ps.getGeneratedKeys();
      rs.next();
      int empID = rs.getInt("empid");
      orm = new Employee(empID, orm.getName(), orm.getDepartment(), orm.getPosition());
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return orm;
  }

  public Employee updateRow(Employee orm, Employee repl) {
    DBConnection db = new DBConnection();
    try {
      // table names
      String table = "\"ServiceRequests\".\"Employee\"";
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

  public Employee deleteRow(Employee orm) {
    DBConnection db = new DBConnection();
    try {
      Statement stmtNode = db.getConnection().createStatement();
      // table names
      String table = "\"ServiceRequests\".\"Employee\"";
      // queries
      String query = "DELETE FROM " + table + " WHERE empID = " + orm.getId();
      stmtNode.executeUpdate(query);
    } catch (Exception e) {
      e.printStackTrace();
    }
    db.closeConnection();
    return orm;
  }
}
