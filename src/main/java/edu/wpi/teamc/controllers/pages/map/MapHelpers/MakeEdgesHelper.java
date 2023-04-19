package edu.wpi.teamc.controllers.pages.map.MapHelpers;

import edu.wpi.teamc.dao.map.Node;
import javafx.scene.shape.Circle;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MakeEdgesHelper {
  Node node;
  int nodesClicked;
  Circle circle;

  public MakeEdgesHelper() {}

  public MakeEdgesHelper(Node node) {
    this.node = node;
  }

  public MakeEdgesHelper(int nodesClicked) {
    this.nodesClicked = nodesClicked;
  }

  public MakeEdgesHelper(Circle circle) {
    this.circle = circle;
  }

  public MakeEdgesHelper(Node node, int nodesClicked) {
    this.node = node;
    this.nodesClicked = nodesClicked;
  }

  public MakeEdgesHelper(Node node, int nodesClicked, Circle circle) {
    this.node = node;
    this.nodesClicked = nodesClicked;
    this.circle = circle;
  }
}
