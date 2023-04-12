package edu.wpi.teamc.graph;

import static java.lang.Math.abs;

import edu.wpi.teamc.dao.map.Edge;
import lombok.Getter;

@Getter
public class GraphEdge extends Edge implements Comparable<GraphEdge> {
  private String id;
  private GraphNode src;
  private GraphNode dest;
  private double weight;
  private double heuristic;

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

  /**
   * Use manhattan distance to set heuristic value
   *
   * @param targetNode Target destination
   */
  public void setHeuristic(GraphNode targetNode) {
    if (!targetNode.getBuilding().equals(dest.getFloor())) {
      this.heuristic =
          abs(dest.getXCoord() - targetNode.getXCoord())
              + abs(dest.getYCoord() - targetNode.getYCoord());
    } else {
      this.heuristic =
          Math.hypot(
              dest.getXCoord() - targetNode.getXCoord(), dest.getYCoord() - targetNode.getYCoord());
    }
  }

  /**
   * Override compareTo function to provide desired ordering when GraphEdges are inserted into a
   * priority queue
   *
   * @param edge the object to be compared.
   * @return int
   */
  @Override
  public int compareTo(GraphEdge edge) {
    return Double.compare(weight + heuristic, weight + edge.heuristic);
  }

  /**
   * Override equals to equate edges that travel between the same two nodes
   *
   * @param o GraphEdge object
   * @return boolean
   */
  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    } else {
      String[] id = ((GraphEdge) o).getId().split("_");
      String reverseID = id[1] + "_" + id[0];

      return reverseID.equals(this.getId());
    }
  }
}
