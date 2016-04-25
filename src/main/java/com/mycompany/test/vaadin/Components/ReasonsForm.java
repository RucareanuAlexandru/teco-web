/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.test.vaadin.Components;

import com.mycompany.test.vaadin.Entities.BehaviourReasons;
import com.vaadin.data.validator.BeanValidator;
import com.vaadin.ui.TextField;

/**
 *
 * @author alex
 */
public class ReasonsForm extends AbstractVerticalForm {
    
    private TextField reasonDescription = new TextField("Description");

    public ReasonsForm() {
        reasonDescription.setNullRepresentation("");
        reasonDescription.addValidator(new BeanValidator(BehaviourReasons.class, "reasonDescription"));
        
        disableValidation();
        
        addComponent(clearSelectionButton);
        addComponent(reasonDescription);
        addComponent(saveRemoceHl);  
    }
    
    public void enablevalidation() {
        reasonDescription.setValidationVisible(true);
    }
    
    public void disableValidation() {
        reasonDescription.setValidationVisible(false);
    }
    
    
}
