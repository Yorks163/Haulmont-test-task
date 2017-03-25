package com.haulmont.testtask;

import com.vaadin.annotations.Theme;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.Reindeer;
import com.vaadin.ui.themes.ValoTheme;

import com.haulmont.testtask.gui.*;

@Theme(ValoTheme.THEME_NAME)
public class MainUI extends UI {

    @Override
    protected void init(VaadinRequest request) {

        Label carWorkshop = new Label("Автомастерская");
        carWorkshop.setStyleName(ValoTheme.LABEL_H1);
        carWorkshop.setHeight("1");
        carWorkshop.setWidth(10, Unit.PERCENTAGE);

        Label author = new Label("Антон Шакиров специально для haulmont.ru");
        author.setHeight("1");
        author.setWidth(30, Unit.PERCENTAGE);

        HorizontalLayout text = new HorizontalLayout();
        text.addComponent(carWorkshop);
        text.setComponentAlignment(carWorkshop, Alignment.MIDDLE_CENTER);
        text.addComponent(author);
        text.setComponentAlignment(author, Alignment.MIDDLE_RIGHT);
        text.setWidth(98, Unit.PERCENTAGE);


        TabSheet tabSheet = new TabSheet();
        tabSheet.setWidth(98.0f, Unit.PERCENTAGE);
        tabSheet.setHeight(98.0f, Unit.PERCENTAGE);
        tabSheet.addTab(new TabClient().tabClient(), "   Клиенты   ");
        tabSheet.addTab(new TabOrder().tabOrder(), "   Заказы   ");

        VerticalLayout mainLayout = new VerticalLayout();
        mainLayout.addComponent(text);
        mainLayout.setComponentAlignment(text, Alignment.TOP_CENTER);
        mainLayout.addComponent(tabSheet);
        mainLayout.setComponentAlignment(tabSheet, Alignment.TOP_CENTER);
        setContent(mainLayout);

    }
}