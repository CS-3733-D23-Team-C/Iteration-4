package edu.wpi.teamc.graph;

import edu.wpi.teamc.dao.IDao;
import edu.wpi.teamc.dao.map.Edge;
import edu.wpi.teamc.dao.map.EdgeDao;
import edu.wpi.teamc.dao.map.Node;
import edu.wpi.teamc.dao.map.NodeDao;
import java.sql.*;
import java.util.*;

public class Graph {
  protected Map<Integer, GraphNode> nodes = new HashMap<>();
  protected Set<GraphNode> settled = new HashSet<>();
  protected Set<GraphNode> unsettled = new HashSet<>();
  protected Map<GraphNode, GraphNode> nodeBefore = new HashMap<>();
  protected Map<GraphNode, Double> distance = new HashMap<>();
  protected PriorityQueue<GraphNode> pq;
  protected final double DIST_DEFAULT = Double.POSITIVE_INFINITY;
  private IAlgorithm algo;

  /** Empty Constructor for Graph */
  public Graph() {}

  public Graph(String algoChoice) {
    if (algoChoice.equals("A*")) {
      algo = new AStar();
    } else if (algoChoice.equals("BFS")) {
      algo = new BFS();
    } else {
      algo = new DFS();
    }
  }

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
  public List<GraphNode> getPathway(GraphNode src, GraphNode dest) {
    return algo.getDirections(src, dest);
  }

  /**
   * Get the neighboring nodes of a node, if they have not been settled
   *
   * @param node
   * @return
   */
  protected List<GraphNode> getNeighbors(GraphNode node) {
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
  protected double getDistance(GraphNode node, GraphNode target) {
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
  protected List<GraphNode> getPath(GraphNode target) {
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
