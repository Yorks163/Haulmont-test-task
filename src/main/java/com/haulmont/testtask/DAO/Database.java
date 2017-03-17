package com.haulmont.testtask.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {

    static Connection connection;
    static Statement  statement;

    static public void startDatabase(){
        try {
            Class.forName("org.hsqldb.jdbcDriver");
        } catch (ClassNotFoundException e) {
            System.err.println("Не удалось загрузить драйвер HSQLDB!");
            e.printStackTrace();
            System.exit(1);
        }

        // Создаем подключение к базе данных
        try {
            connection = DriverManager.getConnection(
                    "jdbc:hsqldb:file:src/main/database/car_workshop", "SA", "");

            statement  = connection.createStatement();
            System.out.println("База данных подключена");
        } catch (SQLException e) {
            System.err.println("Не удалось подключиться к базе данных!");
            e.printStackTrace();
            System.exit(2);
        }
    }

   static  public void closeDatabase(){
        try {
            String query = "SHUTDOWN";
            statement.execute(query);
            System.out.println("база данных закрыта");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("База данных не закрыта!");
        }
    }

}
