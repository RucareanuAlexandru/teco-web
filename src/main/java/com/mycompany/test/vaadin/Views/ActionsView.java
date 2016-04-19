/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.test.vaadin.Views;

import com.mycompany.test.vaadin.Components.StkActionForm;
import com.mycompany.test.vaadin.Components.StkActionPropertyForm;
import com.mycompany.test.vaadin.Components.StkActionType;
import com.mycompany.test.vaadin.Entities.Projects;
import com.mycompany.test.vaadin.Entities.ProjectsActions;
import com.mycompany.test.vaadin.Entities.ProjectsActionsProperties;
import com.mycompany.test.vaadin.Entities.StkActionProperties;
import com.mycompany.test.vaadin.Entities.StkActions;
import com.mycompany.test.vaadin.Facades.ProjectsActionsFacade;
import com.mycompany.test.vaadin.Facades.ProjectsActionsPropertiesFacade;
import com.mycompany.test.vaadin.Facades.ProjectsFacade;
import com.mycompany.test.vaadin.Facades.StkActionPropertiesFacade;
import com.mycompany.test.vaadin.Facades.StkActionsFacade;
import com.mycompany.test.vaadin.UI.TecoMainUi;
import com.vaadin.cdi.CDIView;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.SelectionEvent;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.shared.ui.grid.HeightMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Layout;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TwinColSelect;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.inject.Inject;

/**
 *
 * @author alex
 */
@CDIView("actions")
public class ActionsView extends CustomComponent implements View{

    public static final String NAME = "actions";
    
    @Inject
    private StkActionsFacade actionsService;
    
    @Inject
    private StkActionPropertiesFacade actionPropertiesService;
    
    @Inject
    private ProjectsFacade projectsService;
    
    @Inject
    private ProjectsActionsFacade projectsActionsService;
    
    @Inject
    private ProjectsActionsPropertiesFacade projectsActionsPropertiesSerice;
    
    // design components
    private Grid actionsGrid;
    private Grid propsGrid;
    private BeanItemContainer<StkActions> actionsContainer;
    private BeanItemContainer<StkActionProperties> propsContainer;
    private StkActionForm actionForm;
    private StkActionPropertyForm actionPropertyForm;
    private FieldGroup actionBinder;
    private FieldGroup propBinder;
    private Button saveActionButton;
    private Button removeActionButon;
    private Button clearActionSelectionButton;
    private TabSheet tabs;
    private Button savePropButton;
    private Button removePropButton;
    private Button clearPropSelectionButton;
    private VerticalLayout mainLayout;
    private NativeSelect projectDD;
    private TwinColSelect projActionAssociation;
    
