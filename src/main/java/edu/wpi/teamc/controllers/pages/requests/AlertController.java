package edu.wpi.teamc.controllers.pages.requests;


import static edu.wpi.teamc.languageHelpers.LanguageHolder.language_choice;

import edu.wpi.teamc.CApp;

import edu.wpi.teamc.Main;

import edu.wpi.teamc.SMSHelper;
import edu.wpi.teamc.dao.HospitalSystem;
import edu.wpi.teamc.dao.displays.Alert;
import edu.wpi.teamc.dao.users.PatientUserDao;
import edu.wpi.teamc.navigation.Navigation;
import edu.wpi.teamc.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.controlsfx.control.SearchableComboBox;


public class AlertController {
  @FXML private TableView historyTable;
  @FXML TableColumn Column1;
  @FXML TableColumn Column2;
  @FXML TableColumn Column3;
  @FXML TableColumn Column4;
  @FXML TableColumn Column5;
  @FXML TableColumn Column6;
  @FXML private MenuItem choice1;
  @FXML private MenuItem choice2;
  @FXML private MenuItem choice3;
  @FXML private MenuItem choice4;
  @FXML private MenuItem choice5;
  @FXML private MenuItem choice6;

  @FXML private MenuItem severityLow;
  @FXML private MenuItem severityMed;
  @FXML private MenuItem severityHigh;

  @FXML private MenuButton alertType;

  @FXML private MenuButton severityType;
  @FXML private TextField alertTitle;
  @FXML private TextArea alertDescription;
  // TIME RELATED//
  @FXML private DatePicker startTime;
  @FXML private DatePicker endTime;

  // HOUR RELATED//
  @FXML private MenuButton startHour;
  @FXML private MenuButton endHour;
  @FXML private MenuItem Hour_01;
  @FXML private MenuItem Hour_02;
  @FXML private MenuItem Hour_03;
  @FXML private MenuItem Hour_04;
  @FXML private MenuItem Hour_05;
  @FXML private MenuItem Hour_06;
  @FXML private MenuItem Hour_07;
  @FXML private MenuItem Hour_08;
  @FXML private MenuItem Hour_09;
  @FXML private MenuItem Hour_10;
  @FXML private MenuItem Hour_11;
  @FXML private MenuItem Hour_12;

  // TIME PERIOD RELATED//
  @FXML private MenuButton startTimePeriod;
  @FXML private MenuItem AM;
  @FXML private MenuItem PM;
  @FXML private MenuButton endTimePeriod;

  // MINUTE RELATED//
  @FXML private TextField startMinute;
  @FXML private TextField endMinute;

  @FXML
  void getGoHome(ActionEvent event) {
    Navigation.navigate(Screen.ADMIN_HOME);
  }

  @FXML
  void getSubmit(ActionEvent event) {

    LocalDateTime start =
        LocalDateTime.of(
            LocalDate.from(startTime.getValue()),
            LocalTime.of(StartHourReturn(), StartMinuteReturn(), 00));
    LocalDateTime end =
        LocalDateTime.of(
            LocalDate.from(endTime.getValue()),
            LocalTime.of(StartHourReturn(), StartMinuteReturn(), 00));
    String title = alertTitle.getText();
    String description = alertDescription.getText();
    String type = alertSelecter(alertSelection);
    Alert alert =
        new Alert(title, description, type, Timestamp.valueOf(start), Timestamp.valueOf(end));
    HospitalSystem.addRow(alert);
    //    IDao<Alert, Integer> dao = new AlertDao();
    //    dao.addRow(alert);
    Navigation.navigate(Screen.CONGRATS_PAGE);

    PatientUserDao pddao = new PatientUserDao();
    List<String> phones = pddao.listActivePhone();
    for (int i = 0; i < phones.size(); i++) {
      SMSHelper.sendSMS(phones.get(i), description);
    }
  }

  String alertSelecter(int selection) {
    String alertType = "";
    switch (selection) {
      case 1:
        alertType = "Construction";
        break;
      case 2:
        alertType = "Weather";
        break;
      case 3:
        alertType = "Car Crash";
        break;
      case 4:
        alertType = "Closure";
        break;
      case 5:
        alertType = "Emergency";
        break;
      case 6:
        alertType = "Other";
        break;
    }
    return alertType;
  }

  int alertSelection = 0;

