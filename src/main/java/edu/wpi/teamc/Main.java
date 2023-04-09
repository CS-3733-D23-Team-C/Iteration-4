package edu.wpi.teamc;

import edu.wpi.teamc.dao.map.NodeDAO;
import edu.wpi.teamc.dao.map.NodeDAOImpl;

public class Main {

  public static void main(String[] args) {
    NodeDAO nodeDAO = new NodeDAOImpl();
    // CApp.launch(CApp.class, args);
  }
}
