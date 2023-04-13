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
  private Set<GraphNode> settledNodes;
  private Set<GraphNode> unSettledNodes;
  private Map<GraphNode, GraphNode> predecessors;
  private Map<GraphNode, Double> distance;
  private PriorityQueue<GraphNode> priorityQueue;

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

  public void astar(GraphNode start, GraphNode end) {
    settledNodes = new HashSet<>();
    unSettledNodes = new HashSet<>();
    distance = new HashMap<>();
    predecessors = new HashMap<>();
    priorityQueue =
        new PriorityQueue<>(
            Comparator.comparingDouble(
                node ->
                    distance.getOrDefault(node, Double.POSITIVE_INFINITY) + node.getHeuristic()));

    for (GraphNode node : nodes.values()) {
      if (!node.equals(end)) {
        node.setHeuristic(end);
      }
    }
    distance.put(start, 0.0);
    unSettledNodes.add(start);
    priorityQueue.add(start);

    while (!priorityQueue.isEmpty()) {
      GraphNode node = priorityQueue.poll();
      unSettledNodes.remove(node);
      settledNodes.add(node);
      findMinimalDistances(node);
    }
  }

  private void findMinimalDistances(GraphNode node) {
    List<GraphNode> adjacentNodes = getNeighbors(node);

    for (GraphNode target : adjacentNodes) {
      double edgeDistance = getDistance(node, target);
      double heuristicDistance = target.getHeuristic();
      double totalDistance =
          distance.getOrDefault(node, Double.POSITIVE_INFINITY) + edgeDistance + heuristicDistance;

      if (totalDistance < distance.getOrDefault(target, Double.POSITIVE_INFINITY)) {
        distance.put(target, totalDistance);
        predecessors.put(target, node);

        if (!unSettledNodes.contains(target)) {
          unSettledNodes.add(target);
          priorityQueue.add(target);
        }
      }
    }
  }

  private List<GraphNode> getNeighbors(GraphNode node) {
    List<GraphNode> neighbors = new ArrayList<>();

    for (GraphEdge edge : node.getGraphEdges()) {
      if (edge.getSrc().equals(node) && !isSettled(edge.getDest())) {
        neighbors.add(edge.getDest());
      }
    }

    return neighbors;
  }

  private double getDistance(GraphNode node, GraphNode target) {
    for (GraphEdge edge : node.getGraphEdges()) {
      if (edge.getSrc().equals(node) && edge.getDest().equals(target)) {
        return edge.getWeight();
      }
    }

    throw new RuntimeException("Error: Unable to find distance between nodes.");
  }

  private boolean isSettled(GraphNode node) {
    return settledNodes.contains(node);
  }

  public List<GraphNode> getPath(GraphNode target) {
    List<GraphNode> path = new ArrayList<>();

    for (GraphNode node = target; node != null; node = predecessors.get(node)) {
      path.add(node);
    }

    Collections.reverse(path);

    return path;
  }

  /**
   * Returns a list of directions from start to end
   *
   * @param start - start node
   * @param end - end node
   * @return list of directions
   */
  public List<GraphNode> getDirections_Astar(GraphNode start, GraphNode end) {
    // implement a* algorithm for pathfinding from start to end
    PriorityQueue<GraphEdge> open = new PriorityQueue<>();
    LinkedList<GraphNode> closed = new LinkedList<>();

    // set heuristic vals for all immediate edges
    for (GraphEdge edge : start.getGraphEdges()) {
      edge.setHeuristic(end);
    }

    // add all immediate edges
    open.addAll(start.getGraphEdges());

    while (!open.isEmpty()) {
      // pick the best edge
      GraphEdge current = open.poll();

      // check if the current edge would reach the dest
      if (end.equals(current.getDest())) {
        closed.add(current.getDest());
        return closed;
      }

      // check edges of endNode of current edge
      for (GraphEdge neighbor : nodes.get(current.getEndNode()).getGraphEdges()) {
        // if they haven't been added yet
        if (!closed.contains(neighbor.getSrc()) && !open.contains(neighbor)) {
          neighbor.setHeuristic(end);
          open.add(neighbor);
        }
      }
      closed.add(current.getDest());
    }
    return closed;
  }

  public GraphNode getNode(int nodeID) {
    return nodes.get(nodeID);
  }
}
