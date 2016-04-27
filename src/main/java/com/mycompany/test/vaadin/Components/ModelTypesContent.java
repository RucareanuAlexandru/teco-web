/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.test.vaadin.Components;

import com.mycompany.test.vaadin.Entities.ModelProperties;
import com.mycompany.test.vaadin.Entities.Models;
import com.mycompany.test.vaadin.Entities.PhoneTypeProperties;
import com.mycompany.test.vaadin.Entities.PhoneTypes;
import com.mycompany.test.vaadin.Facades.ModelPropertiesFacade;
import com.mycompany.test.vaadin.Facades.PhoneTypePropertiesFacade;
import com.mycompany.test.vaadin.Facades.PhoneTypesFacade;
import com.vaadin.data.Property;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.SelectionEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author alex
 */
public class ModelTypesContent extends HorizontalLayout {
    
    private final PhoneTypesFacade phoneTypesService;
    private final PhoneTypePropertiesFacade phoneTypePropertiesService;
    private final ModelPropertiesFacade modelPropertiesService;

    private Models model;
    private BeanItemContainer<ModelProperties> typesContainer;
    private Grid typesGrid;
    private ModelTypeForm typeForm = new ModelTypeForm();
    private FieldGroup typeBinder = new FieldGroup();
    private ModelProperties typeToAdd;

    public ModelTypesContent(PhoneTypesFacade phoneTypesService, PhoneTypePropertiesFacade phoneTypePropertiesService, ModelPropertiesFacade modelPropertiesService, Models model) {
        this.phoneTypesService = phoneTypesService;
        this.phoneTypePropertiesService = phoneTypePropertiesService;
        this.modelPropertiesService = modelPropertiesService;
        this.model = model;
        
        buildLayout();
    }
    
    private void buildLayout() {
        buildTypesContainer();
        buildTypesGrid();
        buildTypeForm();
        
        addComponent(typesGrid);
        addComponent(typeForm);
        setMargin(true);
    }
    
    
    private void buildTypesContainer() {
        typesContainer = new BeanItemContainer<>(ModelProperties.class, model.getModelPropertiesList());
        typesContainer.addNestedContainerProperty("property.propertyName");
        typesContainer.addNestedContainerProperty("property.category");
    }
    
    private void buildTypesGrid() {
        typesGrid = new Grid();
        typesGrid.setContainerDataSource(typesContainer);
        typesGrid.removeColumn("id");
        typesGrid.removeColumn("model");
        typesGrid.removeColumn("property");
        typesGrid.setColumnOrder("property.category", "property.propertyName");
        
        typesGrid.addSelectionListener(this::typesGridSelectionListener);
    }
    
    private void buildTypeForm() {
        typeForm.buildCategories(phoneTypesService.findAll());
        typeForm.addSaveListener(this::saveTypeListener);
        typeForm.addDeleteListener(this::deleteTypeListener);
        typeForm.addClearSelectionListener(this::clearTypeSelectionListener);
        typeForm.addCategorySelectListener(this::typesCategoryPropertyChangeListener);
        
        bindNewType();
    }

    private void typesGridSelectionListener(SelectionEvent event) {
        ModelProperties mp = (ModelProperties) typesGrid.getSelectedRow();
        
        if (mp == null) {
            return;
        }
        PhoneTypes category = mp.getProperty().getCategory();
        
        List<String> propertyNames = getStringListFormPropertiesList(category);

        typeForm.buildProperties(propertyNames);
        typeForm.buildCategories(phoneTypesService.findAll());

        bindType(mp);

        typeForm.disableCategory();
        typeForm.showDeleteAndClearSelectionButtons();
    }

    private List<String> getStringListFormPropertiesList(PhoneTypes category) {
        List<PhoneTypeProperties> properties = category.getPhoneTypePropertiesList();
        List<String> propertyNames = new ArrayList<>();
        for (PhoneTypeProperties ptp: properties) {
            propertyNames.add(ptp.getPropertyName());
        }
        return propertyNames;
    }

    private void bindType(ModelProperties mp) {
        BeanItem item = new BeanItem(mp);
        item.addNestedProperty("property.propertyName");
        item.addNestedProperty("property.category");
        typeBinder.setItemDataSource(item);
        typeBinder.bindMemberFields(typeForm);
    }
    
    private void bindNewType() {
        typeForm.hideDeleteAndClearSelectionButtons();
        typeToAdd = new ModelProperties();
        typeToAdd.setModel(model);
        PhoneTypeProperties ptp = new PhoneTypeProperties();
        typeToAdd.setProperty(ptp);
        bindType(typeToAdd);
    }
    
    private void clearTypeSelectionListener(Button.ClickEvent event) {
        bindNewType();
    }
    
    private void saveTypeListener(Button.ClickEvent event) {
        try {
            if (!typeBinder.isValid()) {
                Notification.show("Values not good", Notification.Type.ERROR_MESSAGE);
            }
            typeBinder.commit();
         } catch(FieldGroup.CommitException exception) {
             return;
         }
        
        BeanItem item = (BeanItem) typeBinder.getItemDataSource();
        ModelProperties type = (ModelProperties) item.getBean();
        
        if (type == null) {
            return;
        }
        
        PhoneTypeProperties ptp = phoneTypePropertiesService.findPTPByPropertyNameAndCategory(
                    type.getProperty().getPropertyName(), type.getProperty().getCategory());
        
        if (type.getId() == null) {         
            type.setModel(model);
            type.setProperty(ptp);
            modelPropertiesService.create(type);
            
            type = modelPropertiesService.findByModelAndProperty(model, ptp);
            
            typesContainer.addBean(type);
            typesGrid.setContainerDataSource(typesContainer);
            bindNewType();
            
            Notification.show("type saved", Notification.Type.TRAY_NOTIFICATION);
        } else {
            type.setProperty(ptp);
            modelPropertiesService.edit(type);
            typesGrid.setContainerDataSource(typesContainer);
            typesGrid.select(null);
            bindNewType();
            
            Notification.show("type edited", Notification.Type.TRAY_NOTIFICATION);
        }
    }
    
    private void deleteTypeListener(Button.ClickEvent event) {
        ModelProperties mp = (ModelProperties) typesGrid.getSelectedRow();
        
        if (mp == null) {
            return;
        }
        
        modelPropertiesService.remove(mp);
        typesContainer.removeItem(mp);
        typesGrid.setContainerDataSource(typesContainer);
        
        bindNewType();
        
        Notification.show("type deleted", Notification.Type.TRAY_NOTIFICATION);
    }
    
    private void typesCategoryPropertyChangeListener(Property.ValueChangeEvent vcl) {
        PhoneTypes pt = (PhoneTypes) vcl.getProperty().getValue();
        if (pt == null) {
            return;
        }
        typeForm.buildProperties(getStringListFormPropertiesList(pt));
    }
    
}
