package com.haulmont.testtask.gui;

import com.haulmont.testtask.DAO.Database;
import com.haulmont.testtask.DAO.OrderDAO;
import com.haulmont.testtask.entity.Order;

import com.vaadin.server.Sizeable;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import java.sql.Date;
import java.util.List;

public class TabOrder {
    public VerticalLayout tabOrder(){

        //Добавляем кнопки
        HorizontalLayout buttons = new HorizontalLayout();
        buttons.setSpacing(true);
        buttons.setHeight("70");
        buttons.setWidth("1180");

        Button addOrder = new Button("Добавить");
        addOrder.addStyleName(ValoTheme.BUTTON_LARGE);
        addOrder.addStyleName(ValoTheme.BUTTON_FRIENDLY);
        Button updateOrder = new Button("Изменить");
        Button deleteOrder = new Button("Удалить");
        deleteOrder.setStyleName(ValoTheme.BUTTON_DANGER);

        buttons.addComponent(addOrder);
        buttons.setComponentAlignment(addOrder, Alignment.BOTTOM_LEFT);

        HorizontalLayout twoButtons = new HorizontalLayout();
        twoButtons.addComponent(updateOrder);
        twoButtons.addComponent(deleteOrder);
        twoButtons.setSpacing(true);

        buttons.addComponent(twoButtons);
        buttons.setComponentAlignment(twoButtons, Alignment.BOTTOM_RIGHT);



        //Создаем таблицу
        Grid grid = new Grid();
        grid.addColumn("ID", Long.class).setWidth(80);
        grid.addColumn("Описание", String.class).setMaximumWidth(300);
        grid.addColumn("ID Клиента", Long.class);
        grid.addColumn("Дата создания", String.class);
        grid.addColumn("Дата окончания работ", String.class);
        grid.addColumn("Стоимость", Double.class);
        grid.addColumn("Статус", String.class);
        //grid.setCaption("Список заказов");
        grid.setWidth("1280");
        grid.setHeight("720");
        grid.setEditorEnabled(false);
        grid.setStyleName(ValoTheme.TABLE_BORDERLESS);


        //Заполнение таблицы данными из Базы данных
        Database.startDatabase();
        List<Order> orders = OrderDAO.getAllOrder();
        int index = orders.size();
        for (int i=0; i<index; i++) {
            grid.addRow(orders.get(i).getId(), orders.get(i).getDescription(), orders.get(i).getClientID(), orders.get(i).getDataOfCreation().toString(),
                        orders.get(i).getDataOfCompletion().toString(), orders.get(i).getPrice(), orders.get(i).getStatusDescription());
        }
        Database.closeDatabase();

        //Заполнение таблицы данными из Базы данных
        addOrder.addClickListener(event -> grid.getUI().getUI().addWindow(new EditOrderTable().addOrder(grid)));

        updateOrder.addClickListener(event -> {
            //Проверка, что изменяем выделенную строку
            if (grid.getSelectedRow() != null)
                grid.getUI().getUI().addWindow(new EditOrderTable().updateOrder(grid));
        });

        deleteOrder.addClickListener(event -> new EditOrderTable().deleteOrder(grid));


        FormLayout filter = new FormLayout();
        filter.addComponent(new EditOrderTable().filter(grid));


        HorizontalLayout horizontalLayoutTop = new HorizontalLayout();
        horizontalLayoutTop.addComponent(buttons);
        horizontalLayoutTop.setComponentAlignment(buttons, Alignment.TOP_LEFT);

        HorizontalLayout horizontalLayoutBottom = new HorizontalLayout();
        horizontalLayoutBottom.setSpacing(true);
        horizontalLayoutBottom.addComponent(grid);
        horizontalLayoutBottom.setComponentAlignment(grid, Alignment.TOP_LEFT);
        horizontalLayoutBottom.addComponent(filter);
        horizontalLayoutBottom.setComponentAlignment(filter, Alignment.TOP_RIGHT);

        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setHeightUndefined();
        verticalLayout.setSpacing(true);
        verticalLayout.addComponent(horizontalLayoutTop);
        verticalLayout.addComponent(horizontalLayoutBottom);

        return  verticalLayout;
    }

}
