package edu.wpi.teamc.dao.requests;

import edu.wpi.teamc.dao.IDao;
import java.sql.SQLException;
import java.util.List;

public class FlowerDeliveryRequestDAO implements IDao<FlowerDeliveryRequest> {
  @Override
  public List<FlowerDeliveryRequest> fetchAllObjects() throws SQLException {
    return null;
  }

  @Override
  public FlowerDeliveryRequest updateRow(FlowerDeliveryRequest orm, FlowerDeliveryRequest repl)
      throws SQLException {
    return null;
  }

  @Override
  public FlowerDeliveryRequest addRow(FlowerDeliveryRequest orm) {
    return null;
  }

  @Override
  public FlowerDeliveryRequest deleteRow(FlowerDeliveryRequest orm) throws SQLException {
    return null;
  }
}
