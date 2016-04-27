/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.test.vaadin.Components;

import com.mycompany.test.vaadin.Entities.Models;
import com.mycompany.test.vaadin.Entities.Os;
import com.vaadin.data.validator.BeanValidator;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.TextField;
import java.util.List;
import org.vaadin.maddon.button.MButton;
import org.vaadin.maddon.fields.MTextField;

/**
 *
 * @author alex
 */
public class BasicModelDetailsForm extends FormLayout {
    
    private TextField modelId = new MTextField("Model Id");
    private TextField brandName = new MTextField("Brand name");
    private TextField modelName = new MTextField("Model name");
    private NativeSelect os = new NativeSelect("OS");
    
    protected MButton saveButton = new MButton(FontAwesome.SAVE);
    
    public BasicModelDetailsForm() {
        brandName.setNullRepresentation("");
        brandName.addValidator(new BeanValidator(Models.class, "brandName"));
        
        modelName.setNullRepresentation("");
        modelName.addValidator(new BeanValidator(Models.class, "modelName"));
        
        os.setNullSelectionAllowed(false);
        disableValidation();
        addComponent(modelId);
        addComponent(brandName);
        addComponent(modelName);
        addComponent(os);
        addComponent(saveButton);
    }
    
    public void enableValidation() {
        brandName.setValidationVisible(true);
        modelName.setValidationVisible(true);
    }
    
    public void disableValidation() {
        brandName.setValidationVisible(false);
        modelName.setValidationVisible(false);
    }
    
    public void buildOses(List<Os> oses) {
        os.clear();
        os.addItems(oses);
    }
    
    public void disableModelId() {
        modelId.setEnabled(false);
    }
    
    public void addSaveListener(Button.ClickListener saveListener) {
        saveButton.addClickListener(saveListener);
    }
}
