package edu.wpi.teamc.dao.map;

import edu.wpi.teamc.dao.IOrm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NodeStatus implements IOrm {
  private int nodeID;
  private NODE_STATUS status;

  /**
   * Constructor for Node
   *
   * @param nodeID - ID of the node ex: CCONF001L1
   * @param status - Status of the node ex: OPEN, CLOSED
   */
  public NodeStatus(int nodeID, NODE_STATUS status) {
    this.nodeID = nodeID;
    this.status = status;
  }

  public NodeStatus(NODE_STATUS status) {
    this.status = status;
  }

  public NodeStatus() {}

  @Override
  public String toString() {
    return Integer.toString(nodeID);
  }
}
