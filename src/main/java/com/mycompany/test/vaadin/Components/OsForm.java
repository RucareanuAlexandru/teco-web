package com.mycompany.test.vaadin.Components;

import com.mycompany.test.vaadin.Entities.Os;
import com.vaadin.data.validator.BeanValidator;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class OsForm extends VerticalLayout {
    
    private TextField os = new TextField("Os");
    private TextField version = new TextField("Version");

    public OsForm() {
        addStyleName("osForm");
        setMargin(false);
        os.setNullRepresentation("");
        version.setNullRepresentation("");
        
        os.addValidator(new BeanValidator(Os.class, "os"));
        version.addValidator(new BeanValidator(Os.class, "version"));
        
        os.setValidationVisible(false);
        version.setValidationVisible(false);
        
        addComponent(os);
        addComponent(version);
    }
    
    public void enabelValidation() {
        os.setValidationVisible(true);
        version.setValidationVisible(true);
    }
    
    
    
}
