package edu.wpi.teamc.dao;

import edu.wpi.teamc.dao.map.*;
import edu.wpi.teamc.dao.requests.*;
import edu.wpi.teamc.dao.users.Employee;
import edu.wpi.teamc.dao.users.EmployeeDao;

import java.sql.SQLException;
import java.util.List;

public class HospitalSystem {
  // Map DAOs
  NodeDao nodeDao = new NodeDao();
  EdgeDao edgeDao = new EdgeDao();
  LocationDao locationDao = new LocationDao();
  MoveDao moveDao = new MoveDao();

  // Service Request DAOs
  ConferenceRoomRequestDAO conferenceRoomRequestDAO = new ConferenceRoomRequestDAO();
  FlowerDeliveryRequestDAO flowerDeliveryRequestDAO = new FlowerDeliveryRequestDAO();
  FurnitureDeliveryRequestDAO furnitureDeliveryRequestDAO = new FurnitureDeliveryRequestDAO();
  MealRequestDAO mealRequestDAO = new MealRequestDAO();
  OfficeSuppliesRequestDAO officeSuppliesRequestDAO = new OfficeSuppliesRequestDAO();

  //User DAOs
  EmployeeDao employeeDao = new EmployeeDao();

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

  public List<? extends IOrm> fetchAllObjects(IOrm request) {
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
  public IOrm addRow(IOrm request) {
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

  public IOrm deleteRow(IOrm request) {
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
      return conferenceRoomRequestDAO.updateRow((ConferenceRoomRequest) request, (ConferenceRoomRequest) request);
    } else if (request instanceof Employee) {
      return  employeeDao.updateRow((Employee) request, (Employee) request);
    } else if (request instanceof FlowerDeliveryRequest) {
      return flowerDeliveryRequestDAO.updateRow((FlowerDeliveryRequest) request, (FlowerDeliveryRequest) request);
    } else if (request instanceof FurnitureDeliveryRequest) {
      return furnitureDeliveryRequestDAO.updateRow((FurnitureDeliveryRequest) request, (FurnitureDeliveryRequest) request);
    } else if (request instanceof MealRequest) {
      return mealRequestDAO.updateRow((MealRequest) request, (MealRequest) request);
    } else if (request instanceof OfficeSuppliesRequest) {
      return officeSuppliesRequestDAO.updateRow((OfficeSuppliesRequest) request, (OfficeSuppliesRequest) request);
    }
    return null;
  }

}
