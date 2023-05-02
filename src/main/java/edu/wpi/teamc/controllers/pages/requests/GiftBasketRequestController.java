package edu.wpi.teamc.controllers.pages.requests;

import edu.wpi.teamc.Main;
import edu.wpi.teamc.navigation.Navigation;
import edu.wpi.teamc.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import org.controlsfx.control.SearchableComboBox;

public class GiftBasketRequestController {
  @FXML private MFXButton goHome;
  @FXML private MFXButton submit;

  @FXML private AnchorPane assignEmployeeAnchor;
  @FXML private TextArea basketItems;
  @FXML private DatePicker deliveryTime;
  @FXML private SearchableComboBox<?> employeeName;
  @FXML private ImageView image;
  @FXML private TextField nameBox;
  @FXML private SearchableComboBox<?> roomMenu;
  @FXML private MenuButton serviceMenu;
  @FXML private MenuItem servicechoice1;
  @FXML private MenuItem servicechoice2;
  @FXML private MenuItem servicechoice3;
  @FXML private MenuItem servicechoice4;
  @FXML private TextArea specialRequest;

  @FXML
  void getServicechoice1() {
    serviceMenu.setText(servicechoice1.getText());
    try {
      getImage(1);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @FXML
  void getServicechoice2() {
    serviceMenu.setText(servicechoice2.getText());
    try {
      getImage(2);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @FXML
  void getServicechoice3() {
    serviceMenu.setText(servicechoice3.getText());
    try {
      getImage(3);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @FXML
  void getServicechoice4() {
    serviceMenu.setText(servicechoice4.getText());
    try {
      getImage(4);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @FXML
  public void getGoHome() {
    Navigation.navigate(Screen.ADMIN_HOME);
  }

  @FXML
  void getClear() {
    Navigation.navigate(Screen.GIFT_BASKET);
  }

  @FXML
  void getSubmit() {}

  @FXML
  void getImage(int choice) throws IOException {
    switch (choice) {
      case 1:
        image.setImage(
            new Image(
                Main.class
                    .getResource("views/images/GiftBasket/small-gift-basket.png")
                    .openStream()));
        break;
      case 2:
        image.setImage(
            new Image(
                Main.class
                    .getResource("views/images/GiftBasket/medium-gift-basket.png")
                    .openStream()));
        break;
      case 3:
        image.setImage(
            new Image(
                Main.class
                    .getResource("views/images/GiftBasket/large-gift-basket.png")
                    .openStream()));
        break;
      case 4:
        image.setImage(
            new Image(
                Main.class
                    .getResource("views/images/GiftBasket/extra-large-gift-basket.png")
                    .openStream()));
        break;
    }
  }

  /** Method run when controller is initialized */
  @FXML
  public void initialize() {}

  //  @FXML
  //  void getMapPage(ActionEvent event) {
  //    Navigation.navigate(Screen.FLOOR_PLAN);
  //  }
}
