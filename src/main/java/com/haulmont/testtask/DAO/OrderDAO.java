package com.haulmont.testtask.DAO;

import com.haulmont.testtask.entity.Order;

import java.sql.*;
import java.util.*;

/**
 * Класс осуществляет работу с таблицой Заказов в БД
 *
 * @author Shakirov Anton
 */
public class OrderDAO {

    // Создаем строки с запросами в БД
    static private PreparedStatement preparedStatement;
    static private String addOrder    = "INSERT INTO orders (description, clientID, dataOfCreation, dateOfCompletion, price, status) VALUES (?,?,?,?,?,?)";
    static private String deleteOrder = "DELETE FROM orders WHERE id= ?";
    static private String updateOrder = "UPDATE orders SET description = ?, clientID = ?, dataOfCreation = ?, dateOfCompletion = ?, price = ?, status= ? WHERE id = ?";

    /**
     * Добавление нового заказа в БД
     * @param order
     */
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

        } catch (SQLException e) {
            System.out.println("Database massage: Заказ небыл добавлен!");
        }
    }

    /**
     * Удаление заказа из БД по ID
     * @param id
     */
    static public void deleteOrder(long id) {

        try {
            preparedStatement = Database.connection.prepareStatement(deleteOrder);
            preparedStatement.setLong(1,id);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Получение всех объектов из таблицы с заказами
     * @return
     */
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
            System.out.println("Database massage: В базе нет заказов!");
            List<Order> orders = null;
            return orders;
        }
    }

    /**
     * Обновляем заказ в БД
     * @param order
     */
    static public void updateOrder (Order order){
        try {
            preparedStatement = Database.connection.prepareStatement(updateOrder);

            preparedStatement.setString(1, order.getDescription());
            preparedStatement.setLong(2, order.getClientID());
            preparedStatement.setDate(3, order.getDataOfCreation());
            preparedStatement.setDate(4, order.getDataOfCompletion());
            preparedStatement.setDouble(5, order.getPrice());
            preparedStatement.setString(6, order.getStatusDescription());
            preparedStatement.setLong(7, order.getId());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Database massage: Заказ небыл исправлен. Введены некорректные данные");
        }
    }

}
