package edu.wpi.teamc.controllers.english;

import edu.wpi.teamc.Main;
import edu.wpi.teamc.dao.map.Node;
import edu.wpi.teamc.dao.map.NodeDao;
import edu.wpi.teamc.graph.Graph;
import edu.wpi.teamc.graph.GraphEdge;
import edu.wpi.teamc.graph.GraphNode;
import edu.wpi.teamc.navigation.Navigation;
import edu.wpi.teamc.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.util.Duration;
import javax.swing.*;
import net.kurobako.gesturefx.GesturePane;

public class PathFindingController {
  public Group group;
  public Image image =
      new Image(Main.class.getResource("./views/Images/GroundFloor.png").toString());

  /*
  Notes:
  * implement import and export buttons and get rid of navigation actions
  * implement a submit button that submits all edits at once
      * maybe store edits and their indices in an array and iterate over that array after the submit
      * button is pressed to update the move list from the database
   */

  /** */
  @FXML MFXButton backButton;

  /** Method run when controller is initialized */
  @FXML
  public void goHome() {
    backButton.setOnMouseClicked(event -> Navigation.navigate(Screen.HOME));
  }

  @FXML GesturePane mapGPane;
  @FXML MFXButton FL1;
  @FXML MFXButton FL2;
  @FXML MFXButton FL3;
  @FXML MFXButton FLG;
  @FXML MFXButton FLB1;
  @FXML MFXButton FLB2;
  @FXML MFXButton floorButton;
  Group mapNodes = new Group();
  Group edges = new Group();
  @FXML MFXTextField startNodeId;
  @FXML MFXTextField endNodeId;
  @FXML MFXButton submit;

  @FXML private Button goHome;
  String xCoord_temp = "";
  String yCoord_temp = "";
  String nodeID_temp = "";
  String iD;
  String building;
  String floor = "G";
  List<Node> n_toAdd = new ArrayList<Node>();
  List<Node> n_toModify_newNode = new ArrayList<Node>();
  List<String> n_toModify_oldID = new ArrayList<String>();
  List<String> n_toRemove = new ArrayList<String>();

  /** Method run when controller is initialized */
  public void initialize() {

    //    File file = new File();
    Image image = new Image(Main.class.getResource("./views/Images/GroundFloor.png").toString());
    ImageView imageView = new ImageView(image);
    imageView.relocate(0, 0);
    group.getChildren().add(imageView);
    group.getChildren().add(mapNodes);
    Pane pane = new Pane();
    pane.setMinWidth(image.getWidth());
    pane.setMaxWidth(image.getWidth());
    pane.setMinHeight(image.getHeight());
    pane.setMaxHeight(image.getHeight());
    pane.relocate(0, 0);
    group.getChildren().add(pane);

    placeNodes("G");
  }

  public void changeFloor(ActionEvent event) {
    floorButton = (MFXButton) event.getTarget();

    if (Objects.equals(floorButton.getId(), "FL1")) {
      image = new Image(Main.class.getResource("./views/Images/FirstFloor.png").toString());
      floor = "1";
    } else if (Objects.equals(floorButton.getId(), "FL2")) {
      image = new Image(Main.class.getResource("./views/Images/SecondFloor.png").toString());
      floor = "2";
    } else if (Objects.equals(floorButton.getId(), "FL3")) {
      image = new Image(Main.class.getResource("./views/Images/ThirdFloor.png").toString());
      floor = "3";
    } else if (Objects.equals(floorButton.getId(), "FLG")) {
      image = new Image(Main.class.getResource("./views/Images/GroundFloor.png").toString());
      floor = "G";
    } else if (Objects.equals(floorButton.getId(), "FLB1")) {
      image = new Image(Main.class.getResource("./views/Images/B1.png").toString());
      floor = "L1";
    } else if (Objects.equals(floorButton.getId(), "FLB2")) {
      image = new Image(Main.class.getResource("./views/Images/B2.png").toString());
      floor = "L2";
    }
    group.getChildren().removeAll();
    group.getChildren().remove(mapNodes);
    ImageView imageView = new ImageView(image);
    imageView.relocate(0, 0);
    mapNodes = new Group();
    group.getChildren().add(imageView);
    group.getChildren().add(mapNodes);
    group.getChildren().add(edges);
    Pane pane = new Pane();
    pane.setMinWidth(image.getWidth());
    pane.setMaxWidth(image.getWidth());
    pane.setMinHeight(image.getHeight());
    pane.setMaxHeight(image.getHeight());
    pane.relocate(0, 0);
    group.getChildren().add(pane);
    // placeNodes(floor);
  } // initialize end

  public void placeNodes(String floor) {
    NodeDao nodeDao = new NodeDao();
    nodeDao
        .fetchAllObjects()
        .forEach(
            TBP_node -> {
              if (Objects.equals(TBP_node.getFloor(), floor)) {
                createMapNodes(TBP_node);
              }
            });
    mapNodes.toFront();
  }

