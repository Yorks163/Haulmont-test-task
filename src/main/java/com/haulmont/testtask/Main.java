package com.haulmont.testtask;

import com.haulmont.testtask.DAO.ClientDAO;
import com.haulmont.testtask.DAO.Database;
import com.haulmont.testtask.DAO.OrderDAO;
import com.haulmont.testtask.entity.Client;
import com.haulmont.testtask.entity.Order;

import java.sql.Date;
import java.util.List;

 /* Класс Main предназначен для выполнения
    тестирования работы с БД и с сущностями
 */
public class Main {

    public static void main (String[] args){

        //Database.startDatabase();

        //ClientDAO.addClient("Скайуокер","Энакин","","0001");
        //ClientDAO.seeTable();
        //Client client = new Client("2","2","2","2");
        //client.setId(17);
        //ClientDAO.updateClient(client);
        //ClientDAO.seeTable();
        //ClientDAO.deleteClient(2);
        //Client client = ClientDAO.getClient(5);
        //System.out.println(client.getFirstName());
        //System.out.println(client.getId());

        //OrderDAO.deleteOrder(2);
        //Order order = new Order("111", 20, new java.sql.Date(new java.util.Date("2017/01/01").getTime()) , new java.sql.Date(new java.util.Date("2017/02/02").getTime()), 111, "Выполнен");
        //System.out.println(order.getStatusDescription());
        //order.setStatusDescription("Принят клиентом");
        //System.out.println(order.getStatusDescription());
        //OrderDAO.seeTable();

        //List<Client> clients = ClientDAO.getAllClient();
        //System.out.println(clients.get(1).getId());
        //System.out.println(clients.size());

        //List<Order> orders = OrderDAO.getAllOrder();
        //System.out.println(orders.get(0).getDescription());


        //Database.closeDatabase();
    }
}
