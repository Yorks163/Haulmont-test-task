package com.haulmont.testtask.DAO;

import java.sql.*;

public class ClientDAO {

    private PreparedStatement preparedStatement;
    private String addCustomer = "INSERT INTO client (surname, firstName, patronymic, number) VALUES (?,?,?,?)";
    private String deleteCustomer = "DELETE FROM client WHERE id= ?";



    public void seeTable(){
        try {
            String query = "SELECT * FROM client";
            ResultSet resultSet = Database.statement.executeQuery(query);

            while (resultSet.next()) {
                System.out.println(resultSet.getLong(1) + " "
                        + resultSet.getString(2) + " "
                        + resultSet.getString(3) + " "
                        + resultSet.getString(4) + " "
                        + resultSet.getString(5));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addClient(){
        try {
            preparedStatement = Database.connection.prepareStatement(addCustomer);
            preparedStatement.setString(1,"AAA");
            preparedStatement.setString(2,"BBB");
            preparedStatement.setString(3, "CCC");
            preparedStatement.setString(4, "111");
            preparedStatement.executeUpdate();

            System.out.println("Добавлен новый клиент");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteClient(long id){

        try {
            preparedStatement = Database.connection.prepareStatement(deleteCustomer);
            preparedStatement.setLong(1,id);
            preparedStatement.executeUpdate();

            System.out.println("Удален клиент с id = " + id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
