package com.mycompany.test.vaadin.Components;

import com.mycompany.test.vaadin.Entities.PhoneTypeProperties;
import com.mycompany.test.vaadin.Entities.PhoneTypes;
import com.vaadin.data.validator.BeanValidator;
import com.vaadin.data.validator.NullValidator;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.TextField;
import java.util.List;
import org.vaadin.maddon.fields.MTextField;


public class PhoneTypePropertiesForm extends FormLayout {
    
    private final TextField propertyName = new MTextField("propertyName");
    private final NativeSelect category = new NativeSelect("category");
    
    public PhoneTypePropertiesForm(List<PhoneTypes> typeses) {
        propertyName.setNullRepresentation("");
        propertyName.addValidator(new BeanValidator(PhoneTypeProperties.class, "propertyName"));

        category.addItems(typeses);
        category.setNullSelectionAllowed(false);
        category.addValidator(new NullValidator("can't be null", false));
        
        disableValidation();
        
        addComponent(propertyName);
        addComponent(category);
    }
    
    public void enableValidation() {
        propertyName.setValidationVisible(true);
        category.setValidationVisible(true);
    }
    
    public void disableValidation() {
        propertyName.setValidationVisible(false);
        category.setValidationVisible(false);
    }

    public void rebuildTypes(List<PhoneTypes> typeses) {
        category.clear();
        category.addItems(typeses);
    }
}
