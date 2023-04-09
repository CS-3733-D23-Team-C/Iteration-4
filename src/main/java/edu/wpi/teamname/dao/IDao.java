package edu.wpi.teamname.dao;

import java.util.List;

public interface IDao<T> {
    List<T> fetchAllObjects();
    void updateDBrow(T orm);
    void deleteRow(T orm);
    void addRow(T orm);
}
