package com.haulmont.testtask.DAO;

import java.sql.*;

public class ClientDAO {

    private Connection connection;
    private Statement  statement;
    //private PreparedStatement preparedStatement;
    //private String insertPurchase = "INSERT INTO \"PUBLIC\".\"PURCHASES\"\n VALUES (?,?,?,?,?)";

    public void startServer(){
        // Загружаем класс драйвера
        try {
            Class.forName("org.hsqldb.jdbcDriver");
        } catch (ClassNotFoundException e) {
            System.err.println("Не удалось загрузить драйвер HSQLDB!");
            e.printStackTrace();
            System.exit(1);
        }

        // Создаем подключение к базе данных
        try {
            this.connection = DriverManager.getConnection(
                    "jdbc:hsqldb:file:src/main/database/car_workshop", "SA", "");

            statement  = connection.createStatement();
            System.out.println("База данных подключена");
        } catch (SQLException e) {
            System.err.println("Не удалось подключиться к базе данных!");
            e.printStackTrace();
            System.exit(2);
        }

    }


    public void addClient(){
        try {
            String query = "INSERT INTO client \n" +
                           "( \"SURNAME\", \"FIRSTNAME\", \"PATRONYMIC\", \"NUMBER\") " +
                           "VALUES ('Медведев', 'Дмитрий','Владимирович','8(927)7654321' )";
            statement.executeUpdate(query);

            query = "INSERT INTO client \n" +
                    "( \"SURNAME\", \"FIRSTNAME\", \"PATRONYMIC\", \"NUMBER\") " +
                    "VALUES ('Обама', 'Барак','','1(111)999-99-99' )";
            statement.executeUpdate(query);



        // Упрощение добавления данных через PreparedStatement. НЕ ПОДКЛЮЧАЕТСЯ К ТАБЛИЦЕ
            /*preparedStatement = connection.prepareStatement(insertPurchase);
            preparedStatement.setString(1,"Хлебобулочные изделия");
            preparedStatement.setString(2,"Батон");
            preparedStatement.setDouble(3, 28.5);
            preparedStatement.setDate(4, java.sql.Date.valueOf("2017-01-04"));
            preparedStatement.setString(5,"Перекресток"); */


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void seeTable(){
        try {
            String query = "SELECT * FROM client";
            ResultSet resultSet = statement.executeQuery(query);

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


    public void closeServer(){
        try {
            String query = "SHUTDOWN";
            statement.execute(query);
            System.out.println("Таблица закрыта");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Таблица не закрыта!");
        }
    }
}
