package edu.wpi.teamc.controllers.pages.map.MapHelpers;

import edu.wpi.teamc.dao.map.Node;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.shape.Circle;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CloseModeHelper {
  List<Node> toClose = new ArrayList<Node>();
  Node node = new Node();
  int alignX;
  int alignY;
  Circle circle;
  List<String> longNameList = new ArrayList<String>();
  List<Circle> circToClose = new ArrayList<Circle>();

  public CloseModeHelper() {}

  public CloseModeHelper(Node node) {
    this.node = node;
  }

  public CloseModeHelper(List<Node> nodeList) {
    this.toClose = nodeList;
  }

  public void addToList(Node node, Circle circle) {
    this.toClose.add(node);
    this.circToClose.add(circle);
  }

  public void addToLongnameList(String longname) {
    this.longNameList.add(longname);
  }
}
