package edu.wpi.teamc.dao.map;

import lombok.Getter;

@Getter
public class Edge {
  private String startNode;
  private String endNode;

  public Edge(String startNode, String endNode) {
    this.startNode = startNode;
    this.endNode = endNode;
  }
}
