package edu.wpi.teamc.dao;

import edu.wpi.teamc.dao.map.EdgeDao;
import edu.wpi.teamc.dao.map.LocationDao;
import edu.wpi.teamc.dao.map.MoveDao;
import edu.wpi.teamc.dao.map.NodeDao;
import edu.wpi.teamc.dao.requests.*;

public class HospitalSystem {
  // Map DAOs
  NodeDao nodeDao = new NodeDao();
  EdgeDao edgeDao = new EdgeDao();
  LocationDao locationDao = new LocationDao();
  MoveDao moveDao = new MoveDao();

  // Service Request DAOs
  ConferenceRoomRequestDAO conferenceRoomRequestDAO = new ConferenceRoomRequestDAO();
  EmployeeDao employeeDao = new EmployeeDao();
  FlowerDeliveryRequestDAO flowerDeliveryRequestDAO = new FlowerDeliveryRequestDAO();
  FurnitureDeliveryRequestDAO furnitureDeliveryRequestDAO = new FurnitureDeliveryRequestDAO();
  MealRequestDAO mealRequestDAO = new MealRequestDAO();
  OfficeSuppliesRequestDAO officeSuppliesRequestDAO = new OfficeSuppliesRequestDAO();

  public HospitalSystem(
      NodeDao nodeDao,
      EdgeDao edgeDao,
      LocationDao locationDao,
      MoveDao moveDao,
      ConferenceRoomRequestDAO conferenceRoomRequestDAO,
      EmployeeDao employeeDao,
      FlowerDeliveryRequestDAO flowerDeliveryRequestDAO,
      FurnitureDeliveryRequestDAO furnitureDeliveryRequestDAO,
      MealRequestDAO mealRequestDAO,
      OfficeSuppliesRequestDAO officeSuppliesRequestDAO) {
    this.nodeDao = nodeDao;
    this.edgeDao = edgeDao;
    this.locationDao = locationDao;
    this.moveDao = moveDao;
    this.conferenceRoomRequestDAO = conferenceRoomRequestDAO;
    this.employeeDao = employeeDao;
    this.flowerDeliveryRequestDAO = flowerDeliveryRequestDAO;
    this.furnitureDeliveryRequestDAO = furnitureDeliveryRequestDAO;
    this.mealRequestDAO = mealRequestDAO;
    this.officeSuppliesRequestDAO = officeSuppliesRequestDAO;
  }
}
