/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.test.vaadin.Components;

import com.mycompany.test.vaadin.Entities.ModelProperties;
import com.mycompany.test.vaadin.Entities.Models;
import com.mycompany.test.vaadin.Entities.PhoneTypeProperties;
import com.mycompany.test.vaadin.Entities.Tacs;
import com.mycompany.test.vaadin.Facades.ModelPropertiesFacade;
import com.mycompany.test.vaadin.Facades.OsFacade;
import com.mycompany.test.vaadin.Facades.PhoneTypesFacade;
import com.mycompany.test.vaadin.Facades.TacsFacade;
import com.vaadin.data.Item;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.SelectionEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.inject.Model;

/**
 *
 * @author alex
 */
@Model
public class ModelDetailsPopup extends Window {
    
    private OsFacade osService;
    private TacsFacade tacsService;
    private ModelPropertiesFacade modelPropertiesService;
    private PhoneTypesFacade phoneTypesService;
    
    private Models model;
    private final BasicModelDetailsForm basicForm = new BasicModelDetailsForm();
    private final TacForm tacForm = new TacForm();
    private final ModelTypeForm typeForm = new ModelTypeForm();
    private VerticalLayout windowMenue;
    private final Panel windowContent = new Panel();
    private HorizontalLayout windowLayout = new HorizontalLayout();
    private final FieldGroup modelBinder = new FieldGroup();
    private final FieldGroup tacBinder = new FieldGroup();
    private final FieldGroup typeBinder = new FieldGroup();
    private VerticalLayout basicContent;
    private BeanItemContainer tacsContainer;
    private Grid tacsGrid;
    private HorizontalLayout tacsContent;
    private Tacs tacToAdd = new Tacs();
    private BeanItemContainer<ModelProperties> typesContainer;
    private Grid typesGrid;
    private HorizontalLayout typesContent;
    
    public void buildFromModel(Models model) {
        this.model = model;
        
        setCaption(model.toString());
        buildBasicForm();
        buildBasicContent();
        buildTacsContent();
        buildTypesContent();
        buildLayout();
    }

    private void buildBasicForm() {
        basicForm.buildOses(osService.findAll());
    }

    public ModelDetailsPopup(OsFacade osService, TacsFacade tacsService,
            ModelPropertiesFacade modelPropertiesService, PhoneTypesFacade phoneTypesService) {
        this.osService = osService;
        this.tacsService = tacsService;
        this.modelPropertiesService = modelPropertiesService;
        this.phoneTypesService = phoneTypesService;
        
        configWindow();
    }
   
    private void configWindow() {
        center();
        setClosable(true);
        setModal(true);
        setWidth("1120px");
        setHeight("500px");
    }
    
    private void buildLayout() {
        buildMenue();
        setBasicContent();
        windowLayout = new HorizontalLayout(windowMenue, windowContent);
        windowContent.setSizeFull();
        windowLayout.setMargin(true);
        windowLayout.setSizeFull();
        
        windowLayout.setExpandRatio(windowMenue, 1.5f);
        windowLayout.setExpandRatio(windowContent, 6);
        
        setContent(windowLayout);
    }
    
    private void buildMenue() {
        Button basicButton = new Button("Basics");
        basicButton.addClickListener((Button.ClickEvent event) -> {
            setBasicContent();
        });
        
        Button tacsButton = new Button("Tacs");
        tacsButton.addClickListener((Button.ClickEvent event) -> {
            setTacsContent();
        });
        
        Button typesButton = new Button("Types");
        typesButton.addClickListener((Button.ClickEvent event) -> {
            setTypesContent();
        });
        
        windowMenue = new VerticalLayout(basicButton, tacsButton, typesButton);
        
        for (Component c: windowMenue) {
            c.setStyleName("width-100");
        }
    }
    
    private void buildBasicContent() {
        Item modelItem = new BeanItem(model);
        modelBinder.setItemDataSource(modelItem);
        modelBinder.bindMemberFields(basicForm);
        
        basicContent = new VerticalLayout(basicForm);
        basicContent.setMargin(true);
    }

    private void setBasicContent() {
        windowContent.setContent(basicContent);
    }

    private void buildTacsContent() {
        buildTacsContainer();
        buildTacsGrid();
        
        tacsContent = new HorizontalLayout(tacsGrid, tacForm);
        tacsContent.setMargin(true);
    }
    
    private void setTacsContent() {
        buildTacForm();
        windowContent.setContent(tacsContent);
    }
    
    private void buildTacsContainer() {
        tacsContainer = new BeanItemContainer(Tacs.class, model.getTacsList());
    }
    
