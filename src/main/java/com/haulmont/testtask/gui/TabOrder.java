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

/**
 * Класс отвечает за графическое представление заказов и работу с ними
 *
 * @author Shakirov Anton
 */
public class TabOrder {

    /**
     * Метод возвращает главный слой для работы с заказами
     * @return
     */
    public VerticalLayout tabOrder(){

        //Добавляем кнопки
        HorizontalLayout buttons = new HorizontalLayout();
        buttons.setSpacing(true);
        buttons.setHeight("70");
        buttons.setWidth("64.7%");

        //Создаем кнопки редактирования
        Button addOrder = new Button("Добавить");
        addOrder.addStyleName(ValoTheme.BUTTON_LARGE);
        addOrder.addStyleName(ValoTheme.BUTTON_FRIENDLY);
        Button updateOrder = new Button("Изменить");
        Button deleteOrder = new Button("Удалить");
        deleteOrder.setStyleName(ValoTheme.BUTTON_DANGER);

        //Добавляем созданные кнопки на слой для кнопок
        buttons.addComponent(addOrder);
        buttons.setComponentAlignment(addOrder, Alignment.BOTTOM_LEFT);

        //Кнопки редактирования и удаления помещаем в отдельный слой, чтобы они стояли рядом
        HorizontalLayout twoButtons = new HorizontalLayout();
        twoButtons.addComponent(updateOrder);
        twoButtons.addComponent(deleteOrder);
        twoButtons.setSpacing(true);

        //Слой с кнопками создан
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
        grid.setSizeFull();
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


        //Содержание полного текста описания заказа
        TextArea fullDescription = new TextArea();
        fullDescription.setEnabled(false);
        fullDescription.setWidth("64.7%");
        fullDescription.setHeight("40");
        fullDescription.setStyleName(ValoTheme.TEXTAREA_LARGE);
        fullDescription.setVisible(false);

        //Показываем полное описание заказа при выделении строки
        grid.addSelectionListener(new SelectionEvent.SelectionListener() {
            @Override
            public void select(SelectionEvent event) {

                //Если выбрана строка и описание не помещается в колонку, то выводим отдельную надпись с полным описанием
                if (grid.isSelected(grid.getSelectedRow()))
                    if (grid.getContainerDataSource().getItem(grid.getSelectedRow()).getItemProperty("Описание").getValue().toString().length() > grid.getColumn("Описание").getWidth() / 10) {
                        fullDescription.setValue("Полное описание заказа: " + grid.getContainerDataSource().getItem(grid.getSelectedRow()).getItemProperty("Описание").getValue().toString());
                        fullDescription.setVisible(true);
                        return;
                    }
                fullDescription.setVisible(false);
            }
        });


        //Устанавливаем действия на нажатия кнопок
        addOrder.addClickListener(event -> grid.getUI().getUI().addWindow(new EditOrderTable().addOrder(grid, fullDescription)));

        updateOrder.addClickListener(event -> {
            //Проверка, что изменяем выделенную строку
            if (grid.getSelectedRow() != null)
                grid.getUI().getUI().addWindow(new EditOrderTable().updateOrder(grid, fullDescription));
        });

        deleteOrder.addClickListener(event -> new EditOrderTable().deleteOrder(grid));

        //Создаем форму с фильтром по заказам
        FormLayout filter = new FormLayout();
        filter.addComponent(new EditOrderTable().filter(grid));

        //Создаем верхний слой с кнопками
        HorizontalLayout horizontalLayoutTop = new HorizontalLayout();
        horizontalLayoutTop.addComponent(buttons);
        horizontalLayoutTop.setComponentAlignment(buttons, Alignment.TOP_LEFT);
        horizontalLayoutTop.setWidth(100.0f, Sizeable.Unit.PERCENTAGE);


        //Создаем нижний слой с таблицей и фильтром
        HorizontalLayout horizontalLayoutBottom = new HorizontalLayout();
        horizontalLayoutBottom.setSpacing(true);
        horizontalLayoutBottom.setWidth(130.0f, Sizeable.Unit.PERCENTAGE);
        horizontalLayoutBottom.setHeight("100%");
        horizontalLayoutBottom.addComponent(grid);
        horizontalLayoutBottom.setComponentAlignment(grid, Alignment.TOP_LEFT);
        horizontalLayoutBottom.addComponent(filter);

        //Создаем главный слой для работы с заказами
        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setHeightUndefined();
        verticalLayout.setSpacing(true);

        verticalLayout.addComponent(horizontalLayoutTop);
        verticalLayout.addComponent(horizontalLayoutBottom);
        verticalLayout.addComponent(fullDescription);

        return  verticalLayout;
    }

}
