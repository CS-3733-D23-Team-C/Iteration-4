package edu.wpi.teamc.controllers.pages.map.MapHelpers;

import edu.wpi.teamc.dao.map.Node;
import javafx.scene.shape.Circle;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MoveHelper {
  Node node;
  int nodesClicked;
  Circle circle;

  public MoveHelper() {}

  public MoveHelper(Node node) {
    this.node = node;
  }

  public MoveHelper(int nodesClicked) {
    this.nodesClicked = nodesClicked;
  }

  public MoveHelper(Circle circle) {
    this.circle = circle;
  }

  public MoveHelper(Node node, int nodesClicked) {
    this.node = node;
    this.nodesClicked = nodesClicked;
  }

  public MoveHelper(Node node, int nodesClicked, Circle circle) {
    this.node = node;
    this.nodesClicked = nodesClicked;
    this.circle = circle;
  }
}
