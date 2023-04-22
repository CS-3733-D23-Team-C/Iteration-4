package edu.wpi.teamc.graph;

import edu.wpi.teamc.dao.IDao;
import edu.wpi.teamc.dao.map.*;
import java.sql.*;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Graph {
  protected Map<Integer, GraphNode> nodes = new HashMap<>();
  protected Set<GraphNode> settled = new HashSet<>();
  protected Set<GraphNode> unsettled = new HashSet<>();
  protected Map<GraphNode, GraphNode> nodeBefore = new HashMap<>();
  protected Map<GraphNode, Double> distance = new HashMap<>();
  protected Map<GraphNode, String> nodeType = new HashMap<>();
  protected Map<Integer, String> nodeIDtoLongName = new HashMap<>();
  protected HashMap<String, Integer> longNameToNodeID = new HashMap<>();
  protected Map<Integer, Date> nodeIDtoLastDate = new HashMap<>();
  protected Map<String, String> longNameToNodeType = new HashMap<>();
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
    } else if (algoChoice.equals("DFS")) {
      algo = new DFS();
    } else {
      algo = new Dijkstra();
    }
  }

  public void syncWithDB(String date) {
    IDao<Node, Integer> nodeDao = new NodeDao();
    IDao<Edge, Edge> edgeDao = new EdgeDao();
    IDao<Move, Move> moveDao = new MoveDao();
    IDao<LocationName, String> locDao = new LocationNameDao();

    List<Node> nodes = new LinkedList<>();
    List<Edge> edges = new LinkedList<>();
    List<Move> moves = new LinkedList<>();
    List<LocationName> locs = new LinkedList<>();
    try {
      nodes = nodeDao.fetchAllObjects();
      edges = edgeDao.fetchAllObjects();
      moves = moveDao.fetchAllObjects();
      locs = locDao.fetchAllObjects();
    } catch (Exception e) {
      // error
    }

    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    java.util.Date locDate;
    try {
      locDate = format.parse(date);
    } catch (ParseException e) {
      throw new RuntimeException(e);
    }

    Date dateObj = new Date(locDate.getTime());

    for (Move move : moves) {
      // store the move for the desired date
      if (move.getDate().equals(dateObj)) {
        nodeIDtoLastDate.put(move.getNodeID(), move.getDate());
        nodeIDtoLongName.put(move.getNodeID(), move.getLongName());
        longNameToNodeID.put(move.getLongName(), move.getNodeID());
      } else if (move.getDate().compareTo(dateObj) < 0) {
        nodeIDtoLastDate.putIfAbsent(move.getNodeID(), move.getDate());
        nodeIDtoLongName.putIfAbsent(move.getNodeID(), move.getLongName());
        longNameToNodeID.putIfAbsent(move.getLongName(), move.getNodeID());

        if (nodeIDtoLastDate.get(move.getNodeID()).compareTo(move.getDate()) < 0) {
          nodeIDtoLastDate.put(move.getNodeID(), move.getDate());
          nodeIDtoLongName.put(move.getNodeID(), move.getLongName());
          longNameToNodeID.put(move.getLongName(), move.getNodeID());
        }
      }
    }

    for (LocationName loc : locs) {
      longNameToNodeType.put(loc.getLongName(), loc.getNodeType());
    }

    for (Node node : nodes) {
      String longName = nodeIDtoLongName.get(node.getNodeID());
      String nodeType = longNameToNodeType.get(longName);

      addNode(
          new GraphNode(
              node.getNodeID(),
              node.getXCoord(),
              node.getYCoord(),
              node.getFloor(),
              node.getBuilding(),
              nodeType));
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

  public int getNodeIDfromLongName(String longName) {
    return longNameToNodeID.get(longName);
  }
}
