package com.haulmont.testtask.gui;

import com.haulmont.testtask.DAO.ClientDAO;
import com.haulmont.testtask.DAO.Database;
import com.haulmont.testtask.entity.Client;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.Sizeable;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.nio.file.Path;
import java.util.List;

public class TabClient {

    public VerticalLayout tabClient() {

        //Добавляем кнопки
        HorizontalLayout buttons = new HorizontalLayout();
        buttons.setSpacing(true);
        buttons.setHeight("70");
        buttons.setWidth("1180");

        Button addClient = new Button("Добавить");
        addClient.setIcon(VaadinIcons.USER_CHECK);
        addClient.addStyleName(ValoTheme.BUTTON_FRIENDLY);
        addClient.addStyleName(ValoTheme.BUTTON_LARGE);
        Button updateClient = new Button("Изменить");
        Button deleteClient = new Button("Удалить");
        deleteClient.setStyleName(ValoTheme.BUTTON_DANGER);

        buttons.addComponent(addClient);
        buttons.setComponentAlignment(addClient, Alignment.BOTTOM_LEFT);

        HorizontalLayout twoButtons = new HorizontalLayout();
        twoButtons.addComponent(updateClient);
        twoButtons.addComponent(deleteClient);
        twoButtons.setSpacing(true);

        buttons.addComponent(twoButtons);
        buttons.setComponentAlignment(twoButtons, Alignment.BOTTOM_RIGHT);

        //Создаем таблицу
        Grid grid = new Grid();
        grid.addColumn("ID", Long.class).setWidth(80);
        grid.addColumn("Фамилия", String.class);
        grid.addColumn("Имя", String.class);
        grid.addColumn("Отчество", String.class);
        grid.addColumn("Номер телефона", String.class);
        //grid.setCaption("Список клиентов");
        grid.setWidth("1280");
        grid.setHeight(1000, Sizeable.Unit.PERCENTAGE);
        grid.setEditorEnabled(false);
        grid.setStyleName(ValoTheme.TABLE_BORDERLESS);

        //Заполнение таблицы данными из Базы данных
        Database.startDatabase();
        List<Client> clients = ClientDAO.getAllClient();
        int index = clients.size();
        for (int i = 0; i < index; i++) {
            grid.addRow(clients.get(i).getId(), clients.get(i).getSurname(), clients.get(i).getFirstName(),
                        clients.get(i).getPatronymic(), clients.get(i).getNumber());
        }
        Database.closeDatabase();

        //Устанавливаем действия на нажатия кнопок
        addClient.addClickListener(event -> buttons.getUI().getUI().addWindow(new EditClientTable().addClient(grid)));

        updateClient.addClickListener(event -> {
            //Проверка, что изменяем выделенную строку
            if (grid.getSelectedRow() != null)
                buttons.getUI().getUI().addWindow(new EditClientTable().updateClient(grid));
        });

        deleteClient.addClickListener(event -> new EditClientTable().deleteClient(grid));

        VerticalLayout verticalLayoutClient = new VerticalLayout();
        verticalLayoutClient.addComponent(buttons);
        verticalLayoutClient.setComponentAlignment(buttons, Alignment.TOP_LEFT);
        verticalLayoutClient.addComponent(grid);
        verticalLayoutClient.setComponentAlignment(grid, Alignment.TOP_LEFT);
        verticalLayoutClient.setSpacing(true);
        verticalLayoutClient.setSizeFull();

        return verticalLayoutClient;
    }

}
