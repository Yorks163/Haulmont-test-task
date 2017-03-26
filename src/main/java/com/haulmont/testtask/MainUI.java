package com.haulmont.testtask;

import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import com.haulmont.testtask.gui.*;

/**
 * Главный класс приложения, запускающий графический интерфейс
 *
 * @author Shakirov Anton
 */
@Theme(ValoTheme.THEME_NAME)
public class MainUI extends UI {

    /**
     * Главный метод для запуска графического интерфейса пользователя
     * @param request
     */
    @Override
    protected void init(VaadinRequest request) {

        // Заголовок вверху страницы
        Label carWorkshop = new Label("Автомастерская");
        carWorkshop.setStyleName(ValoTheme.LABEL_H1);
        carWorkshop.setHeight("1");
        carWorkshop.setWidth(10, Unit.PERCENTAGE);

        // Добавляем на страницу текст с автором проекта
        Label author = new Label("Антон Шакиров специально для haulmont.ru");
        author.setHeight("1");
        author.setWidth(30, Unit.PERCENTAGE);

        // Горизонтальный слой, куда добавляются надписи
        HorizontalLayout text = new HorizontalLayout();
        text.setWidth(98, Unit.PERCENTAGE);
        text.addComponent(carWorkshop);
        text.addComponent(author);
        text.setComponentAlignment(carWorkshop, Alignment.MIDDLE_CENTER);
        text.setComponentAlignment(author, Alignment.MIDDLE_RIGHT);

        // Создаем TabSheet с 2 страницами для двух разных таблиц
        TabSheet tabSheet = new TabSheet();
        tabSheet.setWidth(98.0f, Unit.PERCENTAGE);
        tabSheet.setHeight(98.0f, Unit.PERCENTAGE);
        tabSheet.addTab(new TabClient().tabClient(), "   Клиенты   ");
        tabSheet.addTab(new TabOrder().tabOrder(), "   Заказы   ");

        // Главный слой приложения
        VerticalLayout mainLayout = new VerticalLayout();
        mainLayout.addComponent(text);
        mainLayout.setComponentAlignment(text, Alignment.TOP_CENTER);
        mainLayout.addComponent(tabSheet);
        mainLayout.setComponentAlignment(tabSheet, Alignment.TOP_CENTER);
        setContent(mainLayout);

    }
}