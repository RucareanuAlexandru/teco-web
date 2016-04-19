/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.test.vaadin.Components;

import com.mycompany.test.vaadin.UI.TecoMainUi;
import com.mycompany.test.vaadin.Views.ActionsView;
import com.mycompany.test.vaadin.Views.HomeView;
import com.mycompany.test.vaadin.Views.OsView;
import com.mycompany.test.vaadin.Views.PhoneTypesView;
import com.mycompany.test.vaadin.Views.ProjectsView;
import com.mycompany.test.vaadin.Views.ReportsView;
import com.vaadin.ui.Accordion;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;


/**
 *
 * @author alex
 */
public class SideMenuComponent extends VerticalLayout {
    
    public SideMenuComponent() {
        Button homeButton = new Button("Home", new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                ((TecoMainUi)getUI()).switchView(HomeView.NAME);
            }
        });
        
        Button reportsButton = new Button("Reports", new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                ((TecoMainUi)getUI()).switchView(ReportsView.NAME);
            }
        });
        
        Button projButton = new Button("Projects", new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                ((TecoMainUi)getUI()).switchView(ProjectsView.NAME);
            }
        });
        
        Button actionsButton = new Button("Actions", new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                ((TecoMainUi)getUI()).switchView(ActionsView.NAME);
            }
        });
        
        Button typesButton = new Button("Types", new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                ((TecoMainUi)getUI()).switchView(PhoneTypesView.NAME);
            }
        });
        
        Button osButton = new Button("Os", new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                ((TecoMainUi)getUI()).switchView(OsView.NAME);
            }
        });

        VerticalLayout admin = new VerticalLayout(projButton, actionsButton, typesButton, osButton);
        VerticalLayout user = new VerticalLayout(homeButton, reportsButton);

        for (Component component : user) {
            component.addStyleName("width-100");
        }
        
        for (Component component : admin) {
            component.addStyleName("width-100");
        }
        
        Accordion a = new Accordion();
        a.addTab(user, "User").setStyleName("centeredText");
        a.addTab(admin, "Admin");
        
        addComponents(a);
   }
}
