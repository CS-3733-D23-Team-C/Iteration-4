package edu.wpi.teamc.dao;

import edu.wpi.teamc.dao.displays.Alert;
import edu.wpi.teamc.dao.displays.AlertDao;
import edu.wpi.teamc.dao.displays.signage.SignEntryDao;
import edu.wpi.teamc.dao.map.*;
import edu.wpi.teamc.dao.requests.*;
import edu.wpi.teamc.dao.users.AdminUser;
import edu.wpi.teamc.dao.users.AdminUserDao;
import edu.wpi.teamc.dao.users.EmployeeUser;
import edu.wpi.teamc.dao.users.EmployeeUserDao;
import edu.wpi.teamc.dao.users.login.Login;
import edu.wpi.teamc.dao.users.login.LoginDao;
import java.util.List;

// facade pattern

public class HospitalSystem {
  // Map DAOs
  private static NodeDao nodeDao = new NodeDao();
  private static EdgeDao edgeDao = new EdgeDao();
  private static LocationNameDao locationNameDao = new LocationNameDao();
  private static MoveDao moveDao = new MoveDao();

  // Service Request DAOs
  private static ConferenceRoomRequestDAO conferenceRoomRequestDAO = new ConferenceRoomRequestDAO();
  private static FlowerDeliveryRequestDAO flowerDeliveryRequestDAO = new FlowerDeliveryRequestDAO();
  private static FurnitureDeliveryRequestDAO furnitureDeliveryRequestDAO =
      new FurnitureDeliveryRequestDAO();
  private static MealRequestDAO mealRequestDAO = new MealRequestDAO();
  private static OfficeSuppliesRequestDAO officeSuppliesRequestDAO = new OfficeSuppliesRequestDAO();

  // User DAOs
  private static EmployeeUserDao employeeDao = new EmployeeUserDao();
  private static AdminUserDao adminDao = new AdminUserDao();
  private static LoginDao loginDao = new LoginDao();

  // Display DAOs
  private static AlertDao alertDao = new AlertDao();
  private static SignEntryDao signDao = new SignEntryDao();

  public static List<? extends IOrm> fetchAllObjects(IOrm request) {
    if (request instanceof Node) {
      return nodeDao.fetchAllObjects();
    } else if (request instanceof Edge) {
      return edgeDao.fetchAllObjects();
    } else if (request instanceof LocationName) {
      return locationNameDao.fetchAllObjects();
    } else if (request instanceof Move) {
      return moveDao.fetchAllObjects();
    } else if (request instanceof ConferenceRoomRequest) {
      return conferenceRoomRequestDAO.fetchAllObjects();
    } else if (request instanceof EmployeeUser) {
      return employeeDao.fetchAllObjects();
    } else if (request instanceof FlowerDeliveryRequest) {
      return flowerDeliveryRequestDAO.fetchAllObjects();
    } else if (request instanceof FurnitureDeliveryRequest) {
      return furnitureDeliveryRequestDAO.fetchAllObjects();
    } else if (request instanceof MealRequest) {
      return mealRequestDAO.fetchAllObjects();
    } else if (request instanceof OfficeSuppliesRequest) {
      return officeSuppliesRequestDAO.fetchAllObjects();
    } else if (request instanceof AdminUser) {
      return adminDao.fetchAllObjects();
    } else if (request instanceof EmployeeUser) {
      return employeeDao.fetchAllObjects();
    } else if (request instanceof Login) {
      return loginDao.fetchAllObjects();
    } else if (request instanceof Alert) {
      return alertDao.fetchAllObjects();
    } else if (request instanceof Sign) {
      return signDao.fetchAllObjects();
    } else {
      return null;
    }
  }

  public static IOrm addRow(IOrm request) {
    if (request instanceof Node) {
      return nodeDao.addRow((Node) request);
    } else if (request instanceof Edge) {
      return edgeDao.addRow((Edge) request);
    } else if (request instanceof LocationName) {
      return locationNameDao.addRow((LocationName) request);
    } else if (request instanceof Move) {
      return moveDao.addRow((Move) request);
    } else if (request instanceof ConferenceRoomRequest) {
      return conferenceRoomRequestDAO.addRow((ConferenceRoomRequest) request);
    } else if (request instanceof EmployeeUser) {
      return employeeDao.addRow((EmployeeUser) request);
    } else if (request instanceof FlowerDeliveryRequest) {
      return flowerDeliveryRequestDAO.addRow((FlowerDeliveryRequest) request);
    } else if (request instanceof FurnitureDeliveryRequest) {
      return furnitureDeliveryRequestDAO.addRow((FurnitureDeliveryRequest) request);
    } else if (request instanceof MealRequest) {
      return mealRequestDAO.addRow((MealRequest) request);
    } else if (request instanceof OfficeSuppliesRequest) {
      return officeSuppliesRequestDAO.addRow((OfficeSuppliesRequest) request);
    } else if (request instanceof AdminUser) {
      return adminDao.addRow((AdminUser) request);
    } else if (request instanceof EmployeeUser) {
      return employeeDao.addRow((EmployeeUser) request);
    } else if (request instanceof Login) {
      return loginDao.addRow((Login) request);
    } else if (request instanceof Alert) {
      return alertDao.addRow((Alert) request);
    } else if (request instanceof Sign) {
      return signDao.addRow((Sign) request);
    } else {
      return null;
    }
  }

