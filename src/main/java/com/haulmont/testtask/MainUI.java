package com.haulmont.testtask;

import com.haulmont.testtask.DAO.ClientDAO;
import com.haulmont.testtask.DAO.Database;
import com.vaadin.annotations.Theme;
import com.vaadin.data.Item;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import com.haulmont.testtask.entity.Client;
import com.haulmont.testtask.gui.*;

import java.util.List;

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



        List<Client> clients = ClientDAO.getAllClient();

        //Grid<clients> grid1 = new Grid<>();
        //grid1.setColumnOrder("ID","Фамилия","Имя","Отчество","Мобильный");
        //layout.addComponent(grid1);





        Database.closeDatabase();
    }
}