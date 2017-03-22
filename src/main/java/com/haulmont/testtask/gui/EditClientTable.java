package com.haulmont.testtask.gui;


import com.vaadin.server.Sizeable;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Window;
import com.vaadin.annotations.Theme;
import com.vaadin.data.Item;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

public class EditClientTable {

    public Window addClient() {
        final Window window = new Window("Добавление нового клиента");
        window.setModal(true);
        window.center();
        window.setWidth("350");
        window.setHeight("300");

        final FormLayout addClient = new FormLayout();
        addClient.setMargin(true);
        addClient.setSizeFull();

        final TextField surname = new TextField("Фамилия", "");
        surname.setSizeFull();
        surname.setRequired(true);

        final TextField firstName = new TextField("Имя", "");
        firstName.setSizeFull();
        firstName.setRequired(true);

        final TextField patronymic = new TextField("Отчество", "");
        patronymic.setSizeFull();

        final TextField number = new TextField("Телефон", "");
        number.setSizeFull();
        number.setRequired(true);

        final Button saveClient = new Button("Добавить");
        final Button cancel = new Button("Отмена");

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

    public Window updateClient() {
        final Window window = new Window("Изменить данные клиента");
        window.setModal(true);
        window.center();
        window.setWidth("350");
        window.setHeight("300");

        final FormLayout addClient = new FormLayout();
        addClient.setMargin(true);
        addClient.setSizeFull();

        final TextField surname = new TextField("Фамилия", "");
        surname.setSizeFull();
        surname.setRequired(true);

        final TextField firstName = new TextField("Имя", "");
        firstName.setSizeFull();
        firstName.setRequired(true);

        final TextField patronymic = new TextField("Отчество", "");
        patronymic.setSizeFull();

        final TextField number = new TextField("Телефон", "");
        number.setSizeFull();
        number.setRequired(true);

        final Button saveClient = new Button("Применить");
        final Button cancel = new Button("Отмена");

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

}
