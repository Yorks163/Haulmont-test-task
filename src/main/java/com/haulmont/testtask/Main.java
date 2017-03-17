package com.haulmont.testtask;

import com.haulmont.testtask.DAO.ClientDAO;
import com.haulmont.testtask.DAO.Database;

public class Main {

    public static void main (String[] args){

        Database.startDatabase();

        ClientDAO clientDAO = new ClientDAO();
        clientDAO.seeTable();
        clientDAO.addClient();
        clientDAO.deleteClient(11);
        clientDAO.seeTable();

        Database.closeDatabase();
    }
}
