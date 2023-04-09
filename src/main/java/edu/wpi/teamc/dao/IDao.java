package edu.wpi.teamc.dao;

import java.sql.SQLException;
import java.util.List;

public interface IDao<T> {
  List<T> getAll() throws SQLException;

  int insert(T type) throws SQLException;

  int delete(T type);

  int update(T type) throws SQLException;
}