  public static IOrm deleteRow(IOrm request) {
    if (request instanceof Node) {
      return nodeDao.deleteRow((Node) request);
    } else if (request instanceof Edge) {
      return edgeDao.deleteRow((Edge) request);
    } else if (request instanceof LocationName) {
      return locationNameDao.deleteRow((LocationName) request);
    } else if (request instanceof Move) {
      return moveDao.deleteRow((Move) request);
    } else if (request instanceof ConferenceRoomRequest) {
      return conferenceRoomRequestDAO.deleteRow((ConferenceRoomRequest) request);
    } else if (request instanceof EmployeeUser) {
      return employeeDao.deleteRow((EmployeeUser) request);
    } else if (request instanceof FlowerDeliveryRequest) {
      return flowerDeliveryRequestDAO.deleteRow((FlowerDeliveryRequest) request);
    } else if (request instanceof FurnitureDeliveryRequest) {
      return furnitureDeliveryRequestDAO.deleteRow((FurnitureDeliveryRequest) request);
    } else if (request instanceof MealRequest) {
      return mealRequestDAO.deleteRow((MealRequest) request);
    } else if (request instanceof OfficeSuppliesRequest) {
      return officeSuppliesRequestDAO.deleteRow((OfficeSuppliesRequest) request);
    } else if (request instanceof AdminUser) {
      return adminDao.deleteRow((AdminUser) request);
    } else if (request instanceof EmployeeUser) {
      return employeeDao.deleteRow((EmployeeUser) request);
    } else if (request instanceof Login) {
      return loginDao.deleteRow((Login) request);
    } else if (request instanceof Alert) {
      return alertDao.deleteRow((Alert) request);
    } else if (request instanceof Sign) {
      return signDao.deleteRow((Sign) request);
    } else {
      return null;
    }
  }

  public static IOrm updateRow(IOrm request) {
    if (request instanceof Node) {
      return nodeDao.updateRow((Node) request, (Node) request);
    } else if (request instanceof Edge) {
      return edgeDao.updateRow((Edge) request, (Edge) request);
    } else if (request instanceof LocationName) {
      return locationNameDao.updateRow((LocationName) request, (LocationName) request);
    } else if (request instanceof Move) {
      return moveDao.updateRow((Move) request, (Move) request);
    } else if (request instanceof ConferenceRoomRequest) {
      return conferenceRoomRequestDAO.updateRow(
          (ConferenceRoomRequest) request, (ConferenceRoomRequest) request);
    } else if (request instanceof EmployeeUser) {
      return employeeDao.updateRow((EmployeeUser) request, (EmployeeUser) request);
    } else if (request instanceof FlowerDeliveryRequest) {
      return flowerDeliveryRequestDAO.updateRow(
          (FlowerDeliveryRequest) request, (FlowerDeliveryRequest) request);
    } else if (request instanceof FurnitureDeliveryRequest) {
      return furnitureDeliveryRequestDAO.updateRow(
          (FurnitureDeliveryRequest) request, (FurnitureDeliveryRequest) request);
    } else if (request instanceof MealRequest) {
      return mealRequestDAO.updateRow((MealRequest) request, (MealRequest) request);
    } else if (request instanceof OfficeSuppliesRequest) {
      return officeSuppliesRequestDAO.updateRow(
          (OfficeSuppliesRequest) request, (OfficeSuppliesRequest) request);
    } else if (request instanceof AdminUser) {
      return adminDao.updateRow((AdminUser) request, (AdminUser) request);
    } else if (request instanceof EmployeeUser) {
      return employeeDao.updateRow((EmployeeUser) request, (EmployeeUser) request);
    } else if (request instanceof Login) {
      return loginDao.updateRow((Login) request, (Login) request);
    } else if (request instanceof Alert) {
      return alertDao.updateRow((Alert) request, (Alert) request);
    } else if (request instanceof Sign) {
      return signDao.updateRow((Sign) request, (Sign) request);
    } else {
      return null;
    }
  }
}
