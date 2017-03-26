package com.haulmont.testtask.gui;

import com.haulmont.testtask.DAO.Database;
import com.haulmont.testtask.DAO.OrderDAO;
import com.haulmont.testtask.entity.Order;

import com.vaadin.annotations.Widgetset;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.event.FieldEvents;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.MouseEvents;
import com.vaadin.event.SelectionEvent;
import com.vaadin.server.Sizeable;
import com.vaadin.ui.*;
import com.vaadin.ui.renderers.HtmlRenderer;
import com.vaadin.ui.renderers.ProgressBarRenderer;
import com.vaadin.ui.renderers.Renderer;
import com.vaadin.ui.themes.ValoTheme;
import javafx.scene.layout.Pane;

import java.sql.Date;
import java.util.List;

public class TabOrder {
    public VerticalLayout tabOrder(){

        //Добавляем кнопки
        HorizontalLayout buttons = new HorizontalLayout();
        buttons.setSpacing(true);
        buttons.setHeight("70");
        buttons.setWidth("64.7%");

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
        grid.addColumn("ID", Long.class).setWidth(60);
        grid.addColumn("Описание", String.class).setWidth(300);
        grid.addColumn("ID Клиента", Long.class).setWidth(130);
        grid.addColumn("Дата создания", String.class);
        grid.addColumn("Дата окончания работ", String.class);
        grid.addColumn("Стоимость", Double.class);
        grid.addColumn("Статус", String.class);
        grid.setWidth("100%");
        grid.setHeight("745");
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
        horizontalLayoutTop.setWidth(100.0f, Sizeable.Unit.PERCENTAGE);

        HorizontalLayout horizontalLayoutBottom = new HorizontalLayout();
        horizontalLayoutBottom.setSpacing(true);
        horizontalLayoutBottom.setWidth(130.0f, Sizeable.Unit.PERCENTAGE);
        horizontalLayoutBottom.addComponent(grid);
        horizontalLayoutBottom.setComponentAlignment(grid, Alignment.TOP_LEFT);




        Label textDescription = new Label("Полное описание заказа");
        textDescription.setStyleName(ValoTheme.LABEL_H3);
        textDescription.setWidth("250");

        TextArea fullDescription = new TextArea();
        fullDescription.setEnabled(false);
        fullDescription.setWidth("300");
        fullDescription.setHeightUndefined();
        fullDescription.setStyleName(ValoTheme.TEXTAREA_LARGE);

        FormLayout descriptionLayout = new FormLayout();
        descriptionLayout.addStyleName(ValoTheme.TABLE_BORDERLESS);
        descriptionLayout.setWidth("300");
        descriptionLayout.setVisible(false);

        descriptionLayout.addComponent(textDescription);
        descriptionLayout.addComponent(fullDescription);
        descriptionLayout.setComponentAlignment(fullDescription, Alignment.TOP_LEFT);


        VerticalLayout filterAndDescription = new VerticalLayout();
        filterAndDescription.addComponent(filter);
        filterAndDescription.addComponent(descriptionLayout);
        filterAndDescription.setWidth("400");
       // filterAndDescription.setComponentAlignment(filter, Alignment.TOP_LEFT);
        filterAndDescription.setComponentAlignment(descriptionLayout, Alignment.TOP_CENTER);



        horizontalLayoutBottom.addComponent(filterAndDescription);
        //horizontalLayoutBottom.setComponentAlignment(filterAndDescription, Alignment.TOP_LEFT);


        //Показываем полное описание заказа при выделении строки
        grid.addSelectionListener(new SelectionEvent.SelectionListener() {
            @Override
            public void select(SelectionEvent event) {

                //Если выбрана строка и описание не помещается в колонку, то выводим отдельную надпись с полным описанием
                if (grid.isSelected(grid.getSelectedRow()))
                    if (grid.getContainerDataSource().getItem(grid.getSelectedRow()).getItemProperty("Описание").getValue().toString().length() > grid.getColumn("Описание").getWidth() / 10) {
                        fullDescription.setValue(grid.getContainerDataSource().getItem(grid.getSelectedRow()).getItemProperty("Описание").getValue().toString());
                        descriptionLayout.setVisible(true);
                        return;
                    }
                descriptionLayout.setVisible(false);
            }
        });

        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setHeightUndefined();
        verticalLayout.setSpacing(true);
        verticalLayout.addComponent(horizontalLayoutTop);
        verticalLayout.addComponent(horizontalLayoutBottom);

        return  verticalLayout;
    }

}
