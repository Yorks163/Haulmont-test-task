package com.haulmont.testtask.gui;

import com.haulmont.testtask.DAO.ClientDAO;
import com.haulmont.testtask.DAO.Database;

import com.haulmont.testtask.entity.Client;
import com.vaadin.data.Validator;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.*;
import com.vaadin.ui.declarative.FieldBinder;
import com.vaadin.ui.themes.ValoTheme;


import javax.xml.bind.Binder;
import java.util.List;

/**
 * Класс отвечает за изменение содержания бызы с клиентами
 *
 * @author Shakirov Anton
 */
public class EditClientTable {

    /**
     * Метод создает окно для добавления нового клиента
     */
    public Window addClient(Grid grid) {

        //Создание выводимого окна
        final Window window = new Window("Добавление нового клиента");
        window.setModal(true);
        window.center();
        window.setWidth("400");
        window.setHeight("300");
        window.setClosable(false);
        window.setResizable(false);

        //Слой добавления клиента
        final FormLayout addClient = new FormLayout();
        addClient.setMargin(true);
        addClient.setSizeFull();

        //Текстовое поле ввода фамилии
        final TextField surname = new TextField("Фамилия", "");
        surname.setSizeFull();
        surname.setRequired(true);
        surname.setMaxLength(50);
        surname.setNullSettingAllowed(true);

        //Текстовое поле ввода имени
        final TextField firstName = new TextField("Имя", "");
        firstName.setSizeFull();
        firstName.setRequired(true);
        firstName.setMaxLength(50);

        //Текстовое поле ввода фамилии
        final TextField patronymic = new TextField("Отчество", "");
        patronymic.setSizeFull();
        patronymic.setMaxLength(50);

        //Текстовое поле ввода номера телефона
        final TextField number = new TextField("Номер телефона", "");
        number.setSizeFull();
        number.setRequired(true);
        number.setMaxLength(50);

        //Создание кнопки Добававить клиента
        final Button saveClient = new Button("Добавить");
        saveClient.setStyleName(ValoTheme.BUTTON_FRIENDLY);

        //Действие при нажатии на кнопку Добавить
        saveClient.addClickListener(clickEvent -> {

            //Если поля не пустые, добавляем нового клиента
            try {
                surname.validate();
                firstName.validate();
                number.validate();

                //Добавляем запись в БД
                Database.startDatabase();
                Client client = new Client(surname.getValue(), firstName.getValue(), patronymic.getValue(), number.getValue() );
                ClientDAO.addClient(client);
                List<Client> clients = ClientDAO.getAllClient();

                //Добавляем запись в выводимую клиенту таблицу
                int index = clients.size();
                grid.addRow(clients.get(index-1).getId(), clients.get(index-1).getSurname(), clients.get(index-1).getFirstName(),
                            clients.get(index-1).getPatronymic(), clients.get(index-1).getNumber());

                Database.closeDatabase();
                window.close();
            } catch (Validator.InvalidValueException e) {
                //Добавляем ошибку о пустых полях
                surname.setRequiredError("Введите фамилию");
                firstName.setRequiredError("Введите имя");
                number.setRequiredError("Укажите телефон");
                Notification.show("Заполните данные", Notification.TYPE_WARNING_MESSAGE);
            }
        });

        //Создание кнопки Отмена и добавление ей действия
        final Button cancel = new Button("Отмена");
        cancel.addClickListener(clickEvent -> window.close());

        //Создаем слой для размещения кнопок
        HorizontalLayout buttons = new HorizontalLayout();
        buttons.setSpacing(true);
        buttons.addComponent(saveClient);
        buttons.addComponent(cancel);

        //Добавляем в главный слой все поля и кнопки
        addClient.addComponent(surname);
        addClient.addComponent(firstName);
        addClient.addComponent(patronymic);
        addClient.addComponent(number);
        addClient.addComponent(buttons);

        //Выводим созданное окно
        window.setContent(addClient);
        return window;
    }

