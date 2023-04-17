package edu.wpi.teamc.dao.users;

import edu.wpi.teamc.dao.IDao;

import java.sql.SQLException;
import java.util.List;

public class EmployeeLoginDao implements IDao<EmployeeLogin> {
    @Override
    public List<EmployeeLogin> fetchAllObjects() throws SQLException {
        return null;
    }

    @Override
    public EmployeeLogin updateRow(EmployeeLogin type, EmployeeLogin type2) throws SQLException {
        return null;
    }

    @Override
    public EmployeeLogin addRow(EmployeeLogin type) {
        return null;
    }

    @Override
    public EmployeeLogin deleteRow(EmployeeLogin type) throws SQLException {
        return null;
    }
}
