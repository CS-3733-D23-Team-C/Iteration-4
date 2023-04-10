package edu.wpi.teamc.dao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface IDao<T> {
  List<T> fetchAllObjects() throws SQLException;

  int updateRow(T type, T type2) throws SQLException;

  int addRow(T type);

  int deleteRow(T type) throws SQLException;
  boolean importCSV(String CSVfilepath);
  boolean exportCSV(String CSVfilepath) throws IOException;
}
