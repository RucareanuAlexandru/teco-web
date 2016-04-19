/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.test.vaadin.Components;

import com.mycompany.test.vaadin.Entities.StkActions;
import com.vaadin.data.validator.BeanValidator;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.TextField;

/**
 *
 * @author alex
 */
public class StkActionPropertyForm extends FormLayout {
    
    private TextField name = new TextField("name");
    private TextField description = new TextField("description");

    public StkActionPropertyForm() {
        name.setNullRepresentation("");
        name.addValidator(new BeanValidator(StkActions.class, "name"));
        
        description.setNullRepresentation("");
        description.addValidator(new BeanValidator(StkActions.class, "description"));
    
        disableValidation();
        
        addComponent(name);
        addComponent(description);
    }
    
    public void disableValidation() {
        name.setValidationVisible(false);
        description.setValidationVisible(false);
    }
    
    public void enableValidation() {
        name.setValidationVisible(true);
        description.setValidationVisible(true);
    }
    
}
