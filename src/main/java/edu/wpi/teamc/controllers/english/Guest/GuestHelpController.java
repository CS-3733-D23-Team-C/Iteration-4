package edu.wpi.teamc.controllers.english.Guest;

import java.awt.*;
import javafx.fxml.FXML;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import javax.swing.text.html.ImageView;

public class GuestHelpController {
  private String filePath;
  private Desktop desktop = Desktop.getDesktop();
  // @FXML private AdminMenuController adminMenuController;

  @FXML private ImageView English_flag;
  @FXML private ImageView Spanish_flag;
  @FXML private Text AdminHome_Title;
  @FXML private javafx.scene.control.Button logoutButton;
  @FXML private Button exitButton;
  @FXML private MenuButton ServiceRequest;
  @FXML private MenuButton NavigationButton;
  @FXML private MenuButton Settings;
  @FXML private MenuButton Help;
  @FXML private TextArea basicFunctions;
  @FXML private TextArea otherFunctions;
  @FXML private Text helpTitle;

  @FXML
  public void initialize() {
    basicFunctions.setWrapText(true);
    basicFunctions.setEditable(false);
    otherFunctions.setWrapText(true);
    otherFunctions.setEditable(false);
  }
}
