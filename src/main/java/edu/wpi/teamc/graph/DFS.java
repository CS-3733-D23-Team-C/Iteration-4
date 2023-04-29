package edu.wpi.teamc.graph;

import java.util.List;

public class DFS extends Graph implements IAlgorithm {
  public List<GraphNode> getDirections(GraphNode src, GraphNode dest) {
    DFSrecur(src);

    return getPath(dest);
  }

  public void DFSrecur(GraphNode currNode) {
    settled.add(currNode);

    for (GraphNode neighbor : getNeighbors(currNode)) {
      nodeBefore.put(neighbor, currNode);
      if (!settled.contains(neighbor)) {
        DFSrecur(neighbor);
      }
    }
  }
}
