package com.haulmont.testtask.DAO;

import java.sql.*;

public class OrderDAO {

    public void seeTable(){
        try {
            String query = "SELECT * FROM order";
            ResultSet resultSet = Database.statement.executeQuery(query);

            while (resultSet.next()) {
                System.out.println(resultSet.getLong(1) + " "
                        + resultSet.getString(2) + " "
                        + resultSet.getLong(3) + " "
                        + resultSet.getDate(4) + " "
                        + resultSet.getDate(5) + " "
                        + resultSet.getDouble(6) + " "
                        + resultSet.getString(7));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
