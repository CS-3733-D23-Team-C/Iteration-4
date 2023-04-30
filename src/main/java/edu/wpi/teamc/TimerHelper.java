package edu.wpi.teamc;

import edu.wpi.teamc.navigation.Navigation;
import edu.wpi.teamc.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.animation.PauseTransition;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class TimerHelper extends Thread {

  public void run() {

    BorderPane borderPane = new BorderPane();

    // Stuff to show on pop up
    VBox vBox = new VBox();

    Text headerText = new Text("Are you still there?");
    Text building = new Text("hello");

    //  int[] counter = new int[1];
    //    counter[0] = 60;

    PauseTransition pause = new PauseTransition(Duration.seconds(1));
    pause.setOnFinished(
        (event -> {
          Navigation.navigate(Screen.SCREENSAVER);
          Navigation.setMenuType(Navigation.MenuType.DISABLED);
        }));
    try {
      for (int i = 0; i < 5; i++) {
        System.out.println(i);
        building.setText(i + " seconds");
        Thread.sleep(1000);
      }
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    /*for (int i = 60; i >= 0; i--) {
      building.setText(i + " seconds");
      PauseTransition pause = new PauseTransition(Duration.seconds(60));
      pause.setDuration(Duration.seconds(60));
      pause.setDelay(Duration.seconds(60));
      pause.setDelay(Duration.valueOf("60s"));
      pause.play();
      System.out.print("playing");
    } */

    /*for(int i = 60; i >=0; i--) {
        building.setText(i + " seconds");
        Thread.sleep(1000);
    }

        PauseTransition pause = new PauseTransition(Duration.seconds(1));
        pause.setOnFinished(
            (event -> {
              Navigation.navigate(Screen.SCREENSAVER);
              Navigation.setMenuType(Navigation.MenuType.DISABLED);
            })); */
    MFXButton addButton = new MFXButton("Continue");
    vBox.getChildren().addAll(headerText, building, addButton);

    // set styles
    headerText.getStyleClass().add("Header");
    building.getStyleClass().add("Text");
    addButton.getStyleClass().add("MFXbutton");
    borderPane.getStyleClass().add("scenePane");

    // set object locations
    int lay_x = 50;
    int lay_y = 40;
    headerText.setLayoutX(lay_x);
    headerText.setLayoutY(lay_y);
    building.setLayoutX(lay_x);
    building.setLayoutY(lay_y + 35);

    addButton.setLayoutX(lay_x);
    addButton.setLayoutY(lay_y + 95);

    // Set and show screen
    AnchorPane aPane = new AnchorPane();
    aPane.getChildren().addAll(headerText, building, addButton);
    //    Insets insets = new Insets(0, 0, 0, 200);
    //    aPane.setPadding(insets);
    borderPane.getChildren().add(aPane);
    Scene scene = new Scene(borderPane, 410, 225);
    scene
        .getStylesheets()
        .add(Main.class.getResource("views/pages/map/MapEditorPopUps.css").toString());
    borderPane.relocate(0, 0);
    Stage stage = new Stage();
    stage.setScene(scene);
    stage.setTitle("Time Out");
    stage.setAlwaysOnTop(true);
    stage.show();
  }
}