    // logic components
    private StkActionType actionType;
    private StkActions actionToAdd;
    private StkActionProperties actionPropertyToAdd;
    private List<StkActions> selectedProjectActions;
    private List<StkActions> initialSelectedProjectActions;
    private Projects selectedProject;
    private Button saveProjectActionAssociationButton;
    private NativeSelect actionsDD;
    private StkActions selectedAction;
    private TwinColSelect projActionPropertyAssociation;
    private List<StkActionProperties> selectedActionProperties;
    private ArrayList<StkActionProperties> initialSelectedProjectActionProperties;
    private Button saveProjectActionPropertyAssociationButton;
    private boolean isRebuild;

    
    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        TecoMainUi main = (TecoMainUi)getUI();
        main.getAppLayout().getComponent(0).setVisible(true);
    }
    
    @PostConstruct
    public void init() {
        buildTab(StkActionType.COMMAND);
        buildTabSheet();
    }
    
    private void buildTabSheet() {
        tabs = new TabSheet();tabs.setHeight(100, Unit.PERCENTAGE);
        tabs.addTab(new VerticalLayout(mainLayout), StkActionType.COMMAND.name().toLowerCase());
        tabs.addTab(new VerticalLayout(), StkActionType.EVENT.name().toLowerCase());
        tabs.addTab(new VerticalLayout(), StkActionType.SPECIAL.name().toLowerCase());
        tabs.setStyleName(ValoTheme.TABSHEET_FRAMED);
        
        tabs.addSelectedTabChangeListener(this::selectTabListener);
        
        setCompositionRoot(tabs);
        setSizeFull();
    }
    
    private void buildTab(StkActionType tabType) {
        actionType = tabType;
        buildActionsContainer();
        buildActionsGrid();
        buildActionsForm();
        bindNewAction();
        buildPropsContaier();
        buildPropsGrid();
        buildPropsForm();
        bindNewProp();
        buildAssociation();
        builLayout();
        isRebuild = false;
    }
    
    private void builLayout() {
        buildButtons();
        
        // actions layout
        HorizontalLayout actionsButtonsHl = new HorizontalLayout(saveActionButton, removeActionButon);
        actionsButtonsHl.setSpacing(true);
        VerticalLayout actionFormVl = new VerticalLayout(clearActionSelectionButton, actionForm, actionsButtonsHl);
        HorizontalLayout actionHl = new HorizontalLayout(actionsGrid, actionFormVl);
        actionHl.setSpacing(true);
        
        // props layout
        HorizontalLayout propsButtonsHl = new HorizontalLayout(savePropButton, removePropButton);
        propsButtonsHl.setSpacing(true);
        VerticalLayout propsFormVl = new VerticalLayout(clearPropSelectionButton, actionPropertyForm, propsButtonsHl);
        HorizontalLayout propHl = new HorizontalLayout(propsGrid, propsFormVl);
        propHl.setSpacing(true);
        
        VerticalLayout projectActionAssociationVl = new VerticalLayout(projectDD, projActionAssociation, saveProjectActionAssociationButton);
        projectActionAssociationVl.setSpacing(true);
        projectActionAssociationVl.setMargin(new MarginInfo(false, true, false, false));
        
        VerticalLayout projectActionPropertyAssociationVl = new VerticalLayout(actionsDD, projActionPropertyAssociation,
                saveProjectActionPropertyAssociationButton);
        projectActionPropertyAssociationVl.setSpacing(true);
        
        HorizontalLayout associationHl = new HorizontalLayout(projectActionAssociationVl, projectActionPropertyAssociationVl);
        mainLayout = new VerticalLayout(actionHl, propHl, associationHl);
        mainLayout.setSpacing(true);
        
        mainLayout.setSizeFull();
    }
    
    private void buildAssociation() {
        buildProjectsDropDown();
        buildProjectActionAssociation();
        buildActionsDropDown();
        buildProjectActionPropertyAssociation();
    }
    
    private void rebuildAssociation() {
        isRebuild = true;
        buildSelectedActions();
        addDataSourceForProjectActionAssociation();
        buildSelectedActionProperties();
        addDataSourceForProjectActionPropertyAssociation();
        buildActionDDContainer();
        isRebuild = false;
    }
    
    private void buildSelectedActions() {
        selectedProjectActions = projectsActionsService.getActionsByProjectAndActionType(selectedProject, actionType.name());
        initialSelectedProjectActions = new ArrayList<>(selectedProjectActions); 
    }
    
    private void buildSelectedActionProperties() {
        selectedActionProperties = projectsActionsPropertiesSerice.findActionPropertiesByProjectAndAction(selectedProject, selectedAction);
        initialSelectedProjectActionProperties = new ArrayList<>(selectedActionProperties);
    }
    
    private void buildProjectsDropDown() {
        List<Projects> projects = projectsService.findAll();
        projectDD = new NativeSelect("Project", projects);
        projectDD.setNullSelectionAllowed(false);
        selectedProject = projects.get(0);
        projectDD.select(selectedProject);
        buildSelectedActions();
        projectDD.addValueChangeListener(this::projectsDDValueChangeListener);
    }
    
    private void buildActionDDContainer() {
        BeanItemContainer<StkActions> actionsDDContainer = new BeanItemContainer<>(StkActions.class, selectedProjectActions);
        actionsDD.setContainerDataSource(actionsDDContainer);
        if (actionsDDContainer.size() > 0) {
            StkActions action = actionsDDContainer.getIdByIndex(0);
            actionsDD.select(action);
            selectedAction = action;
        }
    }
    
    private void buildActionsDropDown() {
        actionsDD = new NativeSelect("Actions");
        actionsDD.setNullSelectionAllowed(false);
        buildActionDDContainer();
        actionsDD.addValueChangeListener(this::actionsDDValueChangeListener);
        buildSelectedActionProperties();
    }
    
    private void buildProjectActionAssociation() {
        projActionAssociation = new TwinColSelect();
        addDataSourceForProjectActionAssociation();
        projActionAssociation.addValueChangeListener(this::projActionAssociationValueChangeListener);
        
        projActionAssociation.setRightColumnCaption("Selected actions");
        projActionAssociation.setLeftColumnCaption("Available actions");
        projActionAssociation.setRows(5);
    }
    
    private void addDataSourceForProjectActionAssociation() {
        projActionAssociation.setContainerDataSource(null);
        projActionAssociation.setContainerDataSource(actionsContainer);
        projActionAssociation.setValue(selectedProjectActions);
    }
    
    private void buildProjectActionPropertyAssociation() {
        projActionPropertyAssociation = new TwinColSelect();
        addDataSourceForProjectActionPropertyAssociation();
        projActionPropertyAssociation.addValueChangeListener(this::projActionPropertyAssociationValueChangeListener);
        
        projActionPropertyAssociation.setRightColumnCaption("Saved properties");
        projActionPropertyAssociation.setLeftColumnCaption("Available properties");
        projActionPropertyAssociation.setRows(5);
    }
    
    private void addDataSourceForProjectActionPropertyAssociation() {
        projActionPropertyAssociation.setContainerDataSource(propsContainer);
        projActionPropertyAssociation.setValue(selectedActionProperties);
    }
    
    private void buildButtons() {
        saveActionButton = new Button(FontAwesome.SAVE);
        saveActionButton.addClickListener(this::saveActionListener);
        saveActionButton.setImmediate(true);
        removeActionButon = new Button(FontAwesome.REMOVE);
        removeActionButon.addClickListener(this::removeActionListener);
        removeActionButon.setVisible(false);
        
        clearActionSelectionButton = new Button("Clear selection");
        clearActionSelectionButton.addClickListener(this::clearActionSelectionListener);
        clearActionSelectionButton.setVisible(false);
        
        savePropButton = new Button(FontAwesome.SAVE);
        savePropButton.addClickListener(this::saveActionPropListener);
        
        removePropButton = new Button(FontAwesome.REMOVE);
        removePropButton.addClickListener(this::removeActionPropListener);
        removePropButton.setVisible(false);
        
        clearPropSelectionButton = new Button("Clear selction");
        clearPropSelectionButton.addClickListener(this::clearPropSelectionListener);
        clearPropSelectionButton.setVisible(false);
        
        saveProjectActionAssociationButton = new Button(FontAwesome.SAVE);
        saveProjectActionAssociationButton.addClickListener(this::saveProjectActionAssociation);
        
        saveProjectActionPropertyAssociationButton = new Button(FontAwesome.SAVE);
        saveProjectActionPropertyAssociationButton.addClickListener(this::saveProjectActionPropertyAssociation);
    }
    
    private void buildActionsContainer() {
        actionsContainer = new BeanItemContainer<>(StkActions.class,
                actionsService.findStkActionsByType(actionType.name()));
    }
    
    private void buildActionsGrid() {
        actionsGrid = new Grid();
        actionsGrid.setContainerDataSource(actionsContainer);
        actionsGrid.removeColumn("type");
        actionsGrid.removeColumn("id");
        actionsGrid.removeColumn("projectsActionsList");
        actionsGrid.setColumnOrder("name", "description");
        actionsGrid.setHeightByRows(6);
        actionsGrid.setHeightMode(HeightMode.ROW);
        
        actionsGrid.addSelectionListener(this::actionsGridSelectListener);
        
    }
    
    private void buildPropsContaier() {
        propsContainer = new BeanItemContainer<>(StkActionProperties.class,
                actionPropertiesService.findStkActionPropertiesByType(actionType.name()));
    }
    
    private void buildPropsGrid() {
        propsGrid = new Grid();
        propsGrid.setContainerDataSource(propsContainer);
        propsGrid.removeColumn("type");
        propsGrid.removeColumn("id");
        propsGrid.removeColumn("projectsActionsPropertiesList");
        propsGrid.setColumnOrder("name", "description");
        propsGrid.setHeightByRows(6);
        propsGrid.setHeightMode(HeightMode.ROW);
        
        propsGrid.addSelectionListener(this::propsGridSelectListener);
    }
    
    private void buildActionsForm() {
        actionBinder = new FieldGroup();
        actionForm = new StkActionForm();
    }
    
    private void buildPropsForm() {
        propBinder = new FieldGroup();
        actionPropertyForm = new StkActionPropertyForm();
    }
    
    private void bindNewAction() {
        actionToAdd = new StkActions();
        actionToAdd.setType(actionType.name());
        Item item = new BeanItem(actionToAdd);
        actionBinder.setItemDataSource(item);
        actionBinder.bindMemberFields(actionForm);
        
        actionForm.disableValidation();
    }
    
    private void bindNewProp() {
        actionPropertyToAdd = new StkActionProperties();
        actionPropertyToAdd.setType(actionType.name());
        Item item = new BeanItem(actionPropertyToAdd);
        propBinder.setItemDataSource(item);
        propBinder.bindMemberFields(actionPropertyForm);
        
        actionPropertyForm.disableValidation();
    }
    
    private void saveActionListener(Button.ClickEvent event) {
        actionForm.enableValidation();
        try {
            if (!actionBinder.isValid()) {
                return;
            }
            actionBinder.commit();
        } catch(FieldGroup.CommitException exception) {
            return;
        }
        
        BeanItem<StkActions> actionBean = (BeanItem<StkActions>)actionBinder.getItemDataSource();
        StkActions action = actionBean.getBean();
        
        if (action.getId() == null) {
            actionsService.create(action);
            action = actionsService.findStkActionByNameAndType(action.getName(), action.getType());
            
            actionsContainer.addBean(action);
            actionsGrid.setContainerDataSource(actionsContainer);
            bindNewAction();
            actionsGrid.select(null);
            Notification.show("action created", Notification.Type.TRAY_NOTIFICATION);
        } else {
            actionsService.edit(action);
            actionsGrid.setContainerDataSource(actionsContainer);
            bindNewAction();
            actionsGrid.select(null);
            Notification.show("action edited", Notification.Type.TRAY_NOTIFICATION);
        }
        
        rebuildAssociation();
    }
    
    private void removeActionListener(Button.ClickEvent event) {
        BeanItem<StkActions> actionBean = (BeanItem<StkActions>) actionBinder.getItemDataSource();
        StkActions action = actionBean.getBean();
        
        actionsService.remove(action);
        actionsContainer.removeItem(action);
        actionsGrid.setContainerDataSource(actionsContainer);
        
        bindNewAction();
        removeActionButon.setVisible(false);
        clearActionSelectionButton.setVisible(false);
        
        rebuildAssociation();
        
        Notification.show("action deleted", Notification.Type.TRAY_NOTIFICATION);
    }
    
    private void selectTabListener(TabSheet.SelectedTabChangeEvent event) {
        TabSheet tabSheet = event.getTabSheet();
        
        Layout tab = (Layout) tabSheet.getSelectedTab();
        String caption = tabSheet.getTab(tabSheet.getSelectedTab()).getCaption();

        tab.removeAllComponents();
        buildTab(StkActionType.getStkActionTypeFromString(caption));
        tab.addComponent(mainLayout);
    }
    
    private void actionsGridSelectListener(SelectionEvent event) {
        StkActions action = (StkActions)actionsGrid.getSelectedRow();
        if (action == null) {
            return;
        }
        
        Item item = new BeanItem(action);
        actionBinder.setItemDataSource(item);
        actionBinder.bindMemberFields(actionForm);
        
        removeActionButon.setVisible(true);
        clearActionSelectionButton.setVisible(true);
    }
    
    private void clearActionSelectionListener(Button.ClickEvent event) {
        bindNewAction();
        removeActionButon.setVisible(false);
        clearActionSelectionButton.setVisible(false);
        actionsGrid.select(null);
    }
    
    private void saveActionPropListener(Button.ClickEvent event) {
        actionPropertyForm.enableValidation();
        try {
            if (!propBinder.isValid()) {
                return;
            }
            propBinder.commit();
        } catch(FieldGroup.CommitException exception) {
            return;
        }
        
        BeanItem<StkActionProperties> propBean = (BeanItem<StkActionProperties>) propBinder.getItemDataSource();
        StkActionProperties prop = propBean.getBean();
        
        if (prop.getId() == null) {
            actionPropertiesService.create(prop);
            prop = actionPropertiesService.findStkActionPropertyByNameAndType(prop.getName(), prop.getType());
            
            propsContainer.addBean(prop);
            propsGrid.setContainerDataSource(propsContainer);
            bindNewProp();
            propsGrid.select(null);
            Notification.show("prop created", Notification.Type.TRAY_NOTIFICATION);
        } else {
            actionPropertiesService.edit(prop);
            propsGrid.setContainerDataSource(propsContainer);
            bindNewProp();
            propsGrid.select(null);
            Notification.show("prop edited", Notification.Type.TRAY_NOTIFICATION);
        }
        
        rebuildAssociation();
    }
    
    private void removeActionPropListener(Button.ClickEvent event) {
        BeanItem<StkActionProperties> propBean = (BeanItem<StkActionProperties>) propBinder.getItemDataSource();
        StkActionProperties prop = propBean.getBean();
        
        actionPropertiesService.remove(prop);
        propsContainer.removeItem(prop);
        propsGrid.setContainerDataSource(propsContainer);
        
        bindNewProp();
        clearPropSelectionButton.setVisible(false);
        removePropButton.setVisible(false);
        
        rebuildAssociation();
        Notification.show("prop removed", Notification.Type.TRAY_NOTIFICATION);
    }
    
    private void clearPropSelectionListener(Button.ClickEvent event) {
        bindNewProp();
        propsGrid.select(null);
        clearPropSelectionButton.setVisible(false);
        removePropButton.setVisible(false);
    }
    
    private void propsGridSelectListener(SelectionEvent event) {
        StkActionProperties prop = (StkActionProperties)propsGrid.getSelectedRow();
        if (prop == null) {
            return;
        }
        
        Item item = new BeanItem(prop);
        propBinder.setItemDataSource(item);
        propBinder.bindMemberFields(actionPropertyForm);
        
        clearPropSelectionButton.setVisible(true);
        removePropButton.setVisible(true);
    }

    private void projActionAssociationValueChangeListener(Property.ValueChangeEvent event) {
        if (isRebuild) {
            return;
        }
        
        Set<StkActions> actions = (Set<StkActions>)event.getProperty().getValue();
        List<StkActions> auxActions = new ArrayList<>(actions);
        List<StkActions> auxSelectedActions = new ArrayList<>(selectedProjectActions);
        
        StkActions action = null;
        
        auxActions.removeAll(auxSelectedActions);
        if (auxActions.isEmpty()) {
            auxSelectedActions.removeAll(actions);
            if (!auxSelectedActions.isEmpty()) {
                action = auxSelectedActions.get(0);
                selectedProjectActions.remove(action);
            }
        } else {
            action = auxActions.get(0);
            selectedProjectActions.add(action);
        }
    }
    
    private void projectsDDValueChangeListener(Property.ValueChangeEvent event) {
        selectedProject = (Projects) event.getProperty().getValue();
        buildSelectedActions();
        addDataSourceForProjectActionAssociation();
        buildSelectedActionProperties();
        addDataSourceForProjectActionPropertyAssociation();
        buildActionDDContainer();
    }
    
    private void saveProjectActionAssociation(Button.ClickEvent event) {
        List<StkActions> helper = new ArrayList<>(selectedProjectActions);
        
        helper.removeAll(initialSelectedProjectActions); // newly added actions
        helper.forEach(action -> saveProjectAction(action));
        
        initialSelectedProjectActions.removeAll(selectedProjectActions); // deleted actions
        initialSelectedProjectActions.forEach(action -> removeProjectAction(action));
        
        initialSelectedProjectActions = new ArrayList<>(selectedProjectActions);
        
        buildActionDDContainer();
        
        Notification.show("Association saved", Notification.Type.TRAY_NOTIFICATION);
    }
    
    private void saveProjectAction(StkActions action) {
        projectsActionsService.create(new ProjectsActions(selectedProject, action));
    }
    
    private void removeProjectAction(StkActions action) {
        ProjectsActions pa = projectsActionsService.findByProjectAndAction(selectedProject, action);
        if (pa == null) {
            return;
        }
        projectsActionsService.remove(pa);
    }
    
    private void saveProjectActionPropertyAssociation(Button.ClickEvent event) {
        List<StkActionProperties> helper = new ArrayList<>(selectedActionProperties);
        
        helper.removeAll(initialSelectedProjectActionProperties);
        helper.forEach(actionProperty -> saveProjectActionPoroperty(actionProperty));
        
        initialSelectedProjectActionProperties.removeAll(selectedActionProperties);
        initialSelectedProjectActionProperties.forEach(actionProperty -> removeProjectActionProperty(actionProperty));
        
        initialSelectedProjectActionProperties = new ArrayList<>(selectedActionProperties);
        
        Notification.show("Association saved", Notification.Type.TRAY_NOTIFICATION);
    }
    
    private void saveProjectActionPoroperty(StkActionProperties actionProperty) {
        ProjectsActions pa = projectsActionsService.findByProjectAndAction(selectedProject, selectedAction);
        projectsActionsPropertiesSerice.create(new ProjectsActionsProperties(pa, actionProperty));
    }
    
    private void removeProjectActionProperty(StkActionProperties actionProperty) {
        ProjectsActionsProperties pap = projectsActionsPropertiesSerice.findByProjectAndActionAndProperty(selectedProject, selectedAction, actionProperty);
        if (pap == null) {
            return;
        }
        projectsActionsPropertiesSerice.remove(pap);
    }
   
    private void actionsDDValueChangeListener(Property.ValueChangeEvent event) {
        selectedAction = (StkActions) event.getProperty().getValue();
        buildSelectedActionProperties();
        addDataSourceForProjectActionPropertyAssociation();
    }
    
    private void projActionPropertyAssociationValueChangeListener(Property.ValueChangeEvent event) {
        Set<StkActionProperties> properties = (Set<StkActionProperties>)event.getProperty().getValue();
        List<StkActionProperties> auxProperties = new ArrayList<>(properties);
        List<StkActionProperties> auxSelectedProperties = new ArrayList<>(selectedActionProperties);
        
        StkActionProperties property;
        
        if (auxProperties.isEmpty() && !selectedActionProperties.isEmpty()) {
            selectedActionProperties.clear();
            return;
            
        }
        
        auxProperties.removeAll(selectedActionProperties);
        if (auxProperties.isEmpty()) {
            auxProperties.removeAll(selectedActionProperties);
            if (!auxProperties.isEmpty()) {
                property = auxProperties.get(0);
                selectedActionProperties.remove(property);
            }
        } else {
            property = auxProperties.get(0);
            selectedActionProperties.add(property);
        }
    }
}
