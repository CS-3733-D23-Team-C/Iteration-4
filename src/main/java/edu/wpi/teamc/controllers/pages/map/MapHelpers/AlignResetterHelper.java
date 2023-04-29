package edu.wpi.teamc.controllers.pages.map.MapHelpers;

import edu.wpi.teamc.dao.map.Node;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.shape.Circle;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AlignResetterHelper {
  List<Node> toAlign = new ArrayList<Node>();
  Node node = new Node();
  int alignX;
  int alignY;
  Circle circle;
  List<Circle> circToAlign = new ArrayList<Circle>();
  List<Node> lastAlignedNode = new ArrayList<Node>();
  List<Circle> lastAlignedCirc = new ArrayList<Circle>();

  public AlignResetterHelper() {}

  public AlignResetterHelper(Node node) {
    this.node = node;
  }

  public AlignResetterHelper(List<Node> nodeList) {
    this.toAlign = nodeList;
  }

  public void addToList(Node node) {
    this.toAlign.add(node);
    //        this.circToAlign.add(circle);
  }
}
