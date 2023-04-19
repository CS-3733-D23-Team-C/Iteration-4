package edu.wpi.teamc.controllers.english.TableHelpers;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class EmpPane {
  TextField idTXT = new TextField();
  TextField nameTXT = new TextField();
  TextField userNameTXT = new TextField();
  Button clearBtn = new Button("Clear");

  @FXML
  public HBox getEmpPane() {
    HBox empPane = new HBox(10);
    empPane.setAlignment(Pos.BASELINE_CENTER);
    empPane.setPadding(new Insets(10));
    Label IdLBL = new Label("ID: ");
    Label nameLBL = new Label("Name: ");
    Label userNameLBL = new Label("Username: ");
    idTXT.setPrefWidth(25);
    nameTXT.setPrefWidth(125);
    userNameTXT.setPrefWidth(25);
    empPane
        .getChildren()
        .addAll(clearBtn, IdLBL, idTXT, nameLBL, nameTXT, userNameLBL, userNameTXT);
    return empPane;
  }
}
