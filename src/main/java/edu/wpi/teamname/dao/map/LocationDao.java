package edu.wpi.teamname.dao.map;

import edu.wpi.teamname.dao.IDao;
import edu.wpi.teamname.orm.LocationName;
import edu.wpi.teamname.orm.Node;

import java.util.ArrayList;
import java.util.List;

public class LocationDao implements IDao<LocationName> {
    public List<LocationName> fetchAllObjects() {
        return new ArrayList<>();
    }

    public void updateDBrow(LocationName orm) {

    }

    public void addRow(LocationName orm) {

    }

    public void deleteRow(LocationName orm) {

    }
}
