package edu.wpi.teamc.dao.map;

import edu.wpi.teamname.dao.IDao;
import edu.wpi.teamname.orm.Node;

import java.util.ArrayList;
import java.util.List;

public class NodeDao implements IDao<Node> {
    public List<Node> fetchAllObjects() {
        return new ArrayList<>();
    }

    public void updateDBrow(Node orm) {

    }

    public void addRow(Node orm) {

    }

    public void deleteRow(Node orm) {

    }
}
