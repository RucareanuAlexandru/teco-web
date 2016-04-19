/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.test.vaadin.Components;

import com.mycompany.test.vaadin.Entities.PhoneTypes;
import com.vaadin.data.validator.BeanValidator;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.TextField;
import org.vaadin.maddon.fields.MTextField;

/**
 *
 * @author alex
 */
public class PhoneTypeForm extends FormLayout {
    
    private final TextField categoryName = new MTextField("category");

    public PhoneTypeForm() {
        categoryName.setNullRepresentation("");
        categoryName.addValidator(new BeanValidator(PhoneTypes.class, "categoryName"));
        disableValidation();
        
        addComponent(categoryName);
    }
    
    public void enableValidation() {
        categoryName.setValidationVisible(true);
    }
    
    public void disableValidation() {
        categoryName.setValidationVisible(false);
    }
    
    
}
