package edu.wpi.teamc.graph;

import edu.wpi.teamc.dao.IDao;
import edu.wpi.teamc.dao.map.Edge;
import edu.wpi.teamc.dao.map.EdgeDao;
import edu.wpi.teamc.dao.map.Node;
import edu.wpi.teamc.dao.map.NodeDao;
import java.sql.*;
import java.util.*;

public class Graph {
  private Map<Integer, GraphNode> nodes = new HashMap<>();

  /** Empty Constructor for Graph */
  public Graph() {}

  public void syncWithDB() {
    IDao<Node> nodeDao = new NodeDao();
    IDao<Edge> edgeDao = new EdgeDao();
    List<Node> nodes = new LinkedList<>();
    List<Edge> edges = new LinkedList<>();
    try {
      nodes = nodeDao.fetchAllObjects();
      edges = edgeDao.fetchAllObjects();
    } catch (Exception e) {
      // error
    }

    for (Node node : nodes) {
      addNode(
          new GraphNode(
              node.getNodeID(),
              node.getXCoord(),
              node.getYCoord(),
              node.getFloor(),
              node.getBuilding()));
    }
    for (Edge edge : edges) {
      GraphNode src = this.nodes.get(edge.getStartNode());
      GraphNode dest = this.nodes.get(edge.getEndNode());
      String origID = src.getNodeID() + "_" + dest.getNodeID();
      String reverseID = dest.getNodeID() + "_" + src.getNodeID();

      src.getGraphEdges().add(new GraphEdge(origID, src, dest));
      dest.getGraphEdges().add(new GraphEdge(reverseID, dest, src));
    }
  }

  /**
   * Adds a node to the map
   *
   * @param node - node to be added
   */
  public void addNode(GraphNode node) {
    // check if node already exists
    if (nodes.containsKey(node.getNodeID())) {
      System.out.println("Node already exists");
      return;
    } else {
      nodes.put(node.getNodeID(), node);
    }
  }

  /**
   * Returns a list of directions from start to end
   *
   * @param start - start node
   * @param end - end node
   * @return list of directions
   */
  public List<GraphEdge> getDirections_Astar(GraphNode start, GraphNode end) {
    // implement a* algorithm for pathfinding from start to end
    PriorityQueue<GraphEdge> open = new PriorityQueue<>();
    LinkedList<GraphEdge> closed = new LinkedList<>();

    // set heuristic vals for all immediate edges
    for (GraphEdge edge : start.getGraphEdges()) {
      edge.setHeuristic(end);
    }

    // add all immediate edges
    open.addAll(start.getGraphEdges());

    while (!open.isEmpty()) {
      // pick the best edge
      GraphEdge current = open.poll();
      // open.clear();

      // check if the current edge would reach the dest
      if (end.equals(current.getDest())) {
        closed.add(current);
        return closed;
      }

      // check edges of endNode of current edge
      for (GraphEdge neighbor : nodes.get(current.getEndNode()).getGraphEdges()) {
        // if they haven't been added yet
        if (!closed.contains(neighbor) && !open.contains(neighbor)) {
          neighbor.setHeuristic(end);
          open.add(neighbor);
        }
      }
      closed.add(current);
    }
    return closed;
  }

  public GraphNode getNode(int nodeID) {
    return nodes.get(nodeID);
  }
}