  public void createMapNodes(Node node) {
    String shortname = new NodeDao().getShortName(node.getNodeID());
    Circle newCircle = new Circle();
    if (!shortname.equals("")) {
      Tooltip nodeName = new Tooltip(shortname);
      nodeName.setShowDelay(Duration.ZERO);
      nodeName.setShowDuration(Duration.hours(2));
      Tooltip.install(newCircle, nodeName);
    }
    newCircle.setRadius(10);
    newCircle.setCenterX(node.getXCoord());
    newCircle.setCenterY(node.getYCoord());
    newCircle.setId(String.valueOf(node.getNodeID()));
    newCircle.setStroke(Paint.valueOf("#000000"));
    newCircle.setFill(Paint.valueOf("#000000"));
    newCircle.setVisible(true);
    mapNodes.getChildren().add(newCircle);
  }

  //    public String getText(ActionEvent actionEvent) {
  //        String inputtedText;
  //        inputtedText = inputBox.getText();
  //        inputBox.clear();
  //        return inputtedText;
  //    }

  @FXML
  void getSubmit(ActionEvent event) {
    edges.getChildren().clear();
    mapNodes.getChildren().clear();
    String startNode = startNodeId.getText();
    String endNode = endNodeId.getText();

    Graph graph = new Graph();
    graph.syncWithDB();

    GraphNode src = graph.getNode(Integer.valueOf(startNode));
    GraphNode dest = graph.getNode(Integer.valueOf(endNode));
    Circle circ = new Circle();
    circ.setCenterX(dest.getXCoord());
    circ.setCenterY(dest.getYCoord());
    circ.setRadius(20);
    circ.setFill(Paint.valueOf("#32CD32"));
    circ.setStroke(Paint.valueOf("#FF0000"));
    circ.setVisible(true);
    mapNodes.getChildren().add(circ);

    Circle circ2 = new Circle();
    circ2.setCenterX(src.getXCoord());
    circ2.setCenterY(src.getYCoord());
    circ2.setRadius(20);
    circ2.setFill(Paint.valueOf("#FF0000"));
    circ2.setStroke(Paint.valueOf("#32CD32"));
    circ2.setVisible(true);
    mapNodes.getChildren().add(circ2);

    List<GraphEdge> path = graph.getDirections_Astar(src, dest);
    List<GraphEdge> comeAcross = new LinkedList<>();
    GraphEdge saveLast;
    int i = 0;
    int length = path.size();

    while (i < length - 1) {
      saveLast = path.get(i);

      if (saveLast.getStartNode() == path.get(i + 1).getStartNode()) {
        path.remove(i);
        length--;
      } else {
        i++;
      }
    }

    for (GraphEdge edge : path) {
      System.out.println(edge.getStartNode() + " -> " + edge.getEndNode());
      Line temp =
          new Line(
              edge.getSrc().getXCoord(),
              edge.getSrc().getYCoord(),
              edge.getDest().getXCoord(),
              edge.getDest().getYCoord());
      temp.setStrokeWidth(12);
      temp.setVisible(true);
      temp.setStroke(Paint.valueOf("FF0000"));
      edges.getChildren().add(temp);
    }
    edges.toFront();
  }

  @FXML
  void getFlowerDeliveryPage(ActionEvent event) {
    Navigation.navigate(Screen.FLOWER);
  }

  @FXML
  void getFurnitureDeliveryPage(ActionEvent event) {
    Navigation.navigate(Screen.FURNITURE);
  }

  @FXML
  void getHelpPage(ActionEvent event) {
    Navigation.navigate(Screen.HELP);
  }

  @FXML
  void getMealDeliveryPage(ActionEvent event) {
    Navigation.navigate(Screen.MEAL);
  }

  @FXML
  void getOfficeSuppliesPage(ActionEvent event) {
    Navigation.navigate(Screen.OFFICE_SUPPLY);
  }

  @FXML
  void getRoomReservationPage(ActionEvent event) {
    Navigation.navigate(Screen.CONFERENCE);
  }

  @FXML
  void getGiftBasketRequestPage(ActionEvent event) {
    Navigation.navigate(Screen.GIFT_BASKET);
  }

  @FXML
  void getSignagePage(ActionEvent event) {
    Navigation.navigate(Screen.SIGNAGE);
  }

  @FXML
  void getEditMap(ActionEvent event) {
    Navigation.navigate(Screen.EDIT_MAP);
  }

  @FXML
  void getLogOut(ActionEvent event) {
    Navigation.navigate(Screen.LOGIN);
  }

  @FXML
  void getExit(ActionEvent event) {
    Navigation.navigate(Screen.EXIT_PAGE);
  }

  @FXML
  void getMapHistory(ActionEvent event) {
    Navigation.navigate(Screen.MAP_HISTORY_PAGE);
  }

  //  @FXML
  //  void getMapPage(ActionEvent event) {
  //    Navigation.navigate(Screen.FLOOR_PLAN);
  //  }

  @FXML
  void getPathfindingPage(ActionEvent event) {
    Navigation.navigate(Screen.PATHFINDING_PAGE);
  }

  @FXML
  void getHelpage(ActionEvent event) {
    Navigation.navigate(Screen.HELP);
  }
}
