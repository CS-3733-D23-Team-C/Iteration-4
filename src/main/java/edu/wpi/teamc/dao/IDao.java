package edu.wpi.teamc.dao;

import java.util.List;

public interface IDao<T, S> {
  List<T> fetchAllObjects();

  T updateRow(T type, T type2);

  T addRow(T type);

  T deleteRow(T type);

  T fetchObject(S key);
}
