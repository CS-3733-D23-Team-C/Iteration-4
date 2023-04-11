package edu.wpi.teamc.dao;

import java.sql.SQLException;
import java.util.List;

public interface IDao<T> {
  List<T> fetchAllObjects() throws SQLException;

  T updateRow(T type, T type2) throws SQLException;

  static T addRow(T type);

  T deleteRow(T type) throws SQLException;
}