    /**
     * Метод создает окно для изменения выбранного клиента
     */
    public Window updateClient(Grid grid) {

        //Создаем главное окно редактирования клиента
        final Window window = new Window("Изменить данные клиента");
        window.setModal(true);
        window.center();
        window.setWidth("400");
        window.setHeight("300");
        window.setClosable(false);
        window.setResizable(false);

        //Создаем главный слой
        final FormLayout addClient = new FormLayout();
        addClient.setMargin(true);
        addClient.setSizeFull();

        //Поле изменения фамилии
        final TextField surname = new TextField("Фамилия", grid.getContainerDataSource().getItem(grid.getSelectedRow()).getItemProperty("Фамилия").getValue().toString());
        surname.setSizeFull();
        surname.setMaxLength(50);
        surname.setRequired(true);
        surname.setRequiredError("Введите фамилию");
        surname.setNullSettingAllowed(true);

        //Поле изменения имени
        final TextField firstName = new TextField("Имя", grid.getContainerDataSource().getItem(grid.getSelectedRow()).getItemProperty("Имя").getValue().toString());
        firstName.setSizeFull();
        firstName.setRequired(true);
        firstName.setRequiredError("Введите имя");
        firstName.setMaxLength(50);

        //Поле изменения отчества. Отчество указывать не обязательно
        final TextField patronymic = new TextField("Отчество", grid.getContainerDataSource().getItem(grid.getSelectedRow()).getItemProperty("Отчество").getValue().toString());
        patronymic.setSizeFull();
        patronymic.setMaxLength(50);

        //Поле изменения номера
        final TextField number = new TextField("Номер телефона", grid.getContainerDataSource().getItem(grid.getSelectedRow()).getItemProperty("Номер телефона").getValue().toString());
        number.setSizeFull();
        number.setRequired(true);
        number.setRequiredError("Укажите телефон");
        number.setMaxLength(50);

        //Создаем кнопку Применить и назначаем дейчтвия при нажатии
        final Button saveClient = new Button("Применить");
        saveClient.setStyleName(ValoTheme.BUTTON_FRIENDLY);
        saveClient.addClickListener(clickEvent -> {

            //Если поля не пустые, обновляем данные клиента
            try {
                surname.validate();
                firstName.validate();
                number.validate();

                Database.startDatabase();
                Long id = (Long) grid.getContainerDataSource().getItem(grid.getSelectedRow()).getItemProperty("ID").getValue();
                Client client = new Client(surname.getValue(), firstName.getValue(), patronymic.getValue(), number.getValue());
                client.setId(id);
                ClientDAO.updateClient(client);
                Database.closeDatabase();

                //Перезаписываем данного клиента в таблице
                grid.getContainerDataSource().getItem(grid.getSelectedRow()).getItemProperty("Фамилия").setValue(surname.getValue());
                grid.getContainerDataSource().getItem(grid.getSelectedRow()).getItemProperty("Имя").setValue(firstName.getValue());
                grid.getContainerDataSource().getItem(grid.getSelectedRow()).getItemProperty("Отчество").setValue(patronymic.getValue());
                grid.getContainerDataSource().getItem(grid.getSelectedRow()).getItemProperty("Номер телефона").setValue(number.getValue());
                window.close();
            } catch (Validator.InvalidValueException e) {
                //Предупреждение, сли хотя бы одно из полей пустое
                Notification.show("Заполните данные", Notification.TYPE_WARNING_MESSAGE);
            }
        });

        //Создаем кнопку отмены
        final Button cancel = new Button("Отмена");
        cancel.addClickListener(clickEvent -> window.close());

        //Слой с кнопками
        HorizontalLayout buttons = new HorizontalLayout();
        buttons.addComponent(saveClient);
        buttons.addComponent(cancel);
        buttons.setSpacing(true);

        //Добавляем поля и кнопки на главный слой
        addClient.addComponent(surname);
        addClient.addComponent(firstName);
        addClient.addComponent(patronymic);
        addClient.addComponent(number);
        addClient.addComponent(buttons);

        //Возвращаем созданное окно
        window.setContent(addClient);
        return window;
    }

    /**
     * Метод удаляет выбранного клиента
     */
    public void deleteClient(Grid grid){
        //Проверка, что выбрана строка для удаления
        if (grid.getSelectedRow() == null) return;

        Database.startDatabase();

        Long id = (Long) grid.getContainerDataSource().getItem(grid.getSelectedRow()).getItemProperty("ID").getValue();
        Database.startDatabase();
        int result = ClientDAO.deleteClient(id);
        Database.closeDatabase();

        //Проверка, что возможно удаление клиента из БД. Удаляем клиента в случае успеха
        if (result == 0) {
            grid.getContainerDataSource().removeItem(grid.getSelectedRow());
            Notification.show("Удален клиент с ID = " + id.toString());
        } else if (result == 1) {
            //Если для клиента существует заказ, то выводим сообщение об ошибке
            Notification.show("Для клиента с ID = " + id.toString() + " существует заказ!", Notification.TYPE_ERROR_MESSAGE);
        } else Notification.show("Ошибка базы данных!",Notification.TYPE_ERROR_MESSAGE);
    }

}
