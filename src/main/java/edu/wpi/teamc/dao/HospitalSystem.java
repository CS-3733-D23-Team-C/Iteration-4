package edu.wpi.teamc.dao;

import edu.wpi.teamc.dao.map.*;
import edu.wpi.teamc.dao.requests.*;
import edu.wpi.teamc.dao.users.Employee;
import edu.wpi.teamc.dao.users.EmployeeDao;
import java.sql.SQLException;
import java.util.List;

//facade pattern

public class HospitalSystem {
  // Map DAOs
  private NodeDao nodeDao;
  private EdgeDao edgeDao;
  private LocationDao locationDao;
  private MoveDao moveDao;

  // Service Request DAOs
  private ConferenceRoomRequestDAO conferenceRoomRequestDAO ;
  private FlowerDeliveryRequestDAO flowerDeliveryRequestDAO;
  private FurnitureDeliveryRequestDAO furnitureDeliveryRequestDAO;
  private MealRequestDAO mealRequestDAO;
  private OfficeSuppliesRequestDAO officeSuppliesRequestDAO;

  // User DAOs
  private EmployeeDao employeeDao;
   List<? extends IOrm> fetchAllObjects(IOrm request) {
    if (request instanceof Node) {
      return nodeDao.fetchAllObjects();
    } else if (request instanceof Edge) {
      return edgeDao.fetchAllObjects();
    } else if (request instanceof LocationName) {
      return locationDao.fetchAllObjects();
    } else if (request instanceof Move) {
      return moveDao.fetchAllObjects();
    } else if (request instanceof ConferenceRoomRequest) {
      return conferenceRoomRequestDAO.fetchAllObjects();
    } else if (request instanceof Employee) {
      return employeeDao.fetchAllObjects();
    } else if (request instanceof FlowerDeliveryRequest) {
      return flowerDeliveryRequestDAO.fetchAllObjects();
    } else if (request instanceof FurnitureDeliveryRequest) {
      return furnitureDeliveryRequestDAO.fetchAllObjects();
    } else if (request instanceof MealRequest) {
      return mealRequestDAO.fetchAllObjects();
    } else if (request instanceof OfficeSuppliesRequest) {
      return officeSuppliesRequestDAO.fetchAllObjects();
    }
    return null;
  }

   IOrm addRow(IOrm request) {
    if (request instanceof Node) {
      return nodeDao.addRow((Node) request);
    } else if (request instanceof Edge) {
      return edgeDao.addRow((Edge) request);
    } else if (request instanceof LocationName) {
      return locationDao.addRow((LocationName) request);
    } else if (request instanceof Move) {
      return moveDao.addRow((Move) request);
    } else if (request instanceof ConferenceRoomRequest) {
      return conferenceRoomRequestDAO.addRow((ConferenceRoomRequest) request);
    } else if (request instanceof Employee) {
      return employeeDao.addRow((Employee) request);
    } else if (request instanceof FlowerDeliveryRequest) {
      return flowerDeliveryRequestDAO.addRow((FlowerDeliveryRequest) request);
    } else if (request instanceof FurnitureDeliveryRequest) {
      return furnitureDeliveryRequestDAO.addRow((FurnitureDeliveryRequest) request);
    } else if (request instanceof MealRequest) {
      return mealRequestDAO.addRow((MealRequest) request);
    } else if (request instanceof OfficeSuppliesRequest) {
      return officeSuppliesRequestDAO.addRow((OfficeSuppliesRequest) request);
    } else {
      return null;
    }
  }

   IOrm deleteRow(IOrm request) {
    if (request instanceof Node) {
      return nodeDao.deleteRow((Node) request);
    } else if (request instanceof Edge) {
      return edgeDao.deleteRow((Edge) request);
    } else if (request instanceof LocationName) {
      return locationDao.deleteRow((LocationName) request);
    } else if (request instanceof Move) {
      return moveDao.deleteRow((Move) request);
    } else if (request instanceof ConferenceRoomRequest) {
      try {
        return conferenceRoomRequestDAO.deleteRow((ConferenceRoomRequest) request);
      } catch (SQLException e) {
        throw new RuntimeException(e);
      }
    } else if (request instanceof Employee) {
      return employeeDao.deleteRow((Employee) request);
    } else if (request instanceof FlowerDeliveryRequest) {
      return flowerDeliveryRequestDAO.deleteRow((FlowerDeliveryRequest) request);
    } else if (request instanceof FurnitureDeliveryRequest) {
      return furnitureDeliveryRequestDAO.deleteRow((FurnitureDeliveryRequest) request);
    } else if (request instanceof MealRequest) {
      return mealRequestDAO.deleteRow((MealRequest) request);
    } else if (request instanceof OfficeSuppliesRequest) {
      return officeSuppliesRequestDAO.deleteRow((OfficeSuppliesRequest) request);
    }
    return null;
  }

  public IOrm updateRow(IOrm request) {
    if (request instanceof Node) {
      return nodeDao.updateRow((Node) request, (Node) request);
    } else if (request instanceof Edge) {
      return edgeDao.updateRow((Edge) request, (Edge) request);
    } else if (request instanceof LocationName) {
      return locationDao.updateRow((LocationName) request, (LocationName) request);
    } else if (request instanceof Move) {
      return moveDao.updateRow((Move) request, (Move) request);
    } else if (request instanceof ConferenceRoomRequest) {
      return conferenceRoomRequestDAO.updateRow(
          (ConferenceRoomRequest) request, (ConferenceRoomRequest) request);
    } else if (request instanceof Employee) {
      return employeeDao.updateRow((Employee) request, (Employee) request);
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
    }
    return null;
  }
}
