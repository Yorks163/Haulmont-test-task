package com.haulmont.testtask.DAO;

import com.haulmont.testtask.entity.Client;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс осуществляет работу с таблицой Клиентов в БД
 *
 * @author Shakirov Anton
 */
public class ClientDAO {

    // Создаем строки с запросами в БД
    static private PreparedStatement preparedStatement;
    static private String addCustomer    = "INSERT INTO client (surname, firstName, patronymic, number) VALUES (?,?,?,?)";
    static private String deleteCustomer = "DELETE FROM client WHERE id = ?";
    static private String getCustomer    = "SELECT * FROM client WHERE id = ?";
    static private String updateCustomer = "UPDATE client SET SURNAME = ?, FIRSTNAME = ?, PATRONYMIC = ?, NUMBER = ? WHERE id = ?";

    /**
     * Метод добавления нового клиента в БД
     * @param client
     */
    static public void addClient(Client client){
        try {
            preparedStatement = Database.connection.prepareStatement(addCustomer);
            preparedStatement.setString(1, client.getSurname());
            preparedStatement.setString(2, client.getFirstName());
            preparedStatement.setString(3, client.getPatronymic());
            preparedStatement.setString(4, client.getNumber());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Удаление клиента из БД по его ID
     * @param id
     * @return
     */
    static public int deleteClient(long id){

        try {
            preparedStatement = Database.connection.prepareStatement(deleteCustomer);
            preparedStatement.setLong(1,id);
            preparedStatement.executeUpdate();
            return 0;
        } catch (SQLIntegrityConstraintViolationException e) {
            //Возвращаем код ошибки в метод. Если для клиента существует заказ, то его не удаляем
            return 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return 2;
        }
    }

    /**
     * Получения всех объектов из таблицы клиентов
     * @return
     */
    static public List<Client> getAllClient(){
        try {
            String query = "SELECT * FROM client";
            ResultSet resultSet = Database.statement.executeQuery(query);

            List<Client> clients = new ArrayList<>();

            Client client = new Client();
            while (resultSet.next()) {
                client.setId(resultSet.getLong(1));
                client.setSurname(resultSet.getString(2));
                client.setFirstName(resultSet.getString(3));
                client.setPatronymic( resultSet.getString(4));
                client.setNumber(resultSet.getString(5));

                clients.add(new Client(client));
            }

            return clients;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Database massage: В базе нет клиентов!");
            List<Client> clients = null;
            return clients;
        }
    }

    /**
     * Обновление данных клиента в БД
     * @param client
     */
    static public void updateClient (Client client){
        try {
            preparedStatement = Database.connection.prepareStatement(updateCustomer);
            preparedStatement.setString(1, client.getSurname());
            preparedStatement.setString(2, client.getFirstName());
            preparedStatement.setString(3, client.getPatronymic());
            preparedStatement.setString(4, client.getNumber());
            preparedStatement.setLong(5, client.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
