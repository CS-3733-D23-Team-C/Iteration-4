package edu.wpi.teamc.graph;

import java.util.LinkedList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class GraphEdgeTest {

  @Test
  public void testEquals() {
    GraphNode node1 = new GraphNode(1, 10, 10, "L1", "BTM");
    GraphNode node2 = new GraphNode(1, 20, 20, "L1", "BTM");
    GraphEdge edge1 = new GraphEdge("1_2", node1, node2);
    GraphEdge edge2 = new GraphEdge("2_1", node1, node2);
    LinkedList<GraphEdge> edges = new LinkedList<>();
    edges.add(edge1);
    Assertions.assertTrue(edges.contains(edge2));
  }
}
