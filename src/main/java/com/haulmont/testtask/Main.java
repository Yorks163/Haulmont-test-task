package com.haulmont.testtask;

import com.haulmont.testtask.DAO.ClientDAO;
import com.haulmont.testtask.DAO.Database;
import com.haulmont.testtask.DAO.OrderDAO;
import com.haulmont.testtask.entity.Client;

import java.util.Calendar;
import java.util.Date;

public class Main {

    public static void main (String[] args){

        Database.startDatabase();

        //ClientDAO.seeTable();
        //ClientDAO.addClient("Путин","Владимир","Владимирович","8(927)000-00-01");
        ClientDAO.seeTable();
        //ClientDAO.deleteClient(2);
        //Client client = ClientDAO.getClient(5);
        //System.out.println(client.getFirstName());
        //System.out.println(client.getId());

        OrderDAO.addOrder(1, "Ремонт президентского автомобиля", new java.sql.Date(new java.util.Date("01/01/2017").getTime()), new java.sql.Date(new java.util.Date("01/03/2017").getTime()), 102030.5, "Запланирован");
        //OrderDAO.deleteOrder(2);
        OrderDAO.seeTable();

        Database.closeDatabase();
    }
}
