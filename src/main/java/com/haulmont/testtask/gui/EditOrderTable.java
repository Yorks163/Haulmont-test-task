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

/**
 * Класс отвечает за изменение содержания бызы с заказами
 *
 * @author Shakirov Anton
 */
public class EditOrderTable {

    /**
     * Метод создает окно для добавления нового заказа
     */
    public Window addOrder(Grid grid, TextArea fullDescription) {

        //Создаем главное окно
        final Window window = new Window("Добавление нового заказа");
        window.setModal(true);
        window.center();
        window.setWidth("450");
        window.setHeight("400");
        window.setClosable(false);
        window.setResizable(false);

        //Создаем главный слой
        final FormLayout addOrder = new FormLayout();
        addOrder.setMargin(true);
        addOrder.setSizeFull();

        //Поле описания заказа
        final TextField description = new TextField("Описание", "");
        description.setSizeFull();
        description.setRequired(true);
        description.setMaxLength(500);

        //Поле даты создания заказа. Автоматически выставляется текущая дата. Создается валидатор для ограничения возможных значений даты создания
        final DateField dataOfCreation = new DateField("Дата создания");
        Date date =  new Date();
        dataOfCreation.setValue(date);
        dataOfCreation.setSizeFull();
        dataOfCreation.setRequired(true);
        dataOfCreation.addValidator(new DateRangeValidator("Заказ должен быть принят не более одного месяца назад и не позднее сегодняшнего дня",
                                    new Date(date.getYear(), date.getMonth()-1, date.getDate()),
                                    new java.sql.Date(date.getTime()), Resolution.YEAR));


        //Поле даты завершения заказа. Автоматически выставляется завтрашний день относительно текущей даты. Создается валидатор для огграничения возможных значений даты завершения
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

        //Создаем поле стоимости.
        final TextField price = new TextField("Стоимость");
        price.setRequired(true);
        price.setSizeFull();
        //Стоимость возможно ввести как с разделителем ',' между целой и дробной частью, так и с '.'. Сумма не может превышать 999 999,99 рублей
        price.addValidator(new RegexpValidator("[0-9]{1,6}(\\.?\\,?[0-9]{1,2}){0,1}", true, "Стоимость не может быть отрицательной или быть равной 1 000 000 и более"));

        //Ищем всех существующих клиентов
        List<Long> allClientID = new ArrayList();
        Database.startDatabase();
        List<Client> clients = ClientDAO.getAllClient();
        int index = clients.size();
        for (int i=0; i<index; i++) {
            allClientID.add(clients.get(i).getId());
        }

        //Создаем список с выбором клиента из существующих
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

        //Создаем кнопки
        final Button saveOrder = new Button("Добавить");
        saveOrder.setStyleName(ValoTheme.BUTTON_FRIENDLY);
        final Button cancel = new Button("Отмена");

        //Создаем слой с кнопками
        HorizontalLayout buttons = new HorizontalLayout();
        buttons.setSpacing(true);

        buttons.addComponent(saveOrder);
        buttons.addComponent(cancel);


        //Действия при нажатии на кнопки
        saveOrder.addClickListener(clickEvent -> {

            try {
                description.validate();
                clientID.validate();
                dataOfCreation.validate();
                dataOfCompletion.validate();
                price.validate();
                status.validate();

                //Если поля не пустые и данные введены корректно, добавляем заказ
                Database.startDatabase();
                Order order = new Order(description.getValue().trim(), Long.parseLong(clientID.getValue().toString()), new java.sql.Date(dataOfCreation.getValue().getTime()),
                                        new java.sql.Date(dataOfCompletion.getValue().getTime()), Double.parseDouble(price.getValue().replace(',', '.')),
                                        status.getValue().toString() );
                OrderDAO.addOrder(order);
                List<Order> orders = OrderDAO.getAllOrder();

                int size = orders.size();
                grid.addRow(orders.get(size-1).getId(), orders.get(size-1).getDescription(), orders.get(size-1).getClientID(), orders.get(size-1).getDataOfCreation().toString(),
                            orders.get(size-1).getDataOfCompletion().toString(), orders.get(size-1).getPrice(), orders.get(size-1).getStatusDescription());
                Database.closeDatabase();

                //Смотрим на изменения для поля с полным текстом описания
                if (description.getValue().length() > grid.getColumn("Описание").getWidth() / 10) {
                    fullDescription.setValue("Полное описание заказа: " + grid.getContainerDataSource().getItem(grid.getSelectedRow()).getItemProperty("Описание").getValue().toString());
                    fullDescription.setVisible(true);
                } else fullDescription.setVisible(false);
                window.close();

            } catch (Validator.InvalidValueException e) {
                //Если хотя бы одно из полей пустое, выводим сообщения об ошибках
                description.setRequiredError("Введите описание заказа");
                clientID.setRequiredError("Укажите клиента, для которого выполняется заказ");
                price.setRequiredError("Введите стоимость заказа");
                status.setRequiredError("Укажите статус заказа");
                Notification.show("Заполните данные", Notification.TYPE_WARNING_MESSAGE);
            }
        });

        cancel.addClickListener(clickEvent -> window.close());

        //Добавляем поля и кнопки на главный слой
        addOrder.addComponent(description);
        addOrder.addComponent(clientID);
        addOrder.addComponent(dataOfCreation);
        addOrder.addComponent(dataOfCompletion);
        addOrder.addComponent(price);
        addOrder.addComponent(status);
        addOrder.addComponent(buttons);

        //Возвращаем окно добавления заказа
        window.setContent(addOrder);
        return window;
    }

