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
  private Set<GraphNode> settled = new HashSet<>();
  private Set<GraphNode> unsettled = new HashSet<>();
  private Map<GraphNode, GraphNode> nodeBefore = new HashMap<>();
  private Map<GraphNode, Double> distance = new HashMap<>();
  private PriorityQueue<GraphNode> pq;
  private final double DIST_DEFAULT = Double.POSITIVE_INFINITY;

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
   * Get the path between a src and dest node using astar
   *
   * @param src source node
   * @param dest destination node
   */
  public List<GraphNode> getDirectionsAstar(GraphNode src, GraphNode dest) {
    // organize priority queue based on weight
    // distance defaults to max (if it hasn't been visited yet)
    pq =
        new PriorityQueue<>(
            Comparator.comparingDouble(
                node -> distance.getOrDefault(node, DIST_DEFAULT) + node.getHeuristic()));

    // set all the heuristic vals based on dest node location
    for (GraphNode node : nodes.values()) {
      if (!node.equals(dest)) {
        node.setHeuristic(dest);
      }
    }

    // src dist is 0
    distance.put(src, 0.0);
    // add src node to unsettled and queue
    unsettled.add(src);
    pq.add(src);

    while (!pq.isEmpty()) {
      GraphNode node = pq.poll();
      unsettled.remove(node);
      settled.add(node);
      findMinDist(node);
    }

    return getPath(dest);
  }

  /**
   * A method to find the minimum distance between a given node and its neighbors
   *
   * @param node
   */
  private void findMinDist(GraphNode node) {
    List<GraphNode> adjacentNodes = getNeighbors(node);
    double edgeDist, heuristic, total;

    for (GraphNode neighbor : adjacentNodes) {
      edgeDist = getDistance(node, neighbor);
      heuristic = neighbor.getHeuristic();
      total = distance.getOrDefault(node, DIST_DEFAULT) + edgeDist + heuristic;

      // if total is less than what is currently stored, or it hasn't been reached yet
      if (total < distance.getOrDefault(neighbor, DIST_DEFAULT)) {
        // put new total dist value in
        // put given node as the previous node in the path to neighbor
        distance.put(neighbor, total);
        nodeBefore.put(neighbor, node);

        // add to pq and unsettled if it's not in unsettled
        if (!unsettled.contains(neighbor)) {
          unsettled.add(neighbor);
          pq.add(neighbor);
        }
      }
    }
  }

  /**
   * Get the neighboring nodes of a node, if they have not been settled
   *
   * @param node
   * @return
   */
  private List<GraphNode> getNeighbors(GraphNode node) {
    List<GraphNode> neighbors = new ArrayList<>();

    for (GraphEdge edge : node.getGraphEdges()) {
      // check to make sure dest node isn't in settled, don't want to go to a settled node
      if (!settled.contains(edge.getDest())) {
        neighbors.add(edge.getDest());
      }
    }

    return neighbors;
  }

  /**
   * Get the distance between two given nodes, if an edge between them exists
   *
   * @param node source node
   * @param target dest node
   * @return the distance
   */
  private double getDistance(GraphNode node, GraphNode target) {
    for (GraphEdge edge : node.getGraphEdges()) {
      if (edge.getSrc().equals(node) && edge.getDest().equals(target)) {
        return edge.getWeight();
      }
    }

    return -1;
  }

  /**
   * Return the path to the target node
   *
   * @param target
   * @return the path
   */
  public List<GraphNode> getPath(GraphNode target) {
    List<GraphNode> path = new LinkedList<>();
    GraphNode node = target;

    // keep going down until there is no nodeBefore
    while (node != null) {
      path.add(node);
      node = nodeBefore.get(node);
    }

    // reverse the list to go in correct direction
    Collections.reverse(path);

    return path;
  }

  public GraphNode getNode(int nodeID) {
    return nodes.get(nodeID);
  }
}
