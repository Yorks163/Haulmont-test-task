package com.haulmont.testtask;

import com.vaadin.annotations.Theme;
import com.vaadin.data.Item;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import com.haulmont.testtask.entity.Client;

import java.util.List;

@Theme(ValoTheme.THEME_NAME)
public class MainUI extends UI {

    @Override
    protected void init(VaadinRequest request) {

        HorizontalLayout layout = new HorizontalLayout();

        Table table = new Table("The Brightest Stars");

        table.addContainerProperty("Name", String.class, null);
        table.addContainerProperty("Mag", Float.class, null);

        Object newItemId = table.addItem();
        Item row1 = table.getItem(newItemId);
        row1.getItemProperty("Name").setValue("Sirius");
        row1.getItemProperty("Mag").setValue(-1.46f);

        table.addItem(new Object[]{"Canopus", -0.72f}, 2);
        table.addItem(new Object[]{"Arcturus", -0.04f}, 3);
        table.addItem(new Object[]{"Alpha Centauri", -0.01f}, 4);

        table.setPageLength(table.size());
        layout.addComponent(table);


        Client client = new Client("AAA","BBB","CCC","8(927)123-45-67");
        Client client1 = new Client("AAfdfA","BBfdB","CCC","8(927)123-45-67");
        Client client2 = new Client("AAA","BBfdB","CCfdC","8(927)33123-45-67");

        Grid grid = new Grid();
        grid.addColumn("Surname", String.class);
        grid.addColumn("Firstname", String.class);
        grid.addColumn("Patronymic", String.class);
        grid.addColumn("Number", String.class);

        grid.addRow(client.getFirstName(),client.getSurname(),client.getPatronymic(), client.getNumber());
        grid.addRow(client1.getFirstName(),client1.getSurname(),client1.getPatronymic(), client1.getNumber());
        grid.addRow(client2.getFirstName(),client2.getSurname(),client2.getPatronymic(), client2.getNumber());
        grid.setSizeFull();
        grid.setEditorEnabled(true);

        layout.addComponent(grid);


        setContent(layout);


    }
}