  @FXML
  public void initialize() {
    setMinuteTextField();
    Thread thread =
        new Thread(
            new Runnable() {
              @Override
              public void run() {
                ObservableList<Alert> rows = FXCollections.observableArrayList();
                Column1.setCellValueFactory(new PropertyValueFactory<Alert, Integer>("id"));
                Column2.setCellValueFactory(new PropertyValueFactory<Alert, String>("title"));
                Column3.setCellValueFactory(new PropertyValueFactory<Alert, String>("description"));
                Column4.setCellValueFactory(new PropertyValueFactory<Alert, String>("type"));
                Column5.setCellValueFactory(
                    new PropertyValueFactory<Alert, Timestamp>("startdate"));
                Column6.setCellValueFactory(new PropertyValueFactory<Alert, Time>("enddate"));
                List<Alert> list = (List<Alert>) HospitalSystem.fetchAllObjects(new Alert());
                for (Alert a : list) {
                  rows.add(a);
                }
                historyTable.getItems().removeAll();
                historyTable.setItems(rows);
                Platform.runLater(
                    () -> {
                      Column1.setText("ID");
                      Column2.setText("Title");
                      Column3.setText("Description");
                      Column4.setText("Type");
                      Column5.setText("Start date");
                      Column6.setText("End date");
                    });
              }
            });
    thread.start();
  }

