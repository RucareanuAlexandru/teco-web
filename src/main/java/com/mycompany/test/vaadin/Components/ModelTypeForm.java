/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.test.vaadin.Components;

import com.mycompany.test.vaadin.Entities.PhoneTypes;
import com.vaadin.data.fieldgroup.PropertyId;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.VerticalLayout;
import java.util.List;

/**
 *
 * @author alex
 */
public class ModelTypeForm extends AbstractVerticalForm {

    @PropertyId("property.category")
    private NativeSelect category = new NativeSelect("Category");

    @PropertyId("property.propertyName")
    private NativeSelect propertyName = new NativeSelect("Property name");

    public ModelTypeForm() {
        propertyName.setNullSelectionAllowed(false);
        
        
        addComponent(clearSelectionButton);
        addComponent(propertyName);
        addComponent(category);
        addComponent(saveRemoceHl);
    }

    public void buildProperties(List<String> properties) {
        propertyName.clear();
        propertyName.addItems(properties);
    }
    
    public void buildCategories(List<PhoneTypes> types) {
        category.clear();
        category.addItems(types);
    }
    
    public void disableCategory() {
        category.setEnabled(false);
    }
    
    public void enableCategory() {
        category.setEnabled(true);
    }
}
