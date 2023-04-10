package edu.wpi.teamc.dao.requests;
import edu.wpi.teamc.dao.IDao;

import java.sql.SQLException;
import java.util.List;

public class FlowerDeliveryRequestDAO implements IDao<FlowerDeliveryRequest> {

    List<FlowerDeliveryRequest> fetchAllObjects() throws SQLException;

    int updateRow(FlowerDeliveryRequest type, FlowerDeliveryRequest type2) throws SQLException;

     int updateDBRow(FlowerDeliveryRequest type) throws SQLException;

    int addRow(FlowerDeliveryRequest type);

    int deleteRow(FlowerDeliveryRequest type) throws SQLException;

}
