package edu.wpi.teamc.dao;

import java.sql.SQLException;
import java.util.List;

public interface IDao<T, S> {
  List<T> fetchAllObjects() throws SQLException;

  T updateRow(T type, T type2) throws SQLException;

  T addRow(T type);

  T deleteRow(T type) throws SQLException;

  T fetchObject(S key) throws SQLException;
}