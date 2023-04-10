package edu.wpi.teamc.dao.map;

import lombok.Getter;

@Getter
public class Edge {
  private int startNode;
  private int endNode;

  public Edge(int startNode, int endNode) {
    this.startNode = startNode;
    this.endNode = endNode;
  }
}
