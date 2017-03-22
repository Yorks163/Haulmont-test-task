package com.haulmont.testtask;

import com.haulmont.testtask.DAO.Database;
import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import com.haulmont.testtask.gui.*;

@Theme(ValoTheme.THEME_NAME)
public class MainUI extends UI {

    @Override
    protected void init(VaadinRequest request) {


        Database.startDatabase();


        TabSheet tabSheet = new TabSheet();
        tabSheet.setHeight(100.0f, Unit.PERCENTAGE);
        tabSheet.addStyleName(ValoTheme.TABSHEET_FRAMED);
        tabSheet.addStyleName(ValoTheme.TABSHEET_PADDED_TABBAR);
        tabSheet.addTab(new TabClient().tabClient(), "Клиенты ");
        tabSheet.addTab(new TabOrder().tabOrder(), "Заказы ");
        setContent(tabSheet);








        Database.closeDatabase();
    }
}