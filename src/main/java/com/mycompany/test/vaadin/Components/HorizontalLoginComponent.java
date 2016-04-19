package com.mycompany.test.vaadin.Components;


import com.vaadin.ui.AbstractTextField;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author alex
 */
public class HorizontalLoginComponent extends HorizontalLayout {

    Label label;
    AbstractTextField textField;
    
    public HorizontalLoginComponent(Label label, AbstractTextField textField) {
        this.textField = textField;
        this.label = label;
        label.setStyleName("userLabel");
        textField.setRequired(true);
        textField.setRequiredError(label.getValue() + " is required!");
        addComponent(label);
        addComponent(textField);
        setSpacing(true);
        setComponentAlignment(label, Alignment.MIDDLE_CENTER);
    }
    
}