    private void buildTacsGrid() {
        tacsGrid = new Grid();
        tacsGrid.setContainerDataSource(tacsContainer);
        tacsGrid.removeColumn("source");
        tacsGrid.removeColumn("model");
        tacsGrid.removeColumn("id");
        tacsGrid.setColumnOrder("tac", "snr", "terminalProfile");
        
        tacsGrid.addSelectionListener(this::tacsGridSelectListener);
    }
    
    private void tacsGridSelectListener(SelectionEvent event) {
        Tacs tac = (Tacs) tacsGrid.getSelectedRow();
        if (tac == null) {
            return;
        }
        
        Item item = new BeanItem(tac);
        tacBinder.setItemDataSource(item);
        tacBinder.bindMemberFields(tacForm);
        
        tacForm.showDeleteAndClearSelectionButtons();
    }
    
    private void buildTacForm() {
        bindNewTac();
        tacForm.addSaveListener(this::saveTacListener);
        tacForm.addDeleteListener(this::deleteTacListener);
        tacForm.addClearSelectionListener(this::clearTacSelectionListener);
    }
    
    private void saveTacListener(Button.ClickEvent event) {
        tacForm.enableValidation();
        try {
            if (!tacBinder.isValid()) {
                Notification.show("values not good", Notification.Type.TRAY_NOTIFICATION);
            }
            tacBinder.commit();
        } catch(FieldGroup.CommitException exp) {
            return;
        }
        
        BeanItem tacItem = (BeanItem) tacBinder.getItemDataSource();
        Tacs tac = (Tacs) tacItem.getBean();
        
        if (tac == null) {
            return;
        }
        
        if (tac.getId() == null) {
            tac.setSource("TECO");
            tac.setModel(model);
            tacsService.create(tac);
            tac = tacsService.findTacByTac(tac.getTac());
            if (tac == null) {
                Notification.show("some erron on save", Notification.Type.ERROR_MESSAGE);
            }
            
            tacsContainer.addBean(tac);
            tacsGrid.setContainerDataSource(tacsContainer);
            
            Notification.show("tac saved", Notification.Type.TRAY_NOTIFICATION);
        } else {
            tacsService.edit(tac);
            tacsGrid.setContainerDataSource(tacsContainer);
            tacsGrid.select(null);
            bindNewTac();
            
            Notification.show("tac edited", Notification.Type.TRAY_NOTIFICATION);
        }
        
        bindNewTac();
    }
    
    private void deleteTacListener(Button.ClickEvent event) {
        BeanItem tacItem = (BeanItem) tacBinder.getItemDataSource();
        Tacs tac = (Tacs) tacItem.getBean();
        
        if (tac == null) {
            return;
        }
        
        tacsService.remove(tac);
        tacsContainer.removeItem(tac);
        tacsGrid.setContainerDataSource(tacsContainer);
        
        bindNewTac();
        
        Notification.show("tac deleted", Notification.Type.TRAY_NOTIFICATION);
    }
    
    private void clearTacSelectionListener(Button.ClickEvent event) {
        bindNewTac();
    }
    
    private void bindNewTac() {
        tacForm.disableValidation();
        tacForm.hideDeleteAndClearSelectionButtons();
        tacToAdd = new Tacs();
        Item tacItem = new BeanItem(tacToAdd);
        tacBinder.setItemDataSource(tacItem);
        tacBinder.bindMemberFields(tacForm); 
    }
    
    private void buildTypesContent() {
        buildTypesContainer();
        buildTypesGrid();
        
        typesContent = new HorizontalLayout(typesGrid, typeForm);
        typesContent.setMargin(true);
    }
    
    private void setTypesContent() {
        windowContent.setContent(typesContent);
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

    private void typesGridSelectionListener(SelectionEvent event) {
        ModelProperties mp = (ModelProperties) typesGrid.getSelectedRow();
                
        List<PhoneTypeProperties> properties = mp.getProperty().getCategory().getPhoneTypePropertiesList();
        List<String> propertyNames = new ArrayList<>();
        for (PhoneTypeProperties ptp: properties) {
            propertyNames.add(ptp.getPropertyName());
        }

        typeForm.buildProperties(propertyNames);
        typeForm.buildCategories(phoneTypesService.findAll());

        BeanItem item = new BeanItem(mp);
        item.addNestedProperty("property.propertyName");
        item.addNestedProperty("property.category");
        typeBinder.setItemDataSource(item);
        typeBinder.bindMemberFields(typeForm);

        typeForm.disableCategory();
    }
}
