/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.test.vaadin.Views;

import com.mycompany.test.vaadin.Components.ModelDetailsPopup;
import com.mycompany.test.vaadin.Components.WizardModelPopup;
import com.mycompany.test.vaadin.Entities.Models;
import com.mycompany.test.vaadin.Facades.BehaviourReasonsFacade;
import com.mycompany.test.vaadin.Facades.BehavioursFacade;
import com.mycompany.test.vaadin.Facades.ModelPropertiesFacade;
import com.mycompany.test.vaadin.Facades.ModelsFacade;
import com.mycompany.test.vaadin.Facades.OsFacade;
import com.mycompany.test.vaadin.Facades.PhoneTypePropertiesFacade;
import com.mycompany.test.vaadin.Facades.PhoneTypesFacade;
import com.mycompany.test.vaadin.Facades.ProjectsActionsFacade;
import com.mycompany.test.vaadin.Facades.ProjectsFacade;
import com.mycompany.test.vaadin.Facades.TacsFacade;
import com.mycompany.test.vaadin.UI.TecoMainUi;
import com.vaadin.cdi.CDIView;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.filter.SimpleStringFilter;
import com.vaadin.event.FieldEvents;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.HeaderCell;
import com.vaadin.ui.Grid.HeaderRow;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import javax.annotation.PostConstruct;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

/**
 *
 * @author alex
 */
@CDIView("home")
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class HomeView extends CustomComponent implements View {

    public static final String NAME = "home"; 
    
    @Inject
    private ModelsFacade modelService;
    
    @Inject
    private OsFacade osService;
    
    @Inject
    private TacsFacade tacsService;
    
    @Inject
    private PhoneTypesFacade phoneTypesService;
    
    @Inject
    private ModelPropertiesFacade modelPropertiesService;
    
    @Inject
    private PhoneTypePropertiesFacade phoneTypePropertiesService;
    
    @Inject
    private ProjectsActionsFacade projectsActionsService;
    
    @Inject
    private BehavioursFacade behavioursService;
    
    @Inject
    private BehaviourReasonsFacade behaviourReasonsService;
    
    @Inject
    private ProjectsFacade projectsService;

    private ModelDetailsPopup modelDetailsPopup;
    
    private BeanItemContainer<Models> modelsContainer;
    private Grid modelsGrid;
    private TecoMainUi main;
    private Models modelToAdd;

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        main = (TecoMainUi)getUI();
        main.getAppLayout().getComponent(0).setVisible(true);
        init();
    }
    
    @PostConstruct
    public void init() {
        modelDetailsPopup = new ModelDetailsPopup(osService, tacsService,
                modelPropertiesService, phoneTypesService, phoneTypePropertiesService,
                projectsActionsService, behavioursService, behaviourReasonsService, 
                projectsService, modelService);
        
        buildModelsContainer();
        buildModelsGrid();
        buildLayout();
    }
    
    private void buildLayout() {
        VerticalLayout layout = new VerticalLayout();
        
        Button wizardPopupButton = new Button(FontAwesome.PLUS);
        wizardPopupButton.addClickListener(this::wizardPopupListener);
        
        HorizontalLayout hl = new HorizontalLayout(modelsGrid, wizardPopupButton);
        hl.setSizeFull();
        hl.setExpandRatio(modelsGrid, 4);
        hl.setExpandRatio(wizardPopupButton, 1);
        layout.addComponent(hl);
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
        
        modelsGrid.setWidth(100, Unit.PERCENTAGE);
        
        modelsGrid.setColumnOrder("modelId", "brandName", "modelName", "os");
        
        modelsGrid.addSelectionListener(e -> {
            Models selecteModel = (Models)modelsGrid.getSelectedRow();
            selecteModel = modelService.loadLazyCollectionForModel(selecteModel.getModelId());
            
            modelDetailsPopup.buildFromModel(selecteModel);
            
            main.getUI().addWindow(modelDetailsPopup);
        });
        
        HeaderRow header = modelsGrid.appendHeaderRow();
        HeaderCell brandCell = header.getCell("brandName");
        
        TextField brandFilter = new TextField();
        brandFilter.addTextChangeListener((FieldEvents.TextChangeEvent event) -> {
            modelsContainer.removeContainerFilters("brandName");
            
            if (!event.getText().isEmpty()) {
                modelsContainer.addContainerFilter(
                        new SimpleStringFilter("brandName", event.getText(), true, false));
            }
        });
        brandCell.setComponent(brandFilter);
        
        HeaderCell modelCell = header.getCell("modelName");
        
        TextField modelFilter = new TextField();
        modelFilter.addTextChangeListener((FieldEvents.TextChangeEvent event) -> {
            modelsContainer.removeContainerFilters("modelName");
            
            if (!event.getText().isEmpty()) {
                modelsContainer.addContainerFilter(
                        new SimpleStringFilter("modelName", event.getText(), true, false));
            }
        });
        modelCell.setComponent(modelFilter);
        
    }
    
    private void wizardPopupListener(Button.ClickEvent event) {
        modelToAdd = new Models();
        WizardModelPopup wizardModelPopup = new WizardModelPopup(modelService, osService, tacsService, phoneTypesService,
                phoneTypePropertiesService, modelPropertiesService, behaviourReasonsService, projectsActionsService,
                behavioursService, projectsService, modelToAdd);
        wizardModelPopup.addCloseListener(new Window.CloseListener() {
            @Override
            public void windowClose(Window.CloseEvent e) {
                if (modelToAdd != null) {
                    modelsContainer.addBean(modelToAdd);
                    modelsGrid.setContainerDataSource(modelsContainer);
                }
            }
        });
        
        main.addWindow(wizardModelPopup);
    }
    
}
