package edu.wpi.teamc.dao;

import java.sql.*;

public class DBConnection {
  private Connection connection;

  public DBConnection(Boolean isWPI) {
    if (isWPI) {
      getWPIConnection();
    } else {
      getAWSConnection();
    }
  }

  public void getWPIConnection() {
    try {
      // Load the PostgreSQL JDBC driver
      Class.forName("org.postgresql.Driver");
      // Establish the connection
      String url = "jdbc:postgresql://database.cs.wpi.edu/teamcdb";
      String user = "teamc";
      String password = "teamc30";
      connection = DriverManager.getConnection(url, user, password);
    } catch (ClassNotFoundException | SQLException e) {
      e.printStackTrace();
    }
  }

  public void getAWSConnection() {
    try {
      // Load the PostgreSQL JDBC driver
      Class.forName("org.postgresql.Driver");
      // Establish the connection
      String url = "jdbc:postgresql://teamcdb.c7x33sljy2ow.us-east-2.rds.amazonaws.com/teamcdb";
      String user = "teamcdb";
      String password = "teamcteamcteamc";
      connection = DriverManager.getConnection(url, user, password);
    } catch (ClassNotFoundException | SQLException e) {
      e.printStackTrace();
    }
  }

  public void closeConnection() {
    try {
      connection.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public Connection getConnection() {
    return connection;
  }
}
