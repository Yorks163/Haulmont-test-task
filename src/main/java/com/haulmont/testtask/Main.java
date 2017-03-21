package com.haulmont.testtask;

import com.haulmont.testtask.DAO.ClientDAO;
import com.haulmont.testtask.DAO.Database;
import com.haulmont.testtask.DAO.OrderDAO;
import com.haulmont.testtask.entity.Client;
import com.haulmont.testtask.entity.Order;

import java.util.List;

 /* Класс Main предназначен для выполнения
    тестирования работы с БД и с сущностями
 */
public class Main {

    public static void main (String[] args){

        Database.startDatabase();

        //ClientDAO.addClient("Скайуокер","Энакин","","0001");
        ClientDAO.seeTable();
        //ClientDAO.deleteClient(2);
        //Client client = ClientDAO.getClient(5);
        //System.out.println(client.getFirstName());
        //System.out.println(client.getId());

        //OrderDAO.addOrder(4, "Усовершенствовать Спейдер", new java.util.Date("02/02/2017"), new java.util.Date("03/03/2017"), 112358, "Запланирован");
        //OrderDAO.deleteOrder(2);
        OrderDAO.seeTable();

        //List<Client> clients = ClientDAO.getAllClient();
        //System.out.println(clients.get(1).getId());
        //System.out.println(clients.size());

        //List<Order> orders = OrderDAO.getAllOrder();
        //System.out.println(orders.get(0).getDescription());


        Database.closeDatabase();
    }
}
