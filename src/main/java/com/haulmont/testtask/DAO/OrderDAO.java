package com.haulmont.testtask.DAO;

import com.haulmont.testtask.entity.Order;

import java.sql.*;

public class OrderDAO {

    static private PreparedStatement preparedStatement;
    static private String addOrder = "INSERT INTO orders (description, clientID, dataOfCreation, dateOfCompletion, price, status) VALUES (?,?,?,?,?,?)";
    static private String deleteOrder = "DELETE FROM orders WHERE id= ?";

    static public void seeTable(){
        try {
            String query = "SELECT * FROM orders";
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

    static public void addOrder(long clientID, Order order){
        try {
            preparedStatement = Database.connection.prepareStatement(addOrder);
            preparedStatement.setString(1,order.getDescription());
            preparedStatement.setLong(2,clientID);
            preparedStatement.setDate(3, order.getDataOfCreation());
            preparedStatement.setDate(4, order.getDataOfComplrtion());
            preparedStatement.setDouble(5, order.getPrice());
            preparedStatement.setString(6, order.getStatusDescription());
            preparedStatement.executeUpdate();

            System.out.println("Добавлен новый заказ");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static public void addOrder(long clientID, String description, Date dataOfCreation, Date dataOfCompletion, double price, String statusDescription ){
        try {
            preparedStatement = Database.connection.prepareStatement(addOrder);
            preparedStatement.setString(1, description);
            preparedStatement.setLong(2, clientID);
            preparedStatement.setDate(3, dataOfCreation);
            preparedStatement.setDate(4, dataOfCompletion);
            preparedStatement.setDouble(5, price);
            preparedStatement.setString(6, statusDescription);
            preparedStatement.executeUpdate();

            System.out.println("Добавлен новый заказ");
        } catch (SQLException e) {
            System.out.println("Database massage: Новый заказ не был добавлен. Введены некорректные данные!");
        }
    }

    static public void deleteOrder(long id){

        try {
            preparedStatement = Database.connection.prepareStatement(deleteOrder);
            preparedStatement.setLong(1,id);
            preparedStatement.executeUpdate();

            System.out.println("Удален заказ с id = " + id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
