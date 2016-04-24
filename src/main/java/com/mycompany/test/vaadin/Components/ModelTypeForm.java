/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.test.vaadin.Components;

import com.mycompany.test.vaadin.Entities.PhoneTypes;
import com.vaadin.data.Property;
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
        category.setNullSelectionAllowed(false);
        
        addComponent(clearSelectionButton);
        addComponent(category);
        addComponent(propertyName);
        addComponent(saveRemoceHl);
    }

    public void buildProperties(List<String> properties) {
        propertyName.removeAllItems();
        propertyName.addItems(properties);
        if (properties != null && !properties.isEmpty()) {
            propertyName.select(properties.get(0));
        }
    }
    
    public void buildCategories(List<PhoneTypes> types) {
        category.removeAllItems();
        category.addItems(types);
        if (types != null && !types.isEmpty()) {
            category.select(types.get(0));
                    
        }
    }
    
    public void disableCategory() {
        category.setEnabled(false);
    }
    
    public void enableCategory() {
        category.setEnabled(true);
    }
    
    public void addCategorySelectListener(Property.ValueChangeListener valueChangeListener) {
        category.addValueChangeListener(valueChangeListener);
    }
}
