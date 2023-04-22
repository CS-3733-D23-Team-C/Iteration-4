package edu.wpi.teamc;

import edu.wpi.teamc.navigation.Navigation;
import edu.wpi.teamc.navigation.Screen;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CApp extends Application {

  @Setter @Getter private static Stage primaryStage;
  @Setter @Getter private static BorderPane rootPane;

  @Getter @Setter private static Boolean adminLoginCheck = false;
  //  List<Node> Floor1 = new ArrayList<Node>();
  //  List<Node> Floor2 = new ArrayList<Node>();
  //  List<Node> Floor3 = new ArrayList<Node>();
  //  List<Node> FloorG = new ArrayList<Node>();
  //  List<Node> FloorL1 = new ArrayList<Node>();
  //  List<Node> FloorL2 = new ArrayList<Node>();

  @Override
  public void init() {
    log.info("Starting Up");
  }

  @Override
  public void start(Stage primaryStage) throws IOException {
    /* primaryStage is generally only used if one of your components require the stage to display */
    CApp.primaryStage = primaryStage;

    final FXMLLoader loader = new FXMLLoader(CApp.class.getResource("views/Root.fxml"));
    final BorderPane root = loader.load();

    CApp.rootPane = root;

    final Scene scene = new Scene(root);
    primaryStage.setScene(scene);
    primaryStage.show();
    Navigation.navigate(Screen.HOME);
  }

  @Override
  public void stop() {
    log.info("Shutting Down");
  }
}
