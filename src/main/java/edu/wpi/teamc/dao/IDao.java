package edu.wpi.teamc.dao;

import java.sql.SQLException;
import java.util.List;

public interface IDao<T> {
  List<T> fetchAllObjects() throws SQLException;

  int updateDBrow(T type) throws SQLException;

  int addRow(T type);

  int deleteRow(T type) throws SQLException;
}
