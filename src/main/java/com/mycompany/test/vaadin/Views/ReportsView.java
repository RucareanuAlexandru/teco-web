/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.test.vaadin.Views;

import com.mycompany.test.vaadin.UI.TecoMainUi;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

/**
 *
 * @author alex
 */
public class ReportsView extends CustomComponent implements View{

    public static final String NAME = "reports";
    
    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        TecoMainUi main = (TecoMainUi)getUI();
        main.getAppLayout().getComponent(0).setVisible(true);
    }
    
    public ReportsView() {
        VerticalLayout p = new VerticalLayout();
        p.setSizeFull();
        Label l = new Label("Reports Page");
        p.addComponent(l);
        TextField field = new TextField();
        p.addComponent(field);
        setCompositionRoot(p);
    }
}
