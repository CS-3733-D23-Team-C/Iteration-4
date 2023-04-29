package edu.wpi.teamc.controllers.pages.map.MapHelpers;

import edu.wpi.teamc.graph.GraphNode;
import javafx.scene.Group;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;

public class ArrowHelper {
  private Line line;
  private Line lineBorder;

  public ArrowHelper() {}

  public Group drawArrow(GraphNode node, GraphNode nextNode) {
    boolean upOrDown = upOrDown(node.getFloor(), nextNode.getFloor());

    if (upOrDown) {
      return upArrow(node);
    } else {
      return downArrow(node);
    }
  }

  public boolean upOrDown(String srcFloor, String destFloor) {
    int src = floorToInt(srcFloor);
    int dest = floorToInt(destFloor);

    return src <= dest;
  }

  public int floorToInt(String floor) {
    switch (floor) {
      case "L1" -> {
        return 1;
      }
      case "L2" -> {
        return 2;
      }
      case "1" -> {
        return 3;
      }
      case "2" -> {
        return 4;
      }
      case "3" -> {
        return 5;
      }
    }
    return 0;
  }

  public void drawLines(int adjX, int adjY1, int adjY2) {
    Line line = new Line(adjX, adjY1, adjX, adjY2);
    line.setStrokeWidth(9);
    line.setStroke(Paint.valueOf("#EAB334"));
    line.setVisible(true);

    Line lineBorder = new Line(adjX, adjY1, adjX, adjY2);
    lineBorder.setStrokeWidth(11);
    lineBorder.setStroke(Paint.valueOf("#021335"));
    lineBorder.setVisible(true);

    this.line = line;
    this.lineBorder = lineBorder;
  }

  public Group upArrow(GraphNode node) {
    int adjX = node.getXCoord() - 40;
    int adjY1 = node.getYCoord() + 20;
    int adjY2 = node.getYCoord() - 10;

    drawLines(adjX, adjY1, adjY2);

    Polygon arrowHead = new Polygon();
    arrowHead
        .getPoints()
        .addAll(
            (double) adjX,
            (double) node.getYCoord() - 30,
            (double) node.getXCoord() - 55,
            (double) node.getYCoord() - 5,
            (double) node.getXCoord() - 25,
            (double) node.getYCoord() - 5);
    arrowHead.setFill(Paint.valueOf("#EAB334"));
    arrowHead.setStroke(Paint.valueOf("#021335"));
    arrowHead.setVisible(true);

    Group temp = new Group();
    temp.getChildren().add(lineBorder);
    temp.getChildren().add(line);
    temp.getChildren().add(arrowHead);

    return temp;
  }

  public Group downArrow(GraphNode node) {
    int adjX = node.getXCoord() + 40;
    int adjY1 = node.getYCoord() - 20;
    int adjY2 = node.getYCoord() + 10;

    drawLines(adjX, adjY1, adjY2);

    Polygon arrowHead = new Polygon();
    arrowHead
        .getPoints()
        .addAll(
            (double) adjX,
            (double) node.getYCoord() + 30,
            (double) node.getXCoord() + 55,
            (double) node.getYCoord() + 5,
            (double) node.getXCoord() + 25,
            (double) node.getYCoord() + 5);
    arrowHead.setFill(Paint.valueOf("#EAB334"));
    arrowHead.setStroke(Paint.valueOf("#021335"));
    arrowHead.setVisible(true);

    Group temp = new Group();
    temp.getChildren().add(lineBorder);
    temp.getChildren().add(line);
    temp.getChildren().add(arrowHead);

    return temp;
  }
}
