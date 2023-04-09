package edu.wpi.teamc.dao.map;

import edu.wpi.teamname.dao.IDao;
import edu.wpi.teamname.orm.Move;

import java.util.ArrayList;
import java.util.List;

public class MoveDao implements IDao<Move> {
    public List<Move> fetchAllObjects() {
        return new ArrayList<>();
    }

    public void updateDBrow(Move orm) {

    }

    public void addRow(Move orm) {

    }

    public void deleteRow(Move orm) {

    }
}