  @FXML
  private void getDeleteSelected() {
    Alert alert = (Alert) historyTable.getSelectionModel().getSelectedItem();
    if (alert == null) {
      javafx.scene.control.Alert alert2 =
          new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.ERROR);
      alert2.setTitle("Error");
      alert2.setHeaderText("No alert selected");
      alert2.setContentText("Please select an alert to delete.");
      alert2.showAndWait();
      return;
    }
    javafx.scene.control.Alert alert1 =
        new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.CONFIRMATION);
    alert1.setTitle("Delete Alert");
    alert1.setHeaderText("Are you sure you want to delete this alert?");
    alert1.setContentText("Alert " + alert.getId() + " will be deleted permanently.");
    alert1.showAndWait();
    if (alert1.getResult().getText().equals("OK")) {
      Thread thread =
          new Thread(
              new Runnable() {
                @Override
                public void run() {
                  HospitalSystem.deleteRow(alert);
                  initialize();
                }
              });
      thread.start();
    }
  }

  @FXML
  private void getEditSelected() {
    Alert alert = (Alert) historyTable.getSelectionModel().getSelectedItem();
    if (alert == null) {
      javafx.scene.control.Alert alert2 =
          new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.ERROR);
      alert2.setTitle("Error");
      alert2.setHeaderText("No alert selected");
      alert2.setContentText("Please select an alert to edit.");
      alert2.showAndWait();
      return;
    }

    BorderPane borderPane = new BorderPane();

    Text prompt = new Text("Edit Alert " + alert.getId());
    Label alertTitleLabel = new Label("Title");
    Label alertDescriptionLabel = new Label("Description");
    Label alertTypeLabel = new Label("Type");
    Label startTimeLabel = new Label("Start Time");
    Label endTimeLabel = new Label("End Time");
    TextField alertTitle = new TextField();
    TextField alertDescription = new TextField();
    SearchableComboBox<String> alertType = new SearchableComboBox<>();
    DatePicker startTime = new DatePicker();
    DatePicker endTime = new DatePicker();
    alertType
        .getItems()
        .addAll("Construction", "Weather", "Car Crash", "Closures", "Emergency", "Other");
    MFXButton confirmButton = new MFXButton("Submit");
    MFXButton cancelButton = new MFXButton("Cancel");
    VBox vbox = new VBox();
    alertTitle.setText(alert.getTitle());
    alertDescription.setText(alert.getDescription());
    alertType.setValue(alert.getType());
    startTime.setValue(alert.getStartdate().toLocalDateTime().toLocalDate());
    endTime.setValue(alert.getEnddate().toLocalDateTime().toLocalDate());
    confirmButton.setOnAction(
        e -> {
          LocalDateTime start =
              LocalDateTime.of(LocalDate.from(startTime.getValue()), LocalTime.of(00, 00, 00));
          LocalDateTime end =
              LocalDateTime.of(LocalDate.from(endTime.getValue()), LocalTime.of(00, 00, 00));
          String title = alertTitle.getText();
          String description = alertDescription.getText();
          String type = alertType.getValue();

          alert.setTitle(title);
          alert.setDescription(description);
          alert.setType(type);
          alert.setStartdate(Timestamp.valueOf(start));
          alert.setEnddate(Timestamp.valueOf(end));
          HospitalSystem.updateRow(alert);
          //          IDao<Alert, Integer> dao = new AlertDao();
          //          dao.updateRow(alert1);
          Stage stage = (Stage) confirmButton.getScene().getWindow();
          stage.close();
          initialize();
        });

    vbox.setSpacing(10);
    confirmButton.getStyleClass().add("MFXbutton");
    cancelButton.getStyleClass().add("MFXbutton");
    prompt.getStyleClass().add("Header");
    borderPane.getStyleClass().add("scenePane");
    alertTitleLabel.getStyleClass().add("whiteLabel");
    alertDescriptionLabel.getStyleClass().add("whiteLabel");
    alertTypeLabel.getStyleClass().add("whiteLabel");
    startTimeLabel.getStyleClass().add("whiteLabel");
    endTimeLabel.getStyleClass().add("whiteLabel");
    vbox.getChildren()
        .addAll(
            prompt,
            alertTitleLabel,
            alertTitle,
            alertDescriptionLabel,
            alertDescription,
            alertTypeLabel,
            alertType,
            startTimeLabel,
            startTime,
            endTimeLabel,
            endTime,
            confirmButton,
            cancelButton);

    cancelButton.setOnAction(
        e -> {
          Stage stage = (Stage) cancelButton.getScene().getWindow();
          stage.close();
        });

    // set object locations
    int lay_x = 45;
    int lay_y = 40;
    vbox.setLayoutX(lay_x);
    vbox.setLayoutY(lay_y);

    // Set and show screen
    AnchorPane aPane = new AnchorPane();
    aPane.getChildren().addAll(vbox);
    borderPane.getChildren().add(aPane);
    Scene scene = new Scene(borderPane, 300, 525);
    scene
        .getStylesheets()
        .add(Main.class.getResource("views/pages/map/MapEditorPopUps.css").toString());
    borderPane.relocate(0, 0);
    Stage stage = new Stage();
    stage.setScene(scene);
    stage.setTitle("Edit Alert");
    stage.show();
  }

  @FXML
  void setChoice1(ActionEvent event) {
    alertType.setText(choice1.getText());
    alertSelection = 1;
  }

  @FXML
  void setChoice2(ActionEvent event) {
    alertType.setText(choice2.getText());
    alertSelection = 2;
  }

  @FXML
  void setChoice3(ActionEvent event) {
    alertType.setText(choice3.getText());
    alertSelection = 3;
  }

  @FXML
  void setChoice4(ActionEvent event) {
    alertType.setText(choice4.getText());
    alertSelection = 4;
  }

  @FXML
  void setChoice5(ActionEvent event) {
    alertType.setText(choice5.getText());
    alertSelection = 5;
  }

  @FXML
  void setChoice6(ActionEvent event) {
    alertType.setText(choice6.getText());
    alertSelection = 6;
  }

  @FXML
  void setSeverityLow(ActionEvent event) {
    severityType.setText(severityLow.getText());
  }

  @FXML
  void setSeverityMed(ActionEvent event) {
    severityType.setText(severityMed.getText());
  }

  @FXML
  void setSeverityHigh(ActionEvent event) {
    severityType.setText(severityHigh.getText());
  }

  @FXML
  void getClear(ActionEvent event) {
    Navigation.navigate(Screen.ALERT_REQUEST);
  }

  /** Method run when controller is initialized */

  @FXML
  public void initialize() throws Exception {
    setMinuteTextField();
    setLanguage();
  }


  public List<String> holder = new ArrayList<String>();

  @FXML
  void setLanguage() throws Exception {
    if (language_choice == 0) {
      holder = CApp.Alert_English_list;
    } else if (language_choice == 1) {
      // holder = CApp.Home_Spanish_list;
    } else if (language_choice == 2) {
      holder = CApp.Alert_Chinese_list;
    }

    Title.setText(holder.get(0));
    Box1.setText(holder.get(1));
    Box2.setText(holder.get(2));
    alertTitle.setPromptText(holder.get(3));
    alertType.setText(holder.get(4));
    severityType.setText(holder.get(5));
    alertDescription.setPromptText(holder.get(6));
    startTime.setPromptText(holder.get(7));
    endTime.setPromptText(holder.get(8));
    startHour.setText(holder.get(9));
    endHour.setText(holder.get(10));
    startMinute.setPromptText(holder.get(11));
    endMinute.setPromptText(holder.get(12));
    Submit.setText(holder.get(13));
    Clear.setText(holder.get(14));
    Cancel.setText(holder.get(15));
    choice1.setText(holder.get(16));
    choice2.setText(holder.get(17));
    choice3.setText(holder.get(18));
    choice4.setText(holder.get(19));
    choice5.setText(holder.get(20));
    choice6.setText(holder.get(21));
    severityLow.setText(holder.get(22));
    severityMed.setText(holder.get(23));
    severityHigh.setText(holder.get(24));
  }

  @FXML private Text Title;
  @FXML private TextField Box1;
  @FXML private TextField Box2;
  @FXML private MFXButton Submit;
  @FXML private MFXButton Clear;
  @FXML private MFXButton Cancel;

  // MINUTE TEXTFIELD RESTRICTION//
  @FXML
  void setMinuteTextField() {
    TextFormatter<String> startFormat =
        new TextFormatter<>(
            change -> {
              String newText = change.getControlNewText();
              int i = 0;
              if (!(newText.isEmpty())) {
                i = Integer.parseInt(newText);
              }
              if (((newText.matches("\\d*")) && (!(i >= 0) || !(i <= 59)))) {
                return null;
              } else {
                return change;
              }
            });
    TextFormatter<String> endFormat =
        new TextFormatter<>(
            change -> {
              String newText = change.getControlNewText();
              int i = 0;
              if (!(newText.isEmpty())) {
                i = Integer.parseInt(newText);
              }
              if (((newText.matches("\\d*")) && (!(i >= 0) || !(i <= 59)))) {
                return null;
              } else {
                return change;
              }
            });

    startMinute.setTextFormatter(startFormat);
    endMinute.setTextFormatter(endFormat);
  }

  // TIME Combine FUNCTION//

  @FXML
  int StartHourReturn() {
    int i = 0;
    if (startTimePeriod.getText() == "AM") {
      i = Integer.parseInt(startHour.getText());
    } else if (startTimePeriod.getText() == "PM") {
      i = Integer.parseInt(startHour.getText()) + 12;
    }
    return i;
  }

  @FXML
  int EndHourReturn() {
    int i = 0;
    if (endTimePeriod.getText() == "AM") {
      i = Integer.parseInt(endHour.getText());
    } else if (endTimePeriod.getText() == "PM") {
      i = Integer.parseInt(endHour.getText()) + 12;
    }
    return i;
  }

  @FXML
  int StartMinuteReturn() {
    int i = 0;
    i = Integer.parseInt(startMinute.getText());
    return i;
  }

  @FXML
  int EndMinuteReturn() {
    int i = 0;
    i = Integer.parseInt(endMinute.getText());
    return i;
  }

  // TIME RELATED//
  @FXML
  void getStartHour01(ActionEvent event) {
    startHour.setText(Hour_01.getText());
  }

  @FXML
  void getStartHour02(ActionEvent event) {
    startHour.setText(Hour_02.getText());
  }

  @FXML
  void getStartHour03(ActionEvent event) {
    startHour.setText(Hour_03.getText());
  }

  @FXML
  void getStartHour04(ActionEvent event) {
    startHour.setText(Hour_04.getText());
  }

  @FXML
  void getStartHour05(ActionEvent event) {
    startHour.setText(Hour_05.getText());
  }

  @FXML
  void getStartHour06(ActionEvent event) {
    startHour.setText(Hour_06.getText());
  }

  @FXML
  void getStartHour07(ActionEvent event) {
    startHour.setText(Hour_07.getText());
  }

  @FXML
  void getStartHour08(ActionEvent event) {
    startHour.setText(Hour_08.getText());
  }

  @FXML
  void getStartHour09(ActionEvent event) {
    startHour.setText(Hour_09.getText());
  }

  @FXML
  void getStartHour10(ActionEvent event) {
    startHour.setText(Hour_10.getText());
  }

  @FXML
  void getStartHour11(ActionEvent event) {
    startHour.setText(Hour_11.getText());
  }

  @FXML
  void getStartHour12(ActionEvent event) {
    startHour.setText(Hour_12.getText());
  }

  @FXML
  void getEndHour01(ActionEvent event) {
    endHour.setText(Hour_01.getText());
  }

  @FXML
  void getEndHour02(ActionEvent event) {
    endHour.setText(Hour_02.getText());
  }

  @FXML
  void getEndHour03(ActionEvent event) {
    endHour.setText(Hour_03.getText());
  }

  @FXML
  void getEndHour04(ActionEvent event) {
    endHour.setText(Hour_04.getText());
  }

  @FXML
  void getEndHour05(ActionEvent event) {
    endHour.setText(Hour_05.getText());
  }

  @FXML
  void getEndHour06(ActionEvent event) {
    endHour.setText(Hour_06.getText());
  }

  @FXML
  void getEndHour07(ActionEvent event) {
    endHour.setText(Hour_07.getText());
  }

  @FXML
  void getEndHour08(ActionEvent event) {
    endHour.setText(Hour_08.getText());
  }

  @FXML
  void getEndHour09(ActionEvent event) {
    endHour.setText(Hour_09.getText());
  }

  @FXML
  void getEndHour10(ActionEvent event) {
    endHour.setText(Hour_10.getText());
  }

  @FXML
  void getEndHour11(ActionEvent event) {
    endHour.setText(Hour_11.getText());
  }

  @FXML
  void getEndHour12(ActionEvent event) {
    endHour.setText(Hour_12.getText());
  }

  @FXML
  void getStartPeriodAM(ActionEvent event) {
    startTimePeriod.setText(AM.getText());
  }

  @FXML
  void getStartPeriodPM(ActionEvent event) {
    startTimePeriod.setText(PM.getText());
  }

  @FXML
  void getEndPeriodAM(ActionEvent event) {
    endTimePeriod.setText(AM.getText());
  }

  @FXML
  void getEndPeriodPM(ActionEvent event) {
    endTimePeriod.setText(PM.getText());
  }
}
