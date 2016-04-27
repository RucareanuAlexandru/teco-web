/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.test.vaadin.Components;

import com.mycompany.test.vaadin.Entities.Models;
import com.mycompany.test.vaadin.Facades.ModelsFacade;
import com.mycompany.test.vaadin.Facades.OsFacade;
import com.vaadin.data.Item;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.Button;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;

/**
 *
 * @author alex
 */
public class ModelBasicDetailsContent extends VerticalLayout {
    
    private final ModelsFacade modelsService;
    private final OsFacade osService;
    
    private final BasicModelDetailsForm basicForm = new BasicModelDetailsForm();
    private Models model;
    private final FieldGroup modelBinder = new FieldGroup();
    

    public ModelBasicDetailsContent(Models model, ModelsFacade modelsSerice, OsFacade osService) {
        this.modelsService = modelsSerice;
        this.model = model;
        this.osService = osService;
        
        buildBasicForm();
        buildBasicContent();
    }
    
    private void buildBasicForm() {
        basicForm.buildOses(osService.findAll());
    }
    
    private void buildBasicContent() {
        Item modelItem = new BeanItem(model);
        modelBinder.setItemDataSource(modelItem);
        modelBinder.bindMemberFields(basicForm);
        
        basicForm.disableModelId();
        basicForm.addSaveListener(this::saveBasicModel);
        
        addComponent(basicForm);
        setMargin(true);
    }
    
    private void saveBasicModel(Button.ClickEvent event) {
        basicForm.enableValidation();
        try {
            if (!modelBinder.isValid()) {
                Notification.show("Values not good", Notification.Type.TRAY_NOTIFICATION);
            }
            modelBinder.commit();
        } catch(FieldGroup.CommitException ce) {
            return;
        }
        
        BeanItem modelItem = (BeanItem) modelBinder.getItemDataSource();
        Models m = (Models) modelItem.getBean();
        
        if (m != null) {
            modelsService.edit(m);
            
            Notification.show("Model basic's details saved", Notification.Type.TRAY_NOTIFICATION);
        }
    }
    
}
