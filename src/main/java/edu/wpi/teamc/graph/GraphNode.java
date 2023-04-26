package edu.wpi.teamc.graph;

import static java.lang.Math.abs;

import edu.wpi.teamc.dao.map.NODE_STATUS;
import edu.wpi.teamc.dao.map.Node;
import java.util.LinkedList;
import java.util.List;
import lombok.Getter;

@Getter
public class GraphNode extends Node {
  private List<GraphEdge> graphEdges;
  private String nodeType;
  private double heuristic;

  /**
   * Constructor for Node
   *
   * @param nodeID - ID of the node ex: CCONF001L1
   * @param xCoord - x coordinate of the node ex: 2255
   * @param yCoord - y coordinate of the node ex: 849
   * @param floor - floor of the node ex: L1
   * @param building - building of the node ex: CCONF
   */
  public GraphNode(
      int nodeID,
      int xCoord,
      int yCoord,
      String floor,
      String building,
      String nodeType,
      NODE_STATUS status) {
    super(nodeID, xCoord, yCoord, floor, building, status);
    this.graphEdges = new LinkedList<>();
    this.nodeType = nodeType;
  }

  public void setHeuristic(GraphNode targetNode) {
    this.heuristic =
        abs(getXCoord() - targetNode.getXCoord()) + abs(getYCoord() - targetNode.getYCoord());

    if (!this.getFloor().equals(targetNode.getFloor())) {
      if (nodeType.equals("ELEV")) {
        this.heuristic -= 10;
      } else if (nodeType.equals("STAI")) {
        this.heuristic += 10;
      }
    }
  }
}
