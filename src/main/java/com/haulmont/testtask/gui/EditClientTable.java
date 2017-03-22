package com.haulmont.testtask.gui;

import com.haulmont.testtask.DAO.ClientDAO;
import com.haulmont.testtask.DAO.Database;

import com.haulmont.testtask.entity.Client;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;


import java.util.List;

public class EditClientTable {

    public Window addClient(Grid grid) {
        final Window window = new Window("Добавление нового клиента");
        window.setModal(true);
        window.center();
        window.setWidth("400");
        window.setHeight("300");
        window.setClosable(false);
        window.setResizable(false);

        final FormLayout addClient = new FormLayout();
        addClient.setMargin(true);
        addClient.setSizeFull();


        final TextField surname = new TextField("Фамилия", "");
        surname.setSizeFull();
        surname.setRequired(true);
        surname.setMaxLength(50);

        final TextField firstName = new TextField("Имя", "");
        firstName.setSizeFull();
        firstName.setRequired(true);
        firstName.setMaxLength(50);

        final TextField patronymic = new TextField("Отчество", "");
        patronymic.setSizeFull();
        patronymic.setMaxLength(50);

        final TextField number = new TextField("Номер телефона", "");
        number.setSizeFull();
        number.setRequired(true);
        number.setMaxLength(50);

        final Button saveClient = new Button("Добавить");
        saveClient.setStyleName(ValoTheme.BUTTON_FRIENDLY);
        saveClient.addClickListener(clickEvent -> {
            Database.startDatabase();
            Client client = new Client(surname.getValue(), firstName.getValue(), patronymic.getValue(), number.getValue() );
            ClientDAO.addClient(client);
            List<Client> clients = ClientDAO.getAllClient();
            int index = clients.size();
            grid.addRow(clients.get(index-1).getId(), clients.get(index-1).getSurname(), clients.get(index-1).getFirstName(),
                        clients.get(index-1).getPatronymic(), clients.get(index-1).getNumber());
            Database.closeDatabase();
            window.close();
        });
        final Button cancel = new Button("Отмена");
        cancel.addClickListener(clickEvent -> window.close());

        HorizontalLayout buttons = new HorizontalLayout();
        buttons.addComponent(saveClient);
        buttons.addComponent(cancel);


        addClient.addComponent(surname);
        addClient.addComponent(firstName);
        addClient.addComponent(patronymic);
        addClient.addComponent(number);
        addClient.addComponent(buttons);

        window.setContent(addClient);
        return window;
    }

    public Window updateClient(Grid grid) {

        final Window window = new Window("Изменить данные клиента");
        window.setModal(true);
        window.center();
        window.setWidth("400");
        window.setHeight("300");
        window.setClosable(false);
        window.setResizable(false);

        final FormLayout addClient = new FormLayout();
        addClient.setMargin(true);
        addClient.setSizeFull();

        final TextField surname = new TextField("Фамилия", grid.getContainerDataSource().getItem(grid.getSelectedRow()).getItemProperty("Фамилия").getValue().toString());
        surname.setSizeFull();
        surname.setRequired(true);
        surname.setMaxLength(50);

        final TextField firstName = new TextField("Имя", grid.getContainerDataSource().getItem(grid.getSelectedRow()).getItemProperty("Имя").getValue().toString());
        firstName.setSizeFull();
        firstName.setRequired(true);
        firstName.setMaxLength(50);

        final TextField patronymic = new TextField("Отчество", grid.getContainerDataSource().getItem(grid.getSelectedRow()).getItemProperty("Отчество").getValue().toString());
        patronymic.setSizeFull();
        patronymic.setMaxLength(50);

        final TextField number = new TextField("Номер телефона", grid.getContainerDataSource().getItem(grid.getSelectedRow()).getItemProperty("Номер телефона").getValue().toString());
        number.setSizeFull();
        number.setRequired(true);
        number.setMaxLength(50);

        final Button saveClient = new Button("Применить");
        saveClient.setStyleName(ValoTheme.BUTTON_FRIENDLY);
        saveClient.addClickListener(clickEvent -> {
            surname.setNullSettingAllowed(true);
            surname.setNullRepresentation("0");
            surname.validate();
            Database.startDatabase();
            Long id = (Long) grid.getContainerDataSource().getItem(grid.getSelectedRow()).getItemProperty("ID").getValue();
            Client client = new Client(surname.getValue(), firstName.getValue(), patronymic.getValue(), number.getValue());
            client.setId(id);
            ClientDAO.updateClient(client);
            Database.closeDatabase();

            grid.getContainerDataSource().getItem(grid.getSelectedRow()).getItemProperty("Фамилия").setValue(surname.getValue());
            grid.getContainerDataSource().getItem(grid.getSelectedRow()).getItemProperty("Имя").setValue(firstName.getValue());
            grid.getContainerDataSource().getItem(grid.getSelectedRow()).getItemProperty("Отчество").setValue(patronymic.getValue());
            grid.getContainerDataSource().getItem(grid.getSelectedRow()).getItemProperty("Номер телефона").setValue(number.getValue());
            window.close();
        });

        final Button cancel = new Button("Отмена");
        cancel.addClickListener(clickEvent -> window.close());

        HorizontalLayout buttons = new HorizontalLayout();
        buttons.addComponent(saveClient);
        buttons.addComponent(cancel);


        addClient.addComponent(surname);
        addClient.addComponent(firstName);
        addClient.addComponent(patronymic);
        addClient.addComponent(number);
        addClient.addComponent(buttons);

        window.setContent(addClient);
        return window;
    }

    public  void deleteClient(Grid grid){
        //Проверка, что выбрана строка для удаления
        if (grid.getSelectedRow() == null) return;
        Database.startDatabase();

        Long id = (Long) grid.getContainerDataSource().getItem(grid.getSelectedRow()).getItemProperty("ID").getValue();
        Database.startDatabase();
        ClientDAO.deleteClient(id);
        Database.closeDatabase();

        grid.getContainerDataSource().removeItem(grid.getSelectedRow());
        Notification.show("Удален клиент с ID = "+id.toString());
    }
}
