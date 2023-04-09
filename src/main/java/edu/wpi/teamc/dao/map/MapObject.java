package edu.wpi.teamc.dao.map;

import com.sun.javafx.geom.Edge;
import java.util.ArrayList;
import java.util.List;

public abstract class MapObject<T> {
  public static List<Node> databaseNodeList = new ArrayList<Node>();
  public static List<Edge> databaseEdgeList = new ArrayList<Edge>();
  public static List<LocationName> databaseLocationNameList = new ArrayList<LocationName>();
  public static List<Move> databaseMoveList = new ArrayList<Move>();

  public abstract void add(T type);

  public abstract void delete(T type);

  public abstract void update(T type);
}
