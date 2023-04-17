package edu.wpi.teamc.dao.users;

import edu.wpi.teamc.dao.IDao;

import java.sql.SQLException;
import java.util.List;

public class AdminLoginDao implements IDao<AdminLogin> {
    @Override
    public List<AdminLogin> fetchAllObjects() throws SQLException {
        return null;
    }

    @Override
    public AdminLogin updateRow(AdminLogin type, AdminLogin type2) throws SQLException {
        return null;
    }

    @Override
    public AdminLogin addRow(AdminLogin type) {
        return null;
    }

    @Override
    public AdminLogin deleteRow(AdminLogin type) throws SQLException {
        return null;
    }
}
