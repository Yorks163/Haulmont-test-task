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

        Button addOrder = new Button("Добавить");
        addOrder.setStyleName(ValoTheme.BUTTON_LARGE);
        Button updateOrder = new Button("Изменить");
        Button deleteOrder = new Button("Удалить");
        deleteOrder.setStyleName(ValoTheme.BUTTON_DANGER);

        buttons.addComponent(addOrder);
        buttons.addComponent(updateOrder);
        buttons.addComponent(deleteOrder);

        //Создаем таблицу
        Grid grid = new Grid();
        grid.addColumn("ID", Long.class).setWidth(80);
        grid.addColumn("Описание", String.class);
        grid.addColumn("ID Клиента", Long.class);
        grid.addColumn("Дата создания", String.class);
        grid.addColumn("Дата окончания работ", String.class);
        grid.addColumn("Стоимость", Double.class);
        grid.addColumn("Статус", String.class);
        grid.setCaption("Список заказов");
        grid.setWidth("1280");
        grid.setHeight("720");
        grid.setEditorEnabled(false);

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
        filter.setMargin(true);
        filter.addStyleName("outlined");
        filter.setCaption("Фильтр");
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

        return  verticalLayout;
    }

}
