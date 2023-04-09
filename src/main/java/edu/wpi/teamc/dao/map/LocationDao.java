package edu.wpi.teamc.dao.map;

import edu.wpi.teamc.dao.IDao;
import java.util.ArrayList;
import java.util.List;

public class LocationDao implements IDao<LocationName> {
    public List<LocationName> fetchAllObjects() {
        return new ArrayList<>();
    }

    public int updateDBrow(LocationName orm) {
        return 0;
    }

    public int addRow(LocationName orm) {
        return 0;
    }

    public int deleteRow(LocationName orm) {
        return 0;
    }
}
