/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.test.vaadin.Views;

import com.mycompany.test.vaadin.Components.HorizontalLoginComponent;
import com.mycompany.test.vaadin.UI.TecoMainUi;
import com.mycompany.test.vaadin.Components.VerticalLoginComponent;
import com.vaadin.cdi.CDIView;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

/**
 *
 * @author alex
 */
@CDIView("login")
public class LoginView extends CustomComponent implements View {

    public static final String NAME = "login";
    
    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        TecoMainUi main = (TecoMainUi)getUI();
        main.getAppLayout().getComponent(0).setVisible(false);
    }

    public LoginView() {
        Label userNameLabel = new Label("User name");       
        Label userNameLabel2 = new Label("Password");

        TextField userName = new TextField();
        PasswordField userName2 = new PasswordField();
        
        HorizontalLoginComponent hlv = new HorizontalLoginComponent(userNameLabel, userName);
        HorizontalLoginComponent hlv1 = new HorizontalLoginComponent(userNameLabel2, userName2);
        
        VerticalLoginComponent loginView = new VerticalLoginComponent(hlv, hlv1);
        Panel panel = new Panel("Login");
        panel.setContent(loginView);
        panel.setSizeUndefined();
        
        VerticalLayout layout = new VerticalLayout();
        layout.addComponent(panel);
        layout.setComponentAlignment(panel, Alignment.TOP_CENTER);
        setCompositionRoot(layout);
    }
    
    
    
}
