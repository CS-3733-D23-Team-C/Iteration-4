package edu.wpi.teamc.dao.map;

import lombok.Getter;

@Getter
public class Edge extends MapObject {
  private Node startNode;
  private Node endNode;

  public Edge(Node startNode, Node endNode) {
    this.startNode = startNode;
    this.endNode = endNode;
  }

  @Override
  public void add(Object type) {}

  @Override
  public void delete(Object type) {}

  @Override
  public void update(Object type) {}
}
