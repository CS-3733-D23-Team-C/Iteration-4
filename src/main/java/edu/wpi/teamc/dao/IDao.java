package edu.wpi.teamc.dao;

import edu.wpi.teamc.dao.requests.ConferenceRoomRequest;

import java.sql.SQLException;
import java.util.List;

public interface IDao<T> {
  List<T> fetchAllObjects() throws SQLException;

  int updateRow(T type, T type2) throws SQLException;

  int updateDBRow(T type) throws SQLException;

  int addRow(T type);

  int deleteRow(T type) throws SQLException;
}
