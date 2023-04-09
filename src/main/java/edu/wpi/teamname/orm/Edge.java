package edu.wpi.teamname.orm;

import lombok.Getter;

@Getter
public class Edge {
  private Node startNode;
  private Node endNode;

  public Edge(Node startNode, Node endNode) {
    this.startNode = startNode;
    this.endNode = endNode;
  }
}
