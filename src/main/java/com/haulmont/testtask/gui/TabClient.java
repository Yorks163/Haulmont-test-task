package com.haulmont.testtask.gui;

import com.haulmont.testtask.DAO.ClientDAO;
import com.haulmont.testtask.entity.Client;
import com.vaadin.server.Sizeable;
import com.vaadin.ui.*;

import java.util.List;
import java.util.Locale;


public class TabClient {
    public HorizontalLayout tabClient(){


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
        for (int i=0; i<index; i++) {
            grid.addRow(clients.get(i).getId(), clients.get(i).getFirstName(), clients.get(i).getSurname(), clients.get(i).getPatronymic(), clients.get(i).getNumber());
        }

        HorizontalLayout layout = new HorizontalLayout();
        layout.addComponent(grid);


        final TextField clientID = new TextField("ID Клиента", "");
        clientID.setWidth(100.0f, Sizeable.Unit.PERCENTAGE);
        final TextField status = new TextField("Статус", "");
        status.setWidth(100.0f, Sizeable.Unit.PERCENTAGE);
        final TextField description = new TextField("Описание", "");
        description.setWidth(000.0f, Sizeable.Unit.PERCENTAGE);
        description.setMaxLength(500);

        FormLayout formLayout = new FormLayout();
        formLayout.addComponent(clientID);
        formLayout.addComponent(status);
        formLayout.addComponent(description);

        Button addClient = new Button("Добавить");
        addClient.addClickListener(event -> {
            layout.addComponent(formLayout);
        });
        Button updateClient = new Button("Изменить");
        updateClient.addClickListener(event -> {
            layout.addComponent(formLayout);
        });

        Button deleteClient = new Button("Удалить");
        deleteClient.addClickListener(event -> {
            layout.addComponent(formLayout);
        });

        VerticalLayout verticalLayout = new VerticalLayout();

        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.addComponent(addClient);
        horizontalLayout.addComponent(updateClient);
        horizontalLayout.addComponent(deleteClient);

        verticalLayout.addComponent(horizontalLayout);
        verticalLayout.addComponent(grid);
        layout.addComponent(verticalLayout);

        return  layout;
    }

}
