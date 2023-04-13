package edu.wpi.teamc.graph;

import static java.lang.Math.abs;

import edu.wpi.teamc.dao.map.Edge;
import lombok.Getter;

@Getter
public class GraphEdge extends Edge {
  private String id;
  private GraphNode src;
  private GraphNode dest;
  private double weight;

  public GraphEdge(String id, GraphNode startNode, GraphNode endNode) {
    super(startNode.getNodeID(), endNode.getNodeID());
    this.id = id;
    this.src = startNode;
    this.dest = endNode;
    this.weight =
        Math.hypot(
            abs(startNode.getXCoord() - endNode.getXCoord()),
            abs(startNode.getYCoord() - endNode.getYCoord()));
  }
}
