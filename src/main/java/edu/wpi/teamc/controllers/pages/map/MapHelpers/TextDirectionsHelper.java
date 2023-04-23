package edu.wpi.teamc.controllers.pages.map.MapHelpers;

import static java.lang.Math.abs;

import edu.wpi.teamc.graph.GraphNode;
import java.util.LinkedList;
import java.util.List;

public class TextDirectionsHelper {
  private String orientation;

  public TextDirectionsHelper() {}

  public LinkedList<String> textDirections(List<GraphNode> path) {
    LinkedList<String> textDirections = new LinkedList<>();
    String direction = "";

    orientation = findOrientation(path.get(0), path.get(1));
    direction = "Go straight for " + distance(path.get(0), path.get(1));
    textDirections.add(direction);

    for (int i = 1; i < path.size() - 1; i++) {
      GraphNode src = path.get(i);
      GraphNode dest = path.get(i + 1);
      String tempOrientation = findOrientation(src, dest);
      direction = "";

      if (!src.getFloor().equals(dest.getFloor())) {
        direction = "Floor change";
        textDirections.add(direction);
      } else {
        if (!tempOrientation.equals(orientation)) {
          direction += leftOrRight(tempOrientation);
          orientation = tempOrientation;
        }

        direction += "go straight for " + distance(src, dest);
        textDirections.add(direction);
      }
    }

    return textDirections;
  }

  private String findOrientation(GraphNode one, GraphNode two) {
    // 1748 1321, 75 Francis exit
    // 2091, 796, Bathroom Lobby

    // x increases eastward
    // y decreases northward

    int xDiff = two.getXCoord() - one.getXCoord();
    int yDiff = two.getYCoord() - one.getYCoord();

    if (xDiff > 5 && Math.abs(yDiff) < 100) {
      return "E";
    } else if (xDiff < -5 && Math.abs(yDiff) < 100) {
      return "W";
    } else if (yDiff > 5 && Math.abs(xDiff) < 100) {
      return "S";
    } else {
      return "N";
    }
  }

  private int distance(GraphNode src, GraphNode dest) {
    return (int)
        Math.hypot(
            abs(src.getXCoord() - dest.getXCoord()), abs(src.getYCoord() - dest.getYCoord()));
  }

  private String leftOrRight(String tempOrientation) {
    String retVal = "";

    if (orientation.equals("N") && tempOrientation.equals("E")) {
      retVal = "Turn right, then ";
    } else if (orientation.equals("N") && tempOrientation.equals("W")) {
      retVal = "Turn left, then ";
    } else if (orientation.equals("S") && tempOrientation.equals("E")) {
      retVal = "Turn left, then ";
    } else if (orientation.equals("S") && tempOrientation.equals("W")) {
      retVal = "Turn right, then ";
    } else if (orientation.equals("W") && tempOrientation.equals("S")) {
      retVal = "Turn left, then ";
    } else if (orientation.equals("E") && tempOrientation.equals("S")) {
      retVal = "Turn right, then ";
    } else if (orientation.equals("W") && tempOrientation.equals("N")) {
      retVal = "Turn right, then ";
    } else if (orientation.equals("E") && tempOrientation.equals("N")) {
      retVal = "Turn left, then ";
    }

    return retVal;
  }
}
