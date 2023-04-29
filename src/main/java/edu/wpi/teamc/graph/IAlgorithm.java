package edu.wpi.teamc.graph;

import java.util.List;

public interface IAlgorithm {
  List<GraphNode> getDirections(GraphNode src, GraphNode dest);
}
