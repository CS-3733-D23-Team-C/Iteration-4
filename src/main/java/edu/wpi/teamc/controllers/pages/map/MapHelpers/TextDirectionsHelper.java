package edu.wpi.teamc.controllers.pages.map.MapHelpers;

import static java.lang.Math.abs;

import edu.wpi.teamc.graph.GraphNode;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
          textDirections.add(leftOrRight(tempOrientation));
          orientation = tempOrientation;
        }

        direction += "go straight for " + distance(src, dest);
        textDirections.add(direction);
      }
    }

    return clean(textDirections);
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
      retVal = "Turn right";
    } else if (orientation.equals("N") && tempOrientation.equals("W")) {
      retVal = "Turn left";
    } else if (orientation.equals("S") && tempOrientation.equals("E")) {
      retVal = "Turn left";
    } else if (orientation.equals("S") && tempOrientation.equals("W")) {
      retVal = "Turn right";
    } else if (orientation.equals("W") && tempOrientation.equals("S")) {
      retVal = "Turn left";
    } else if (orientation.equals("E") && tempOrientation.equals("S")) {
      retVal = "Turn right";
    } else if (orientation.equals("W") && tempOrientation.equals("N")) {
      retVal = "Turn right";
    } else if (orientation.equals("E") && tempOrientation.equals("N")) {
      retVal = "Turn left";
    } else {
      retVal = "go 0";
    }

    return retVal;
  }

  private LinkedList<String> clean(LinkedList<String> textDirections) {
    LinkedList<String> clean = new LinkedList<>();
    int totalLength = 0;
    Pattern pattern = Pattern.compile("^go", Pattern.CASE_INSENSITIVE);
    Matcher matcher;

    for (String textDirection : textDirections) {
      matcher = pattern.matcher(textDirection);

      if (matcher.find()) {
        totalLength += Integer.parseInt(textDirection.replaceAll("[^0-9]", ""));
      } else {
        if (totalLength != 0) {
          String combined = "go straight for " + totalLength;
          clean.add(combined);
          totalLength = 0;
        }
        clean.add(textDirection);
      }
    }

    if (totalLength != 0) {
      String combined = "go straight for " + totalLength;
      clean.add(combined);
    }

    return clean;
  }
}
