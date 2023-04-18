package edu.wpi.teamc.dao.map;

import edu.wpi.teamc.dao.IOrm;
import lombok.Getter;

@Getter
public class Edge implements IOrm {
  private int startNode;
  private int endNode;

  public Edge(int startNode, int endNode) {
    this.startNode = startNode;
    this.endNode = endNode;
  }

  public int getStartNode() {
    return startNode;
  }

  public int getEndNode() {
    return endNode;
  }
}
