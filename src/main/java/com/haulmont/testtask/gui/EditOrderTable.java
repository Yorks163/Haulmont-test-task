package com.haulmont.testtask.gui;

import com.vaadin.ui.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class EditOrderTable {

    public Window addOrder() {
        final Window window = new Window("Добавление нового заказа");
        window.setModal(true);
        window.center();
        window.setWidth("450");
        window.setHeight("400");

        final FormLayout addOrder = new FormLayout();
        addOrder.setMargin(true);

        addOrder.setSizeFull();

        final TextField description = new TextField("Описание", "");
        description.setSizeFull();
        description.setRequired(true);

        final TextField clientId = new TextField("ID клиента", "");
        clientId.setSizeFull();
        clientId.setRequired(true);

        final DateField dataOfCreation = new DateField("Дата создания");
        dataOfCreation.setValue(new java.util.Date());
        dataOfCreation.setSizeFull();
        dataOfCreation.setRequired(true);

        final DateField dataOfCompletion = new DateField("Дата окончания");
        dataOfCompletion.setSizeFull();

        final TextField price = new TextField("Стоимость", "");
        price.setSizeFull();


        List<String> status = new ArrayList<>();
        status.add("Запланирован");
        status.add("Выполнен");
        status.add("Принят клиентом");
        ComboBox nativeSelect = new ComboBox("Статус", status);
        nativeSelect.setSizeFull();
        nativeSelect.setRequired(true);
        nativeSelect.setInputPrompt("Выберите статус");
        nativeSelect.setNullSelectionAllowed(false);

        final Button saveClient = new Button("Добавить");
        final Button cancel = new Button("Отмена");
        HorizontalLayout buttons = new HorizontalLayout();
        buttons.addComponent(saveClient);
        buttons.addComponent(cancel);

        addOrder.addComponent(description);
        addOrder.addComponent(clientId);
        addOrder.addComponent(dataOfCreation);
        addOrder.addComponent(dataOfCompletion);
        addOrder.addComponent(price);
        addOrder.addComponent(nativeSelect);
        addOrder.addComponent(buttons);

        window.setContent(addOrder);
        return window;
    }

    public Window updateOrder() {
        final Window window = new Window("Изменение заказа");
        window.setModal(true);
        window.center();
        window.setWidth("450");
        window.setHeight("400");

        final FormLayout addOrder = new FormLayout();
        addOrder.setMargin(true);

        addOrder.setSizeFull();

        final TextField description = new TextField("Описание", "");
        description.setSizeFull();
        description.setRequired(true);

        final TextField clientId = new TextField("ID клиента", "");
        clientId.setSizeFull();
        clientId.setRequired(true);

        final DateField dataOfCreation = new DateField("Дата создания");
        dataOfCreation.setValue(new java.util.Date());
        dataOfCreation.setSizeFull();
        clientId.setRequired(true);

        final DateField dataOfCompletion = new DateField("Дата окончания");
        dataOfCompletion.setSizeFull();

        final TextField price = new TextField("Стоимость", "");
        price.setSizeFull();


        List<String> status = new ArrayList<>();
        status.add("Запланирован");
        status.add("Выполнен");
        status.add("Принят клиентом");
        ComboBox nativeSelect = new ComboBox("Статус", status);
        nativeSelect.setSizeFull();
        nativeSelect.setRequired(true);
        nativeSelect.setInputPrompt("Выберите статус");
        nativeSelect.setNullSelectionAllowed(false);

        final Button saveClient = new Button("Изменить");
        final Button cancel = new Button("Отмена");
        HorizontalLayout buttons = new HorizontalLayout();
        buttons.addComponent(saveClient);
        buttons.addComponent(cancel);

        addOrder.addComponent(description);
        addOrder.addComponent(clientId);
        addOrder.addComponent(dataOfCreation);
        addOrder.addComponent(dataOfCompletion);
        addOrder.addComponent(price);
        addOrder.addComponent(nativeSelect);
        addOrder.addComponent(buttons);

        window.setContent(addOrder);
        return window;
    }

}
