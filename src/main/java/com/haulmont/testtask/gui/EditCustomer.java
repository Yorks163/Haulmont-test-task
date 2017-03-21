package com.haulmont.testtask.gui;


import com.vaadin.server.Sizeable;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Window;
import com.vaadin.annotations.Theme;
import com.vaadin.data.Item;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

public class EditCustomer {

    public Window editCustomer() {
        final Window window = new Window("Window");
        window.setModal(true);
        window.center();
        window.setWidth(300.0f, Sizeable.Unit.PIXELS);
        final FormLayout content = new FormLayout();
        content.setMargin(true);
        window.setContent(content);

        return window;
    }
}
