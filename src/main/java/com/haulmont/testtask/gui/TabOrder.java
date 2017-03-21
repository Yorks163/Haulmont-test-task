package com.haulmont.testtask.gui;

import com.haulmont.testtask.DAO.ClientDAO;
import com.haulmont.testtask.DAO.OrderDAO;
import com.haulmont.testtask.entity.Client;
import com.haulmont.testtask.entity.Order;
import com.vaadin.server.Sizeable;
import com.vaadin.ui.*;

import java.sql.Date;
import java.text.DateFormat;
import java.util.List;


public class TabOrder {
    public HorizontalLayout tabOrder(){

        Grid grid = new Grid();
        grid.addColumn("ID", Long.class).setWidth(80);
        grid.addColumn("Описание", String.class);
        grid.addColumn("ID Клиента", Long.class);
        grid.addColumn("Дата создания", Date.class);
        grid.addColumn("Дата окончания работ", Date.class);
        grid.addColumn("Стоимость", Double.class);
        grid.addColumn("Статус", String.class);
        grid.setCaption("Список заказов");
        grid.setWidth("1280");
        grid.setHeight("720");
        grid.setEditorEnabled(false);

        //Заполнение таблицы данными из Базы данных
        List<Order> orders = OrderDAO.getAllOrder();
        int index = orders.size();
        for (int i=0; i<index; i++) {
            grid.addRow(orders.get(i).getId(), orders.get(i).getDescription(), orders.get(i).getClientID(), orders.get(i).getDataOfCreation(),
                        orders.get(i).getDataOfCompletion(), orders.get(i).getPrice(), orders.get(i).getStatusDescription());
        }

        HorizontalLayout layout = new HorizontalLayout();

        Button addOrder = new Button("Добавить");
        addOrder.addClickListener(event -> layout.addComponent(new EditCustomer().editCustomer()));

        Button updateOrder = new Button("Изменить");
        updateOrder.addClickListener(event -> layout.addComponent(new EditCustomer().editCustomer()));

        Button deleteOrder = new Button("Удалить");
        deleteOrder.addClickListener(event -> layout.addComponent(new EditCustomer().editCustomer()));


        FormLayout filter = new FormLayout();
        filter.setMargin(true);
        //filter.addStyleName("outlined");
        filter.setSizeFull();

        final TextField clientID = new TextField("ID Клиента", "");
        clientID.setWidth(100.0f, Sizeable.Unit.PERCENTAGE);
        final TextField status = new TextField("Статус", "");
        status.setWidth(100.0f, Sizeable.Unit.PERCENTAGE);
        final TextField description = new TextField("Описание", "");
        description.setWidth(000.0f, Sizeable.Unit.PERCENTAGE);
        description.setMaxLength(500);


        filter.addComponent(clientID);
        filter.addComponent(status);
        filter.addComponent(description);
        filter.addComponent(new Button("Применить"));


        HorizontalLayout horizontalLayoutTop = new HorizontalLayout();
        horizontalLayoutTop.addComponent(addOrder);
        horizontalLayoutTop.addComponent(updateOrder);
        horizontalLayoutTop.addComponent(deleteOrder);

        HorizontalLayout horizontalLayoutBottom = new HorizontalLayout();
        horizontalLayoutBottom.addComponent(grid);
        horizontalLayoutBottom.addComponent(filter);

        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.addComponent(horizontalLayoutTop);
        verticalLayout.addComponent(horizontalLayoutBottom);


        layout.addComponent(verticalLayout);

        return  layout;
    }

}
