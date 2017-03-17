package com.haulmont.testtask;

import com.haulmont.testtask.DAO.ClientDAO;

public class Main {

    public static void main (String[] args){

        ClientDAO clientDAO = new ClientDAO();
        clientDAO.startServer();
        clientDAO.addClient();
        clientDAO.seeTable();
        clientDAO.closeServer();

    }
}
