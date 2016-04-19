package com.mycompany.test.vaadin.Components;


import com.mycompany.test.vaadin.UI.TecoMainUi;
import com.mycompany.test.vaadin.Views.HomeView;
import com.vaadin.ui.Button;
import com.vaadin.ui.VerticalLayout;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author alex
 */
public class VerticalLoginComponent extends VerticalLayout {
    
    Button submitButton = new Button("Submit");

    public VerticalLoginComponent(final HorizontalLoginComponent hlv1, final HorizontalLoginComponent hlv2) {
        addComponent(hlv1);
        addComponent(hlv2);
        
        submitButton.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                ((TecoMainUi)getUI()).switchView(HomeView.NAME);
            }
        });
        
        addComponent(submitButton);
        setStyleName("centeredLayout");
        setMargin(true);
        setSpacing(true);
    }
    
    
    
}
