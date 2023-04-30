package edu.wpi.teamc.controllers.pages;

import edu.wpi.teamc.dao.IDao;
import edu.wpi.teamc.dao.ImportCSV;
import edu.wpi.teamc.dao.displays.AlertDao;
import edu.wpi.teamc.dao.displays.signage.SignEntryDao;
import edu.wpi.teamc.dao.map.EdgeDao;
import edu.wpi.teamc.dao.map.LocationNameDao;
import edu.wpi.teamc.dao.map.MoveDao;
import edu.wpi.teamc.dao.map.NodeDao;
import edu.wpi.teamc.dao.requests.*;
import edu.wpi.teamc.dao.users.EmployeeUserDao;
import edu.wpi.teamc.dao.users.login.LoginDao;
import edu.wpi.teamc.navigation.Navigation;
import edu.wpi.teamc.navigation.Screen;
import edu.wpi.teamc.dao.*;
import java.awt.*;
import java.io.*;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class ImportExportController {

  @FXML
  void getGoHome(ActionEvent event) {
    Navigation.navigate(Screen.ADMIN_HOME);
  }

  private Desktop desktop = Desktop.getDesktop();
  private String filePath;

  /** Method run when controller is initialized */
  @FXML
  public void initialize() {}

  // Map variables
  private String[] selectedMapFilePaths = new String[4];
  final String nodeHeader = "nodeID,xCoord,yCoord,floor,building,status";
  final String edgeHeader = "startNode,endNode";
  final String moveHeader = "nodeID,longName,date";
  final String locationHeader = "longName,shortName,nodeType";

  // Service variables
  private String[] selectedServiceFilePaths = new String[6];
  final String conferenceRoomHeader =
      "requestid,requester,roomname,status,additionalnotes,starttime,endtime,assignedto";
  final String flowerHeader =
      "requestID,requester,roomname,status,additionalnotes,eta,flower,assignedto";
  final String furnitureHeader =
      "requestid,requester,roomname,status,additionalnotes,furnituretype,eta,assignedto";
  final String giftBasketHeader =
      "requestid,requester,status,additionalnotes,giftbasket,eta,roomname,assignedto";
  final String mealHeader =
      "requestID,requester,status,additionalnotes,meal,eta,roomname,assignedto";
  final String officeSupplyHeader =
      "requestID,requester,status,additionalnotes,officesupplytype,eta,roomname,assignedto";

  // User imports
  private String[] selectedUsersFilePaths = new String[3];
  final String employeeHeader = "id,username,name,department,position";
  final String loginHeader = "username,password,permissions,salt,otp";
  final String patientHeader = "id,name,checkin,checkout,phone,room,activetext";
  // Displays imports
  private String[] selectedDisplaysFilePaths = new String[2];
  final String alertsHeader = "id,title,description,type,startdate,enddate";
  final String signageHeader = "macadd,devicename,date,locationname,direction";

  @FXML
  void getImport(String Header) {
    FileChooser fileChooser = new FileChooser();
    FileChooser.ExtensionFilter extFilter =
        new FileChooser.ExtensionFilter("CSV files (*.csv)", "*.csv");
    fileChooser.getExtensionFilters().add(extFilter);
    File file = fileChooser.showOpenDialog(new Stage());
    if (file != null) {
      try {
        BufferedReader br = new BufferedReader(new FileReader(file));
        String importedHeader = br.readLine();
        if (importedHeader.equals(Header)) {
          desktop.open(file);
          filePath = file.getAbsolutePath();
          switch (Header) {
            case nodeHeader:
              selectedMapFilePaths[0] = filePath;
              break;
            case edgeHeader:
              selectedMapFilePaths[1] = filePath;
              break;
            case moveHeader:
              selectedMapFilePaths[2] = filePath;
              break;
            case locationHeader:
              selectedMapFilePaths[3] = filePath;
              break;
            case conferenceRoomHeader:
              selectedServiceFilePaths[0] = filePath;
              break;
            case flowerHeader:
              selectedServiceFilePaths[1] = filePath;
              break;
            case furnitureHeader:
              selectedServiceFilePaths[2] = filePath;
              break;
            case giftBasketHeader:
              selectedServiceFilePaths[3] = filePath;
              break;
            case mealHeader:
              selectedServiceFilePaths[4] = filePath;
              break;
            case officeSupplyHeader:
              selectedServiceFilePaths[5] = filePath;
              break;
            case employeeHeader:
              selectedUsersFilePaths[0] = filePath;
              break;
            case loginHeader:
              selectedUsersFilePaths[1] = filePath;
              break;
            case patientHeader:
              selectedUsersFilePaths[2] = filePath;
              break;
            case alertsHeader:
              selectedDisplaysFilePaths[0] = filePath;
              break;
            case signageHeader:
              selectedDisplaysFilePaths[1] = filePath;
              break;
          }
        } else {
          // if it doesn't, display error message
          Alert alert = new Alert(Alert.AlertType.ERROR);
          alert.setTitle("Error");
          alert.setHeaderText("File header does not match header format");
          alert.setContentText("Please select a valid file");
          alert.showAndWait();
        }
      } catch (Exception ex) {
        ex.printStackTrace();
      }
    }
  }

  // Map imports
  @FXML
  void getImportNodes() {
    getImport(nodeHeader);
  }

  @FXML
  void getImportEdges() {
    getImport(edgeHeader);
  }

  @FXML
  void getImportMoves() {
    getImport(moveHeader);
  }

  @FXML
  void getImportLocations() {
    getImport(locationHeader);
  }

  @FXML
  void getImportMapSubmit(ActionEvent event) {
    String nodesFilePath = selectedMapFilePaths[0];
    String edgesFilePath = selectedMapFilePaths[1];
    String moveFilePath = selectedMapFilePaths[2];
    String locationNamesFilePath = selectedMapFilePaths[3];
    ImportCSV.importMapCSV(nodesFilePath, edgesFilePath, moveFilePath, locationNamesFilePath);
    selectedMapFilePaths[0] = null;
    selectedMapFilePaths[1] = null;
    selectedMapFilePaths[2] = null;
    selectedMapFilePaths[3] = null;
  }

  @FXML
  void getImportMapCancel(ActionEvent event) {
    selectedMapFilePaths[0] = null;
    selectedMapFilePaths[1] = null;
    selectedMapFilePaths[2] = null;
    selectedMapFilePaths[3] = null;
  }

  // Service imports
  @FXML
  void getImportConferenceRoom() {
    getImport(conferenceRoomHeader);
  }

  @FXML
  void getImportFlower() {
    getImport(flowerHeader);
  }

  @FXML
  void getImportFurniture() {
    getImport(furnitureHeader);
  }

  @FXML
  void getImportGiftBasket() {
    getImport(giftBasketHeader);
  }

  @FXML
  void getImportMeal() {
    getImport(mealHeader);
  }

  @FXML
  void getImportOfficeSupply() {
    getImport(officeSupplyHeader);
  }

  @FXML
  void getImportServiceSubmit(ActionEvent event) throws SQLException, FileNotFoundException {
    String conferenceRoomFilePath = selectedServiceFilePaths[0];
    String flowerFilePath = selectedServiceFilePaths[1];
    String furnitureFilePath = selectedServiceFilePaths[2];
    String giftBasketFilePath = selectedServiceFilePaths[3];
    String mealFilePath = selectedServiceFilePaths[4];
    String officeSupplyFilePath = selectedServiceFilePaths[5];
    ImportCSV.importConferenceRequestCSV(conferenceRoomFilePath);
    ImportCSV.importFlowerRequestCSV(flowerFilePath);
    ImportCSV.importFurnitureRequestCSV(furnitureFilePath);
    ImportCSV.importGiftBasketRequestCSV(giftBasketFilePath);
    ImportCSV.importMealRequestCSV(mealFilePath);
    ImportCSV.importOfficeSupplyRequestCSV(officeSupplyFilePath);
    selectedServiceFilePaths[0] = null;
    selectedServiceFilePaths[1] = null;
    selectedServiceFilePaths[2] = null;
    selectedServiceFilePaths[3] = null;
    selectedServiceFilePaths[4] = null;
    selectedServiceFilePaths[5] = null;
  }

  @FXML
  void getImportServiceCancel(ActionEvent event) {
    selectedServiceFilePaths[0] = null;
    selectedServiceFilePaths[1] = null;
    selectedServiceFilePaths[2] = null;
    selectedServiceFilePaths[3] = null;
    selectedServiceFilePaths[4] = null;
    selectedServiceFilePaths[5] = null;
  }
  // User imports
  @FXML
  void getImportEmployee() {
    getImport(employeeHeader);
  }

  @FXML
  void getImportLogin() {
    getImport(loginHeader);
  }

  @FXML
  void getImportPatient() {
    getImport(patientHeader);
  }

  @FXML
  void getImportUserSubmit(ActionEvent event) throws SQLException, FileNotFoundException {
    String employeeFilePath = selectedUsersFilePaths[0];
    String loginFilePath = selectedUsersFilePaths[1];
    String patientFilePath = selectedUsersFilePaths[2];
    ImportCSV.importEmployeeCSV(employeeFilePath, loginFilePath);
    ImportCSV.importPatientCSV(patientFilePath);
    selectedUsersFilePaths[0] = null;
    selectedUsersFilePaths[1] = null;
    selectedUsersFilePaths[2] = null;
  }

  @FXML
  void getImportUserCancel(ActionEvent event) {
    selectedUsersFilePaths[0] = null;
    selectedUsersFilePaths[1] = null;
    selectedUsersFilePaths[2] = null;
  }

  // Display imports
  @FXML
  void getImportAlerts() {
    getImport(alertsHeader);
  }

  @FXML
  void getImportSignage() {
    getImport(signageHeader);
  }

  @FXML
  void getImportDisplaySubmit(ActionEvent event) throws SQLException, FileNotFoundException {
    String alertsFilePath = selectedDisplaysFilePaths[0];
    String signageFilePath = selectedDisplaysFilePaths[1];
    ImportCSV.importAlertCSV(alertsFilePath);
    ImportCSV.importSignageCSV(signageFilePath);
    selectedDisplaysFilePaths[0] = null;
    selectedDisplaysFilePaths[1] = null;
  }

  @FXML
  void getImportDisplayCancel(ActionEvent event) {
    selectedDisplaysFilePaths[0] = null;
    selectedDisplaysFilePaths[1] = null;
  }

  //Export

//  void getExport(String header) throws IOException {
//      FileChooser fileChooser = new FileChooser();
//      fileChooser.setTitle("Save");
//      fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("All Files", "*.*"));
//      File file = fileChooser.showSaveDialog(new Stage());
//      if (file != null) {
//        String filePath = file.getAbsolutePath();
//        if (!filePath.endsWith(".csv")) { // check if file path doesn't already end with ".csv"
//          filePath += ".csv"; // append ".csv" to the file path
//        }
//        switch (header){
//            case nodeHeader:
//                NodeDao.exportCSV(filePath);
//                break;
//            case edgeHeader:
//                EdgeDao.exportCSV(filePath);
//                break;
//            case moveHeader:
//                MoveDao.exportCSV(filePath);
//                break;
//            case locationHeader:
//                LocationNameDao.exportCSV(filePath);
//                break;
//            case conferenceRoomHeader:
//              ConferenceRoomRequestDAO.exportCSV(filePath);
//                break;
//            case flowerHeader:
//              FlowerDeliveryRequestDAO.exportCSV(filePath);
//                break;
//            case furnitureHeader:
//              FurnitureDeliveryRequestDAO.exportCSV(filePath);
//                break;
////            case giftBasketHeader:
////                GiftBasketDao.exportCSV(filePath);
////                break;
//            case mealHeader:
//              MealRequestDAO.exportCSV(filePath);
//                break;
//            case officeSupplyHeader:
//              OfficeSuppliesRequestDAO.exportCSV(filePath);
//                break;
//            case employeeHeader:
//              EmployeeUserDao.exportCSV(filePath);
//                break;
//            case loginHeader:
//                LoginDao.exportCSV(filePath);
//                break;
////            case patientHeader:
////                PatientDao.exportCSV(filePath);
////                break;
//            case alertsHeader:
//                AlertDao.exportCSV(filePath);
//                break;
//            case signageHeader:
//              SignEntryDao.exportCSV(filePath);
//                break;
//        }
//      }
//    }
}
