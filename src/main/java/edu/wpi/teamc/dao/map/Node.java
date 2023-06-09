package edu.wpi.teamc.dao.map;

import edu.wpi.teamc.dao.IOrm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Node implements IOrm {
  private int nodeID;
  private int xCoord;
  private int yCoord;
  private String floor;
  private String building;
  private NODE_STATUS status;

  /**
   * Constructor for Node
   *
   * @param nodeID - ID of the node ex: CCONF001L1
   * @param xCoord - x coordinate of the node ex: 2255
   * @param yCoord - y coordinate of the node ex: 849
   * @param floor - floor of the node ex: L1
   * @param building - building of the node ex: CCONF
   */
  public Node(int nodeID, int xCoord, int yCoord, String floor, String building) {
    this.nodeID = nodeID;
    this.xCoord = xCoord;
    this.yCoord = yCoord;
    this.floor = floor;
    this.building = building;
  }

  public Node(
      int nodeID, int xCoord, int yCoord, String floor, String building, NODE_STATUS status) {
    this.nodeID = nodeID;
    this.xCoord = xCoord;
    this.yCoord = yCoord;
    this.floor = floor;
    this.building = building;
    this.status = status;
  }

  public Node(int xCoord, int yCoord, String floor, String building) {
    this.xCoord = xCoord;
    this.yCoord = yCoord;
    this.floor = floor;
    this.building = building;
  }

  public Node() {}

  @Override
  public String toString() {
    return Integer.toString(nodeID);
  }
}
