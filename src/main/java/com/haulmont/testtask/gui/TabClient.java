package com.haulmont.testtask.gui;

import com.haulmont.testtask.DAO.ClientDAO;
import com.haulmont.testtask.entity.Client;
import com.vaadin.server.Sizeable;
import com.vaadin.ui.*;

import java.util.List;
import java.util.Locale;


public class TabClient {

    public VerticalLayout tabClient() {

        HorizontalLayout buttons = new HorizontalLayout();

        Button addClient = new Button("Добавить");
        addClient.addClickListener(event -> buttons.getUI().getUI().addWindow(new EditClientTable().addClient()));

        Button updateClient = new Button("Изменить");
        updateClient.addClickListener(event -> buttons.getUI().getUI().addWindow(new EditClientTable().updateClient()));

        Button deleteClient = new Button("Удалить");
        deleteClient.addClickListener(event -> new Window("Delete"));

        buttons.addComponent(addClient);
        buttons.addComponent(updateClient);
        buttons.addComponent(deleteClient);


        Grid grid = new Grid();
        grid.addColumn("ID", Long.class).setWidth(80);
        grid.addColumn("Фамилия", String.class);
        grid.addColumn("Имя", String.class);
        grid.addColumn("Отчество", String.class);
        grid.addColumn("Номер телефона", String.class);
        grid.setCaption("Список клиентов");
        grid.setWidth("1280");
        grid.setHeight("720");
        grid.setEditorEnabled(false);

        //Заполнение таблицы данными из Базы данных
        List<Client> clients = ClientDAO.getAllClient();
        int index = clients.size();
        for (int i = 0; i < index; i++) {
            grid.addRow(clients.get(i).getId(), clients.get(i).getSurname(), clients.get(i).getFirstName(), clients.get(i).getPatronymic(), clients.get(i).getNumber());
        }


        VerticalLayout verticalLayoutClient = new VerticalLayout();
        verticalLayoutClient.addComponent(buttons);
        verticalLayoutClient.addComponent(grid);

        return verticalLayoutClient;
    }

}
