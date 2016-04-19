/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.test.vaadin.Views;

import com.mycompany.test.vaadin.Components.ModelDetailsPopup;
import com.mycompany.test.vaadin.Entities.Models;
import com.mycompany.test.vaadin.Facades.ModelsFacade;
import com.mycompany.test.vaadin.Facades.OsFacade;
import com.mycompany.test.vaadin.UI.TecoMainUi;
import com.vaadin.cdi.CDIView;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.filter.SimpleStringFilter;
import com.vaadin.event.FieldEvents;
import com.vaadin.event.SelectionEvent;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.HeaderCell;
import com.vaadin.ui.Grid.HeaderRow;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import javax.annotation.PostConstruct;
import javax.inject.Inject;

/**
 *
 * @author alex
 */
@CDIView("home")
public class HomeView extends CustomComponent implements View {

    public static final String NAME = "home"; 
    
    @Inject
    private ModelsFacade modelService;
    
    @Inject 
    private OsFacade osService;
    
    private BeanItemContainer<Models> modelsContainer;
    private Grid modelsGrid;
    private TecoMainUi main;

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        main = (TecoMainUi)getUI();
        main.getAppLayout().getComponent(0).setVisible(true);
    }
    
    @PostConstruct
    public void init() {
        buildModelsContainer();
        buildModelsGrid();
        buildLayout();
    }
    
    private void buildLayout() {
        VerticalLayout layout = new VerticalLayout();
        layout.addComponent(modelsGrid);
        layout.setMargin(true);
        layout.setSizeFull();
        setCompositionRoot(layout);
    }
    
    private void buildModelsContainer() {
        modelsContainer = new BeanItemContainer<>(Models.class, modelService.findAll());
    }
    
    private void buildModelsGrid() {
        modelsGrid = new Grid();
        modelsGrid.setContainerDataSource(modelsContainer);
        modelsGrid.removeColumn("tacsList");
        modelsGrid.removeColumn("modelPropertiesList");
        modelsGrid.removeColumn("behavioursList");
        
        modelsGrid.setWidth(72, Unit.PERCENTAGE);
        
        modelsGrid.setColumnOrder("modelId", "brandName", "modelName", "os");
        
        modelsGrid.addSelectionListener(new SelectionEvent.SelectionListener() {
            @Override
            public void select(SelectionEvent event) {
                Models selecteModel = (Models)modelsGrid.getSelectedRow();
                
                ModelDetailsPopup popup = new ModelDetailsPopup(selecteModel, osService.findAll());
                
                main.getUI().addWindow(popup);
            }
        });
        
        HeaderRow header = modelsGrid.appendHeaderRow();
        HeaderCell brandCell = header.getCell("brandName");
        
        TextField brandFilter = new TextField();
        brandFilter.addTextChangeListener(new FieldEvents.TextChangeListener() {
            @Override
            public void textChange(FieldEvents.TextChangeEvent event) {
                modelsContainer.removeContainerFilters("brandName");
                
                if (!event.getText().isEmpty()) {
                    modelsContainer.addContainerFilter(
                            new SimpleStringFilter("brandName", event.getText(), true, false));
                }
            }
        });
        brandCell.setComponent(brandFilter);
        
        HeaderCell modelCell = header.getCell("modelName");
        
        TextField modelFilter = new TextField();
        modelFilter.addTextChangeListener(new FieldEvents.TextChangeListener() {
            @Override
            public void textChange(FieldEvents.TextChangeEvent event) {
                modelsContainer.removeContainerFilters("modelName");
                
                if (!event.getText().isEmpty()) {
                    modelsContainer.addContainerFilter(
                            new SimpleStringFilter("modelName", event.getText(), true, false));
                }
            }
        });
        modelCell.setComponent(modelFilter);
        
    }
    
}
