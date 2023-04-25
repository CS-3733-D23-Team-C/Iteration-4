package edu.wpi.teamc;

import edu.wpi.teamc.dao.map.NodeStatusDao;
import edu.wpi.teamc.dao.requests.*;
import java.io.IOException;
import java.sql.SQLException;

public class Main {

  public static void main(String[] args) throws SQLException, IOException {
    //    CApp.launch(CApp.class, args);
    NodeStatusDao nodeStatusDao = new NodeStatusDao();
    nodeStatusDao.exportCSV(
        "C:\\Users\\chase\\OneDrive\\Documents\\D Term 2023\\Soft Eng\\Iteration 3\\Iteration-3\\src\\main\\resources\\edu\\wpi\\teamc\\NodeStatusExport.csv");

    //    ImportCSV importCSV = new ImportCSV();
    //    importCSV.importAllDisplaysCSV(
    //        "src/main/resources/edu/wpi/teamc/Alert.csv",
    //        "src/main/resources/edu/wpi/teamc/Signage.csv");
    //    ImportCSV importCSV = new ImportCSV();
    //    boolean test =
    //        importCSV.importAllCSV(
    //            "src/main/resources/edu/wpi/teamc/Node.csv",
    //            "src/main/resources/edu/wpi/teamc/Edge.csv",
    //            "src/main/resources/edu/wpi/teamc/Move.csv",
    //            "src/main/resources/edu/wpi/teamc/LocationName.csv");
    //
    //    System.out.println(test);
    System.out.println("Hello World!");
  }
}
