package com.haulmont.testtask.DAO;

import com.haulmont.testtask.entity.Order;

import java.sql.*;
import java.util.*;

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

    static public void addOrder(Order order){
        try {
            preparedStatement = Database.connection.prepareStatement(addOrder);
            preparedStatement.setString(1, order.getDescription());
            preparedStatement.setLong(2, order.getClientID());
            preparedStatement.setDate(3, order.getDataOfCreation());
            preparedStatement.setDate(4, order.getDataOfCompletion());
            preparedStatement.setDouble(5, order.getPrice());
            preparedStatement.setString(6, order.getStatusDescription());
            preparedStatement.executeUpdate();

            System.out.println("Добавлен новый заказ");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static public void addOrder(long clientID, String description, java.util.Date dataOfCreation, java.util.Date dataOfCompletion, double price, String statusDescription ){
        try {
            preparedStatement = Database.connection.prepareStatement(addOrder);
            preparedStatement.setString(1, description);
            preparedStatement.setLong(2, clientID);
            preparedStatement.setDate(3, new java.sql.Date(dataOfCreation.getTime()));
            preparedStatement.setDate(4, new java.sql.Date(dataOfCompletion.getTime()));
            preparedStatement.setDouble(5, price);
            preparedStatement.setString(6, statusDescription);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Database massage: Новый заказ не был добавлен. Введены некорректные данные!");
        }
    }

    static public void deleteOrder(long id) {

        try {
            preparedStatement = Database.connection.prepareStatement(deleteOrder);
            preparedStatement.setLong(1,id);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static public List<Order> getAllOrder(){
        try {
            String query = "SELECT * FROM orders";
            ResultSet resultSet = Database.statement.executeQuery(query);

            List<Order> orders = new ArrayList<>();

            Order order = new Order();
            while (resultSet.next()) {
                order.setId(resultSet.getLong(1));
                order.setDescription(resultSet.getString(2));
                order.setClientID(resultSet.getLong(3));
                order.setDataOfCreation( resultSet.getDate(4));
                order.setDataOfCompletion(resultSet.getDate(5));
                order.setPrice(resultSet.getDouble(6));
                order.setStatusDescription(resultSet.getString(7));

                orders.add(new Order(order));
            }

            return orders;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Database massage: В базе нет заказов!");
            List<Order> orders = null;
            return orders;
        }
    }

}
