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

        Label label = new Label("Автомастерская");
        label.setStyleName(ValoTheme.LABEL_H1);

        TabSheet tabSheet = new TabSheet();
        tabSheet.setHeight(98.0f, Unit.PERCENTAGE);
        tabSheet.addTab(new TabClient().tabClient(), "   Клиенты   ");
        tabSheet.addTab(new TabOrder().tabOrder(), "   Заказы   ");

        VerticalLayout mainLayout = new VerticalLayout();
        mainLayout.addComponent(label);
        mainLayout.addComponent(tabSheet);
        setContent(mainLayout);

    }
}