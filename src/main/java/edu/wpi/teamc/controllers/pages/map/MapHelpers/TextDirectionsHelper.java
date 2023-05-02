package edu.wpi.teamc.controllers.pages.map.MapHelpers;

import static java.lang.Math.abs;

import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeWriter;
import edu.wpi.teamc.graph.Graph;
import edu.wpi.teamc.graph.GraphNode;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
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
import org.json.JSONObject;


public class TextDirectionsHelper {
  private String orientation;

  public TextDirectionsHelper() {}

  public BufferedImage buildImage(List<GraphNode> path, Graph currGraph, LocalDate forDate) {
    String start = currGraph.getLongNameFromNodeID(path.get(0).getNodeID());
    String end = currGraph.getLongNameFromNodeID(path.get(path.size() - 1).getNodeID());
    String directions = "";
    String responseBody = "";

    // format the directions for HttpPost
    for (String s : textDirections(path, currGraph)) {
      if (!s.startsWith("D")) directions += s + ";";
    }

    directions = directions.substring(0, directions.length() - 1);

    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    String formattedDate = formatter.format(Date.valueOf(forDate));
    String json =
        String.format(
            "{\"start\":\"%s\",\"end\":\"%s\",\"directions\":\"%s\", \"forDate\":\"%s\"}",
            start, end, directions, formattedDate);

    try (CloseableHttpClient client = HttpClients.createDefault()) {
      // define website
      HttpPost httpPost = new HttpPost(new URI("https://teamc.blui.co/api/directions"));

      // format and set json
      StringEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);
      httpPost.setEntity(entity);

      HttpResponse response = client.execute(httpPost);
      HttpEntity responseEntity = response.getEntity();
      if (responseEntity != null) {
        responseBody = EntityUtils.toString(responseEntity, StandardCharsets.UTF_8);
        System.out.println(responseBody);
      }
    } catch (Exception e) {
      System.err.println(e.getMessage());
    }
    JSONObject jsonobj = new JSONObject(responseBody);
    String url = jsonobj.getString("shortLink");
    return genQR(url);
  }

  public BufferedImage genQR(String url) {
    BufferedImage qrImage = null;
    int width = 300;
    int height = 300;

    try {
      // setup writer and then encode into a bitMatrix
      QRCodeWriter qrCodeWriter = new QRCodeWriter();
      BitMatrix bitMatrix =
          qrCodeWriter.encode(url, com.google.zxing.BarcodeFormat.QR_CODE, width, height);

      qrImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
      qrImage.createGraphics();

      // using the bitMatrix to "paint" the qrCode
      for (int x = 0; x < width; x++) {
        for (int y = 0; y < height; y++) {
          qrImage.setRGB(x, y, bitMatrix.get(x, y) ? 0xFFFFFFFF : 0xFF02143b);
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return qrImage;
  }

  public LinkedList<String> textDirections(List<GraphNode> path, Graph currGraph) {
    LinkedList<String> textDirections = new LinkedList<>();
    String direction;

    // find starting orientation
    orientation = findOrientation(path.get(0), path.get(1));
    // add floor indicator and first "direction"
    textDirections.add("Directions on Floor " + path.get(0).getFloor());
    direction =
        distance(path.get(0), path.get(1))
            + "~Go straight~"
            + currGraph.getLongNameFromNodeID(path.get(1).getNodeID());
    textDirections.add(direction);

    // iterate through the path and form the text directions
    for (int i = 1; i < path.size() - 1; i++) {
      GraphNode src = path.get(i);
      GraphNode dest = path.get(i + 1);
      // find upcoming orientation to see if a turn is necessary
      String tempOrientation = findOrientation(src, dest);
      direction = "";

      if (!src.getFloor().equals(dest.getFloor())) {
        direction =
            "0~Go to floor "
                + dest.getFloor()
                + "~"
                + currGraph.getLongNameFromNodeID(src.getNodeID());
        textDirections.add(direction);
        textDirections.add("Directions on Floor " + dest.getFloor());
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
      retVal = "↱ Turn right ↱";
    } else if (orientation.equals("N") && tempOrientation.equals("W")) {
      retVal = "↰ Turn left ↰";
    } else if (orientation.equals("S") && tempOrientation.equals("E")) {
      retVal = "↰ Turn left ↰";
    } else if (orientation.equals("S") && tempOrientation.equals("W")) {
      retVal = "↱ Turn right ↱";
    } else if (orientation.equals("W") && tempOrientation.equals("S")) {
      retVal = "↰ Turn left ↰";
    } else if (orientation.equals("E") && tempOrientation.equals("S")) {
      retVal = "↱ Turn right ↱";
    } else if (orientation.equals("W") && tempOrientation.equals("N")) {
      retVal = "↱ Turn right ↱";
    } else if (orientation.equals("E") && tempOrientation.equals("N")) {
      retVal = "↰ Turn left ↰";
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

    // cleans the string by combining any "Go straight" that are next to X amount of other "Go
    // straight"'s
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

    // add icons for start/end
    String str = clean.remove(1);
    String[] strArr = str.split("~");
    strArr[1] = "⚐" + strArr[1] + "⚐";
    str = strArr[0] + "~" + strArr[1] + "~" + strArr[2];
    clean.add(1, str);

    str = clean.removeLast();
    strArr = str.split("~");
    strArr[1] = "⚑" + strArr[1] + "⚑";
    str = strArr[0] + "~" + strArr[1] + "~" + strArr[2];
    clean.addLast(str);

    return clean;
  }
}
