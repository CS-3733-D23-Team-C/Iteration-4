package edu.wpi.teamc.graph;

import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class Dijkstra extends Graph implements IAlgorithm {
  @Override
  public List<GraphNode> getDirections(GraphNode src, GraphNode dest) {
    // organize priority queue based on weight
    // distance defaults to max (if it hasn't been visited yet)
    pq =
        new PriorityQueue<>(
            Comparator.comparingDouble(node -> distance.getOrDefault(node, DIST_DEFAULT)));

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
    double edgeDist, total;

    for (GraphNode neighbor : adjacentNodes) {
      edgeDist = getDistance(node, neighbor);
      total = distance.getOrDefault(node, DIST_DEFAULT) + edgeDist;

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
}
