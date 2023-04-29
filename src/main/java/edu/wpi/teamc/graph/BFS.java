package edu.wpi.teamc.graph;

import java.util.LinkedList;
import java.util.List;

public class BFS extends Graph implements IAlgorithm {
  public List<GraphNode> getDirections(GraphNode src, GraphNode dest) {
    LinkedList<GraphNode> queue = new LinkedList<>();
    queue.add(src);
    settled.add(src);

    while (!queue.isEmpty()) {
      GraphNode node = queue.poll();

      for (GraphNode neighbor : getNeighbors(node)) {
        nodeBefore.put(neighbor, node);
        settled.add(neighbor);
        queue.add(neighbor);
      }
    }

    return getPath(dest);
  }
}
