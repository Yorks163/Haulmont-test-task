package com.haulmont.testtask.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Класс отвечает за соединение с БД
 *
 * @author Shakirov Anton
 */
public class Database {

    public static Connection connection;
    public static Statement  statement;

    /**
     * Подключение к БД
     */
    static public void startDatabase(){
        try {
            Class.forName("org.hsqldb.jdbcDriver");
        } catch (ClassNotFoundException e) {
            System.err.println("Database massage: Не удалось загрузить драйвер HSQLDB!");
            e.printStackTrace();
            System.exit(1);
        }

        // Создаем подключение к базе данных
        try {
            connection = DriverManager.getConnection(
                    "jdbc:hsqldb:file:src/main/database/car_workshop", "SA", "");

            statement  = connection.createStatement();
        } catch (SQLException e) {
            System.err.println("Database massage: Не удалось подключиться к базе данных!");
            e.printStackTrace();
            System.exit(2);
        }
    }

    /**
     * Отключение от БД
     */
   static  public void closeDatabase(){
        try {
            String query = "SHUTDOWN";
            statement.execute(query);
        } catch (SQLException e) {
            System.out.println("Database massage: База данных не закрыта!");
        }
    }

}