    /**
     * Метод создает окно для изменения выбранного заказа
     */
    public Window updateOrder(Grid grid, TextArea fullDescription) {

        //Создаем главное окно
        final Window window = new Window("Изменение заказа");
        window.setModal(true);
        window.center();
        window.setWidth("450");
        window.setHeight("400");
        window.setClosable(false);
        window.setResizable(false);

        //Создаем главный слой
        final FormLayout addOrder = new FormLayout();
        addOrder.setMargin(true);
        addOrder.setSizeFull();

        //Создаем поле изменения описания
        final TextField description = new TextField("Описание", grid.getContainerDataSource().getItem(grid.getSelectedRow()).getItemProperty("Описание").getValue().toString());
        description.setSizeFull();
        description.setRequired(true);
        description.setRequiredError("Опишите выполняемую работу");
        description.setMaxLength(500);

        //Указываем формат для считывания даты
        SimpleDateFormat dateFotmat = new SimpleDateFormat("yyyy-MM-dd");

        //Создаем поле редактирования даты создания заказа
        final DateField dataOfCreation = new DateField("Дата создания");
        Date date =  new Date();
        try {
            dataOfCreation.setValue(dateFotmat.parse(grid.getContainerDataSource().getItem(grid.getSelectedRow()).getItemProperty("Дата создания").getValue().toString()));
        } catch(Exception e) {
            System.out.println("Order Table: Ошибка в считывании Даты создания");
        }
        dataOfCreation.setSizeFull();
        dataOfCreation.setRequired(true);

        //Создаем поле редактирования даты завершения заказа
        final DateField dataOfCompletion = new DateField("Дата окончания");
        try {
            dataOfCompletion.setValue(dateFotmat.parse(grid.getContainerDataSource().getItem(grid.getSelectedRow()).getItemProperty("Дата окончания работ").getValue().toString()));
        } catch(Exception e) {
            System.out.println("Order Table: Ошибка в считывании Даты окончания работ");
        }
        dataOfCompletion.setSizeFull();
        dataOfCompletion.setRequired(true);
        dataOfCompletion.setRequiredError("Необходимо указать дату завершения работ");


        dataOfCompletion.setRequiredError("Необходимо указать дату завершения работ");
        dataOfCompletion.addValidator(new DateRangeValidator("Заказ может быть завершен не раньше дня создания заказа и не позднее трех месяцев с текущего дня",
                                      new Date(dataOfCreation.getValue().getTime()), new Date(date.getYear(), date.getMonth()+3, date.getDate()), Resolution.YEAR));

        //Если изменяем уже выставленную до этого дату создания заказа, то для него создаться валидатор.
        dataOfCreation.addValueChangeListener(event -> {
            //Если изменяем дату, то ее нельзя ставить позде 1 месяца от текущего дня
            dataOfCreation.setRequiredError("Необходимо указать дату создания заказа");
            dataOfCreation.removeAllValidators();
            dataOfCreation.addValidator(new DateRangeValidator("Заказ должен быть принят не более одного месяца назад и не позднее сегодняшнего дня",
                                        new Date(date.getYear(), date.getMonth()-1, date.getDate()),
                                        new java.sql.Date(date.getTime()), Resolution.YEAR));

            try {
                //Обновляем валидатор даты завершения заказа при изменении даты создания.
                dataOfCompletion.removeAllValidators();
                //Получаем новое значение даты создания для невозможности выставить дату завершения раньше измененной даты создания заказа
                Date updateDateOfCreation = dataOfCreation.getValue();
                dataOfCompletion.addValidator(new DateRangeValidator("Заказ может быть завершен не раньше дня создания заказа и не позднее трех месяцев с текущего дня!",
                                              new Date(updateDateOfCreation.getYear(), updateDateOfCreation.getMonth(), updateDateOfCreation.getDate()),
                                              new Date(date.getYear(), date.getMonth()+3, date.getDate()), Resolution.YEAR));
            } catch (NullPointerException e) {
                Notification.show("Ошибка введенных дат");
            }
        } );


        final TextField price = new TextField("Стоимость",  grid.getContainerDataSource().getItem(grid.getSelectedRow()).getItemProperty("Стоимость").getValue().toString());
        price.setSizeFull();
        price.setRequired(true);
        price.setRequiredError("укажите стоимость");

        //Стоимость возможно ввести как с разделителем ',' между целой и дробной частью, так и с '.'. Стоимость не может быть более 999 999,99 рублей
        price.addValidator(new RegexpValidator("[0-9]{1,6}(\\.?\\,?[0-9]{1,2}){0,1}", true, "Стоимость не может быть отрицательной или быть равной 1 000 000 и более"));

        //Панель выбора клиента из существующих
        List<Long> allClientID = new ArrayList();
        Database.startDatabase();
        List<Client> clients = ClientDAO.getAllClient();
        int index = clients.size();
        for (int i=0; i<index; i++) {
            allClientID.add(clients.get(i).getId());
        }

        //Выпадающее меню с выбором клиента из существующих в базе
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

        //Создаем кнопки и добавляем их на отдельный слой
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

                //Если данные введены корректно, изменям запись
                Database.startDatabase();
                Long id = (Long) grid.getContainerDataSource().getItem(grid.getSelectedRow()).getItemProperty("ID").getValue();
                Order order = new Order(description.getValue().trim(), Long.parseLong(clientID.getValue().toString()), new java.sql.Date(dataOfCreation.getValue().getTime()),
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

                //Смотрим на изменения для поля с полным текстом описания
                if (description.getValue().length() > grid.getColumn("Описание").getWidth() / 10) {
                    fullDescription.setValue("Полное описание заказа: " + grid.getContainerDataSource().getItem(grid.getSelectedRow()).getItemProperty("Описание").getValue().toString());
                    fullDescription.setVisible(true);
                } else fullDescription.setVisible(false);

                window.close();
            } catch (Validator.InvalidValueException e) {
                //Если данные введены некорректно
                Notification.show("Заполните данные", Notification.TYPE_WARNING_MESSAGE);
            }
         });

        cancel.addClickListener(clickEvent -> {
            window.close();
        });

        //Добавляем формы и кнопки на главный слой
        addOrder.addComponent(description);
        addOrder.addComponent(clientID);
        addOrder.addComponent(dataOfCreation);
        addOrder.addComponent(dataOfCompletion);
        addOrder.addComponent(price);
        addOrder.addComponent(status);
        addOrder.addComponent(buttons);

        //Возвращаем окно редактирования заказов
        window.setContent(addOrder);
        return window;
    }

    /**
     * Метод удаляет выбранный заказ
     */
    public  void deleteOrder(Grid grid){

        //Проверка, что выбрана строка для удаления
        if (grid.getSelectedRow() == null) return;

        //Удаляем объект из БД и таблицы, выводим сообщение с информацией о ID удаленного заказа
        Long id = (Long) grid.getContainerDataSource().getItem(grid.getSelectedRow()).getItemProperty("ID").getValue();
        Database.startDatabase();
        OrderDAO.deleteOrder(id);
        Database.closeDatabase();

        grid.getContainerDataSource().removeItem(grid.getSelectedRow());
        Notification.show("Удален заказ с ID = "+id.toString());
    }

    /**
     * Метод создает форму для фильтрации заказов по параметрам
     */
    public FormLayout filter(Grid grid){

        //Надпись "Фильтр"
        Label textFilter = new Label("Фильтр");
        textFilter.setStyleName(ValoTheme.LABEL_H3);

        //Создаем поля фильтра
        TextField clientID = new TextField("ID Клиента", "");
        clientID.setWidth("200");
        TextField status = new TextField("Статус", "");
        status.setWidth("200");
        TextField description = new TextField("Описание", "");
        description.setWidth("200");
        description.setMaxLength(500);

        //Создаем кнопку применить и определяем дейчтвия при надатии на нее
        Button apply = new Button("Применить");

        //Обработка полей фильтра при нажатии кнопки Применить
        apply.addClickListener(event-> {

            //Создаем фильтр
            Filterable filterable = (Filterable) grid.getContainerDataSource();
            filterable.removeAllContainerFilters();

            //Добавляем в фильтр значения из полей ввода
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

        //Главный слой фильтра
        FormLayout filterLayout = new FormLayout();
        filterLayout.addStyleName(ValoTheme.TABLE_BORDERLESS);
        filterLayout.setWidth("295");

        //Добавляем на слой фильтра поля фильтрации и кнопку. Возвращаем готовый слой
        filterLayout.addComponent(textFilter);
        filterLayout.setComponentAlignment(textFilter, Alignment.TOP_LEFT);
        filterLayout.addComponent(clientID);
        filterLayout.addComponent(status);
        filterLayout.addComponent(description);
        filterLayout.addComponent(apply);
        return filterLayout;
    }
}
