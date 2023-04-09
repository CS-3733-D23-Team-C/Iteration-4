package edu.wpi.teamc.dao.map;

import edu.wpi.teamc.dao.IDao;
import java.util.ArrayList;
import java.util.List;

public class EdgeDao implements IDao<Edge> {
    public List<Edge> fetchAllObjects() {
        return new ArrayList<>();
    }

    public int updateDBrow(Edge orm) {
        return 0;
    }

    public int addRow(Edge orm) {
        return 0;
    }

    public int deleteRow(Edge orm) {
        return 0;
    }
}
