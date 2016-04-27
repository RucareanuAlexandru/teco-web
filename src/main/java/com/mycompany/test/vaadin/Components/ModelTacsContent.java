/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.test.vaadin.Components;

import com.mycompany.test.vaadin.Entities.Models;
import com.mycompany.test.vaadin.Entities.Tacs;
import com.mycompany.test.vaadin.Facades.TacsFacade;
import com.vaadin.data.Item;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.SelectionEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;

/**
 *
 * @author alex
 */
public class ModelTacsContent extends HorizontalLayout {

    private TacsFacade tacsService;
    
    private BeanItemContainer tacsContainer;
    private Grid tacsGrid;
    private Models model;
    private FieldGroup tacBinder = new FieldGroup();
    private TacForm tacForm = new TacForm();
    private Tacs tacToAdd;

    public ModelTacsContent(TacsFacade tacsService, Models model) {
        this.tacsService = tacsService;
        this.model = model;
        
        buildLayout();
    }
    
    public void buildLayout() {
        buildTacsContainer();
        buildTacsGrid();
        buildTacForm();
        
        addComponent(tacsGrid);
        addComponent(tacForm);
        setMargin(true);
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
            bindNewTac();
            
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
}
