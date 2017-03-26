package com.haulmont.testtask.gui;

import com.haulmont.testtask.DAO.ClientDAO;
import com.haulmont.testtask.DAO.Database;
import com.haulmont.testtask.DAO.OrderDAO;
import com.haulmont.testtask.entity.Client;
import com.haulmont.testtask.entity.Order;

import com.vaadin.data.Validator;
import com.vaadin.data.util.filter.SimpleStringFilter;
import com.vaadin.data.validator.*;
import com.vaadin.server.Sizeable;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import com.vaadin.data.Container.Filter;
import com.vaadin.data.Container.Filterable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EditOrderTable {

    public Window addOrder(Grid grid) {
        final Window window = new Window("Добавление нового заказа");
        window.setModal(true);
        window.center();
        window.setWidth("450");
        window.setHeight("400");
        window.setClosable(false);
        window.setResizable(false);

        final FormLayout addOrder = new FormLayout();
        addOrder.setMargin(true);
        addOrder.setSizeFull();

        final TextField description = new TextField("Описание", "");
        description.setSizeFull();
        description.setRequired(true);
        description.setMaxLength(500);

        final DateField dataOfCreation = new DateField("Дата создания");
        Date date =  new Date();
        dataOfCreation.setValue(date);
        dataOfCreation.setSizeFull();
        dataOfCreation.setRequired(true);
        dataOfCreation.addValidator(new DateRangeValidator("Заказ должен быть принят не более одного месяца назад и не позднее сегодняшнего дня",
                                    new Date(date.getYear(), date.getMonth()-1, date.getDate()),
                                    new java.sql.Date(date.getTime()), Resolution.YEAR));


        final DateField dataOfCompletion = new DateField("Дата окончания");
        dataOfCompletion.setValue(new Date(date.getYear(), date.getMonth(), date.getDate()+1));
        dataOfCompletion.setRequired(true);
        dataOfCompletion.setSizeFull();
        dataOfCompletion.addValidator(new DateRangeValidator("Заказ может быть завершен не раньше дня создания заказа и не позднее трех месяцев с текущего дня", new Date(date.getTime()),
                                                             new Date(date.getYear(), date.getMonth()+3, date.getDate()), Resolution.YEAR));

        //Обновляем валидатор при изменении даты создания заказа
        dataOfCreation.addValueChangeListener(event -> {
            //Если дата создания введена некорректно
            try {
                dataOfCompletion.removeAllValidators();
                Date updateDateOfCreation = dataOfCreation.getValue();
                dataOfCompletion.addValidator(new DateRangeValidator("Заказ может быть завершен не раньше дня создания заказа и не позднее трех месяцев с текущего дня",
                                                                    new Date(updateDateOfCreation.getYear(), updateDateOfCreation.getMonth(), updateDateOfCreation.getDate()),
                                                                    new Date(date.getYear(), date.getMonth()+3, date.getDate()), Resolution.YEAR));
            } catch (NullPointerException e) {
            }

        } );
        final TextField price = new TextField("Стоимость");
        price.setRequired(true);
        price.setSizeFull();
        //Стоимость возможно ввести как с разделителем ',' между целой и дробной частью, так и с '.'
        price.addValidator(new RegexpValidator("[0-9]{1,6}(\\.?\\,?[0-9]{1,2}){0,1}", true, "Стоимость не может быть отрицательной или быть равной 1 000 000 и более"));

        //Панель выбора клиента из существующих
        List<Long> allClientID = new ArrayList();
        Database.startDatabase();
        List<Client> clients = ClientDAO.getAllClient();
        int index = clients.size();
        for (int i=0; i<index; i++) {
            allClientID.add(clients.get(i).getId());
        }

        ComboBox clientID = new ComboBox("ID Клиента", allClientID);
        clientID.setSizeFull();
        clientID.setRequired(true);
        clientID.setInputPrompt("Выберите клиента по его ID");
        clientID.setNullSelectionAllowed(false);

        //Панель выбора статуса из трех возможных
        List<String> allStatus = new ArrayList<>();
        allStatus.add("Запланирован");
        allStatus.add("Выполнен");
        allStatus.add("Принят клиентом");
        ComboBox status = new ComboBox("Статус", allStatus);
        status.setSizeFull();
        status.setRequired(true);
        status.setInputPrompt("Выберите статус");
        status.setNullSelectionAllowed(false);

        final Button saveOrder = new Button("Добавить");
        saveOrder.setStyleName(ValoTheme.BUTTON_FRIENDLY);
        final Button cancel = new Button("Отмена");

        HorizontalLayout buttons = new HorizontalLayout();
        buttons.addComponent(saveOrder);
        buttons.addComponent(cancel);
        buttons.setSpacing(true);

        //Действия при нажатии на кнопки
        saveOrder.addClickListener(clickEvent -> {

            try {
                description.validate();
                clientID.validate();
                dataOfCreation.validate();
                dataOfCompletion.validate();
                price.validate();
                status.validate();

                    Database.startDatabase();
                    Order order = new Order(description.getValue(), Long.parseLong(clientID.getValue().toString()), new java.sql.Date(dataOfCreation.getValue().getTime()),
                                            new java.sql.Date(dataOfCompletion.getValue().getTime()), Double.parseDouble(price.getValue().replace(',', '.')),
                                            status.getValue().toString() );
                    OrderDAO.addOrder(order);
                    List<Order> orders = OrderDAO.getAllOrder();
                    int size = orders.size();
                    grid.addRow(orders.get(size-1).getId(), orders.get(size-1).getDescription(), orders.get(size-1).getClientID(), orders.get(size-1).getDataOfCreation().toString(),
                                orders.get(size-1).getDataOfCompletion().toString(), orders.get(size-1).getPrice(), orders.get(size-1).getStatusDescription());
                    Database.closeDatabase();
                    window.close();

            } catch (Validator.InvalidValueException e) {
                description.setRequiredError("Введите описание заказа");
                clientID.setRequiredError("Укажите клиента, для которого выполняется заказ");
                price.setRequiredError("Введите стоимость заказа");
                status.setRequiredError("Укажите статус заказа");
                Notification.show("Заполните данные", Notification.TYPE_WARNING_MESSAGE);
            }
        });



        cancel.addClickListener(clickEvent -> window.close());

        addOrder.addComponent(description);
        addOrder.addComponent(clientID);
        addOrder.addComponent(dataOfCreation);
        addOrder.addComponent(dataOfCompletion);
        addOrder.addComponent(price);
        addOrder.addComponent(status);
        addOrder.addComponent(buttons);

        window.setContent(addOrder);
        return window;
    }

    public Window updateOrder(Grid grid) {
        final Window window = new Window("Изменение заказа");
        window.setModal(true);
        window.center();
        window.setWidth("450");
        window.setHeight("400");
        window.setClosable(false);
        window.setResizable(false);

        final FormLayout addOrder = new FormLayout();
        addOrder.setMargin(true);
        addOrder.setSizeFull();

        final TextField description = new TextField("Описание", grid.getContainerDataSource().getItem(grid.getSelectedRow()).getItemProperty("Описание").getValue().toString());
        description.setSizeFull();
        description.setRequired(true);
        description.setRequiredError("Опишите выполняемую работу");
        description.setMaxLength(500);


        SimpleDateFormat dateFotmat = new SimpleDateFormat("yyyy-MM-dd");

        final DateField dataOfCreation = new DateField("Дата создания");
        Date date =  new Date();
        try {
            dataOfCreation.setValue(dateFotmat.parse(grid.getContainerDataSource().getItem(grid.getSelectedRow()).getItemProperty("Дата создания").getValue().toString()));
        } catch(Exception e) {
            System.out.println("Order Table: Ошибка в считывании Даты создания");
        }
        dataOfCreation.setSizeFull();
        dataOfCreation.setRequired(true);


        final DateField dataOfCompletion = new DateField("Дата окончания");
        try {
            dataOfCompletion.setValue(dateFotmat.parse(grid.getContainerDataSource().getItem(grid.getSelectedRow()).getItemProperty("Дата окончания работ").getValue().toString()));
        } catch(Exception e) {
            System.out.println("Order Table: Ошибка в считывании Даты окончания работ");
        }
        dataOfCompletion.setSizeFull();
        dataOfCompletion.setRequired(true);
        dataOfCreation.setRequiredError("Необходимо указать дату завершения работ");
        dataOfCompletion.addValidator(new DateRangeValidator("Заказ может быть завершен не раньше дня создания заказа и не позднее трех месяцев с текущего дня",
                                      new Date(dataOfCreation.getValue().getTime()), new Date(date.getYear(), date.getMonth()+3, date.getDate()), Resolution.YEAR));
        //Обновляем валидатор при изменении даты создания заказа
        dataOfCreation.addValueChangeListener(event -> {
            //Если изменяем дату, то ее нельзя ставить позде 1 месяца от текущего дня
            dataOfCreation.setRequiredError("Необходимо указать дату создания заказа");
            dataOfCreation.addValidator(new DateRangeValidator("Заказ должен быть принят не более одного месяца назад и не позднее сегодняшнего дня",
                    new Date(date.getYear(), date.getMonth()-1, date.getDate()),
                    new java.sql.Date(date.getTime()), Resolution.YEAR));
            //Если дата создания введена некорректно
            try {
                dataOfCompletion.removeAllValidators();
                Date updateDateOfCreation = dataOfCreation.getValue();
                dataOfCompletion.addValidator(new DateRangeValidator("Заказ может быть завершен не раньше дня создания заказа и не позднее трех месяцев с текущего дня!",
                        new Date(updateDateOfCreation.getYear(), updateDateOfCreation.getMonth(), updateDateOfCreation.getDate()),
                        new Date(date.getYear(), date.getMonth()+3, date.getDate()), Resolution.YEAR));
            } catch (NullPointerException e) {
            }
        } );

        final TextField price = new TextField("Стоимость",  grid.getContainerDataSource().getItem(grid.getSelectedRow()).getItemProperty("Стоимость").getValue().toString());
        price.setSizeFull();
        price.setRequired(true);
        price.setRequiredError("укажите стоимость");
        //Стоимость возможно ввести как с разделителем ',' между целой и дробной частью, так и с '.'
        price.addValidator(new RegexpValidator("[0-9]{1,6}(\\.?\\,?[0-9]{1,2}){0,1}", true, "Стоимость не может быть отрицательной или быть равной 1 000 000 и более"));

        //Панель выбора клиента из существующих
        List<Long> allClientID = new ArrayList();
        Database.startDatabase();
        List<Client> clients = ClientDAO.getAllClient();
        int index = clients.size();
        for (int i=0; i<index; i++) {
            allClientID.add(clients.get(i).getId());
        }
        ComboBox clientID = new ComboBox("ID Клиента", allClientID);
        clientID.setSizeFull();
        clientID.setRequired(false);
        clientID.setValue(Long.parseLong(grid.getContainerDataSource().getItem(grid.getSelectedRow()).getItemProperty("ID Клиента").getValue().toString()));
        clientID.setNullSelectionAllowed(false);

        //Панель выбора статуса из трех возможных
        List<String> allStatus = new ArrayList<>();
        allStatus.add("Запланирован");
        allStatus.add("Выполнен");
        allStatus.add("Принят клиентом");
        ComboBox status = new ComboBox("Статус", allStatus);
        status.setSizeFull();
        status.setValue(grid.getContainerDataSource().getItem(grid.getSelectedRow()).getItemProperty("Статус").getValue().toString());
        status.setNullSelectionAllowed(false);
        status.setRequired(false);


        final Button updateOrder = new Button("Изменить");
        updateOrder.setStyleName(ValoTheme.BUTTON_FRIENDLY);
        final Button cancel = new Button("Отмена");

        HorizontalLayout buttons = new HorizontalLayout();
        buttons.addComponent(updateOrder);
        buttons.addComponent(cancel);
        buttons.setSpacing(true);

        //Действия при нажатии на кнопки
        updateOrder.addClickListener(clickEvent -> {
            try {
                description.validate();
                clientID.validate();
                dataOfCreation.validate();
                dataOfCompletion.validate();
                price.validate();
                status.validate();

                Database.startDatabase();
                Long id = (Long) grid.getContainerDataSource().getItem(grid.getSelectedRow()).getItemProperty("ID").getValue();
                Order order = new Order(description.getValue(), Long.parseLong(clientID.getValue().toString()), new java.sql.Date(dataOfCreation.getValue().getTime()),
                        new java.sql.Date(dataOfCompletion.getValue().getTime()), Double.parseDouble(price.getValue().replace(',', '.')), status.getValue().toString());
                order.setId(id);
                OrderDAO.updateOrder(order);
                Database.closeDatabase();

                grid.getContainerDataSource().getItem(grid.getSelectedRow()).getItemProperty("Описание").setValue(description.getValue());
                grid.getContainerDataSource().getItem(grid.getSelectedRow()).getItemProperty("ID Клиента").setValue(clientID.getValue());
                grid.getContainerDataSource().getItem(grid.getSelectedRow()).getItemProperty("Дата создания").setValue(new java.sql.Date(dataOfCreation.getValue().getTime()).toString());
                grid.getContainerDataSource().getItem(grid.getSelectedRow()).getItemProperty("Дата окончания работ").setValue(new java.sql.Date(dataOfCompletion.getValue().getTime()).toString());
                grid.getContainerDataSource().getItem(grid.getSelectedRow()).getItemProperty("Стоимость").setValue(Double.parseDouble(price.getValue().replace(',', '.')));
                grid.getContainerDataSource().getItem(grid.getSelectedRow()).getItemProperty("Статус").setValue(status.getValue());

                window.close();
            } catch (Validator.InvalidValueException e) {
                Notification.show("Заполните данные", Notification.TYPE_WARNING_MESSAGE);
            }
         });

        cancel.addClickListener(clickEvent -> window.close());

        addOrder.addComponent(description);
        addOrder.addComponent(clientID);
        addOrder.addComponent(dataOfCreation);
        addOrder.addComponent(dataOfCompletion);
        addOrder.addComponent(price);
        addOrder.addComponent(status);
        addOrder.addComponent(buttons);

        window.setContent(addOrder);
        return window;
    }

    public  void deleteOrder(Grid grid){
        //Проверка, что выбрана строка для удаления
        if (grid.getSelectedRow() == null) return;

        Long id = (Long) grid.getContainerDataSource().getItem(grid.getSelectedRow()).getItemProperty("ID").getValue();
        Database.startDatabase();
        OrderDAO.deleteOrder(id);
        Database.closeDatabase();

        grid.getContainerDataSource().removeItem(grid.getSelectedRow());
        Notification.show("Удален заказ с ID = "+id.toString());
    }

    public FormLayout filter(Grid grid){

        FormLayout filterLayout = new FormLayout();
        filterLayout.addStyleName(ValoTheme.TABLE_BORDERLESS);

        Label textFilter = new Label("Фильтр");
        textFilter.setStyleName(ValoTheme.LABEL_H3);
        filterLayout.addComponent(textFilter);
        filterLayout.setComponentAlignment(textFilter, Alignment.TOP_LEFT);
        filterLayout.setWidth("295");

        TextField clientID = new TextField("ID Клиента", "");
        clientID.setWidth("200");
        TextField status = new TextField("Статус", "");
        status.setWidth("200");
        TextField description = new TextField("Описание", "");
        description.setWidth("200");
        description.setMaxLength(500);
        Button apply = new Button("Применить");

        //Обработка полей фильтра при нажатии кнопки Применить
        apply.addClickListener(event-> {

            Filterable filterable = (Filterable) grid.getContainerDataSource();
            filterable.removeAllContainerFilters();
            Filter filterClientID = new SimpleStringFilter("ID Клиента", clientID.getValue().toString(),
                    true, false);
            Filter filterStatus = new SimpleStringFilter("Статус", status.getValue().toString(),
                    true, false);
            Filter filterDescription = new SimpleStringFilter("Описание", description.getValue().toString(),
                    true, false);


            filterable.addContainerFilter(filterClientID);
            filterable.addContainerFilter(filterStatus);
            filterable.addContainerFilter(filterDescription);
        });

        filterLayout.addComponent(clientID);
        filterLayout.addComponent(status);
        filterLayout.addComponent(description);
        filterLayout.addComponent(apply);
        return filterLayout;
    }
}
