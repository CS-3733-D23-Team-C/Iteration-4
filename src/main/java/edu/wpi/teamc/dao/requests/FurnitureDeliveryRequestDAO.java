package edu.wpi.teamc.dao.requests;

import edu.wpi.teamc.dao.DBConnection;
import edu.wpi.teamc.dao.IDao;
import java.sql.SQLException;
import java.util.List;

public class FurnitureDeliveryRequestDAO implements IDao<FurnitureDeliveryRequest> {
  DBConnection db = new DBConnection();

  @Override
  public List<FurnitureDeliveryRequest> fetchAllObjects() throws SQLException {
    return null;
  }

  @Override
  public int updateRow(FurnitureDeliveryRequest orm, FurnitureDeliveryRequest repl)
      throws SQLException {
    return 0;
  }

  @Override
  public int addRow(FurnitureDeliveryRequest orm) {
    return 0;
  }

  @Override
  public int deleteRow(FurnitureDeliveryRequest orm) throws SQLException {
    return 0;
  }
}
