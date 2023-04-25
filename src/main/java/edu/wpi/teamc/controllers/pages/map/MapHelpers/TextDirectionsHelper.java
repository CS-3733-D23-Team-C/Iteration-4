package edu.wpi.teamc.controllers.pages.map.MapHelpers;

import static java.lang.Math.abs;

import edu.wpi.teamc.graph.Graph;
import edu.wpi.teamc.graph.GraphNode;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class TextDirectionsHelper {
  private String orientation;

  public TextDirectionsHelper() {}

  public String buildURL(List<GraphNode> path, Graph currGraph) {
    String start = currGraph.getLongNameFromNodeID(path.get(0).getNodeID());
    String end = currGraph.getLongNameFromNodeID(path.get(path.size() - 1).getNodeID());
    String directions = "";
    String responseBody = "";

    for (String s : textDirections(path, currGraph)) {
      directions += s + ";";
    }

    try (CloseableHttpClient client = HttpClients.createDefault()) {
      HttpPost httpPost =
          new HttpPost(new URI("https://cs3733-hospital-nextjs-ii0pqmpem-brandonlui.vercel.app/"));

      // set request body
      String json =
          String.format(
              "{\"start\":\"%s\",\"end\":\"%s\",\"directions\":\"%s\"}", start, end, directions);
      StringEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);
      httpPost.setEntity(entity);

      // execute request
      HttpResponse response = client.execute(httpPost);
      HttpEntity responseEntity = response.getEntity();
      if (responseEntity != null) {
        responseBody = EntityUtils.toString(responseEntity, StandardCharsets.UTF_8);
        System.out.println(responseBody);
      }
    } catch (Exception e) {
      System.err.println("Invalid URI: " + e.getMessage());
    }

    return responseBody;
  }

  public LinkedList<String> textDirections(List<GraphNode> path, Graph currGraph) {
    LinkedList<String> textDirections = new LinkedList<>();
    String direction;

    orientation = findOrientation(path.get(0), path.get(1));
    direction =
        distance(path.get(0), path.get(1))
            + "~Go straight~"
            + currGraph.getLongNameFromNodeID(path.get(1).getNodeID());
    textDirections.add(direction);

    for (int i = 1; i < path.size() - 1; i++) {
      GraphNode src = path.get(i);
      GraphNode dest = path.get(i + 1);
      String tempOrientation = findOrientation(src, dest);
      direction = "";

      if (!src.getFloor().equals(dest.getFloor())) {
        direction =
            "0~Go to floor "
                + dest.getFloor()
                + "~"
                + currGraph.getLongNameFromNodeID(src.getNodeID());
        textDirections.add(direction);
      } else {
        if (!tempOrientation.equals(orientation)) {
          direction += distance(src, dest) + "~" + leftOrRight(tempOrientation);
          orientation = tempOrientation;
        } else {
          direction += distance(src, dest) + "~Go straight";
        }
        direction += "~" + currGraph.getLongNameFromNodeID(dest.getNodeID());
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
    String retVal;

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
      retVal = "Continue Straight";
    }

    return retVal;
  }

  private LinkedList<String> clean(LinkedList<String> textDirections) {
    LinkedList<String> clean = new LinkedList<>();
    int totalLength = 0;
    Pattern pattern = Pattern.compile("Go straight", Pattern.CASE_INSENSITIVE);
    Matcher matcher;

    for (String textDirection : textDirections) {
      matcher = pattern.matcher(textDirection);

      if (matcher.find()) {
        String[] split = textDirection.split("~");
        totalLength += Integer.parseInt(split[0]);
      } else {
        if (totalLength != 0) {
          String[] split = textDirection.split("~");
          String combined = totalLength + "~Go straight~" + split[2];
          clean.add(combined);
          totalLength = 0;
        }
        clean.add(textDirection);
      }
    }

    if (totalLength != 0) {
      String[] split = textDirections.get(textDirections.size() - 1).split("~");
      String combined = totalLength + "~Go straight~" + split[2];
      clean.add(combined);
    }

    return clean;
  }
}
