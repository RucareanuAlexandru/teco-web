/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.test.vaadin.Components;

import com.mycompany.test.vaadin.Entities.BehaviourReasons;
import com.mycompany.test.vaadin.Entities.Behaviours;
import com.mycompany.test.vaadin.Entities.ModelProperties;
import com.mycompany.test.vaadin.Entities.Models;
import com.mycompany.test.vaadin.Entities.PhoneTypeProperties;
import com.mycompany.test.vaadin.Entities.PhoneTypes;
import com.mycompany.test.vaadin.Entities.Projects;
import com.mycompany.test.vaadin.Entities.ProjectsActions;
import com.mycompany.test.vaadin.Entities.ProjectsActionsProperties;
import com.mycompany.test.vaadin.Entities.Tacs;
import com.mycompany.test.vaadin.Facades.BehaviourReasonsFacade;
import com.mycompany.test.vaadin.Facades.BehavioursFacade;
import com.mycompany.test.vaadin.Facades.ModelPropertiesFacade;
import com.mycompany.test.vaadin.Facades.OsFacade;
import com.mycompany.test.vaadin.Facades.PhoneTypePropertiesFacade;
import com.mycompany.test.vaadin.Facades.PhoneTypesFacade;
import com.mycompany.test.vaadin.Facades.ProjectsActionsFacade;
import com.mycompany.test.vaadin.Facades.ProjectsFacade;
import com.mycompany.test.vaadin.Facades.TacsFacade;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.data.util.ObjectProperty;
import com.vaadin.event.SelectionEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
    private PhoneTypePropertiesFacade phoneTypePropertiesService;
    private ProjectsActionsFacade projectsActionsService;
    private BehavioursFacade behavioursService;
    private BehaviourReasonsFacade behaviourReasonsService;
    private ProjectsFacade projectsService;
    
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
    private ModelProperties typeToAdd;
    private StkActionType actionType;
    private Projects selectedProject;
    private List<ProjectsActions> actionsList;
    private Map<String, List<Behaviours>> behavioursMap = new HashMap<>();
    private VerticalLayout commandsBehaviourContent;
    private Button saveBehavioursButton;
    private BehavioursGrid behavioursGrid;
    private VerticalLayout eventsBehavioursContent;
    private VerticalLayout specialsBehaviourContent;
    private VerticalLayout behavioursContent;
    private NativeSelect projectsSelect;
    private List<Projects> projectsList;
    
    public void buildFromModel(Models model) {
        this.model = model;
        
        setCaption(model.toString());
        buildBasicForm();
        buildBasicContent();
        buildTacsContent();
        buildTypesContent();
        buildProjectsSelect();
        buildLayout();
    }

    private void buildBasicForm() {
        basicForm.buildOses(osService.findAll());
    }

    public ModelDetailsPopup(OsFacade osService, TacsFacade tacsService,
            ModelPropertiesFacade modelPropertiesService, PhoneTypesFacade phoneTypesService,
            PhoneTypePropertiesFacade phoneTypePropertiesService, ProjectsActionsFacade projectsActionsService, 
            BehavioursFacade behavioursService, BehaviourReasonsFacade behaviourReasonsService, 
            ProjectsFacade projectsService) {
        this.osService = osService;
        this.tacsService = tacsService;
        this.modelPropertiesService = modelPropertiesService;
        this.phoneTypesService = phoneTypesService;
        this.phoneTypePropertiesService = phoneTypePropertiesService;
        this.projectsActionsService = projectsActionsService;
        this.behavioursService = behavioursService;
        this.behaviourReasonsService = behaviourReasonsService;
        this.projectsService = projectsService;
        
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
        
        Button commandsButton = new Button("Commands");
        commandsButton.addClickListener((e) -> {
            buildCommandsBehaviourContent();
        });
        
        Button eventsButton = new Button("Events");
        eventsButton.addClickListener((e) -> {
            buildEventsBehaviourContent();
        });
        
        Button specialsButton = new Button("Specials");
        specialsButton.addClickListener((e) -> {
            buildSpecialsBehaviourContent();
        });
        
        windowMenue = new VerticalLayout(basicButton, tacsButton, typesButton,
                commandsButton, eventsButton, specialsButton);
        
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
    
    private void buildTypesContent() {
        buildTypeForm();
        buildTypesContainer();
        buildTypesGrid();
        typeForm.buildCategories(phoneTypesService.findAll());
        
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
    
    private void buildTypeForm() {
        bindNewType();
        typeForm.addSaveListener(this::saveTypeListener);
        typeForm.addDeleteListener(this::deleteTypeListener);
        typeForm.addClearSelectionListener(this::clearTypeSelectionListener);
        typeForm.addCategorySelectListener(this::typesCategoryPropertyChangeListener);
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
    
    private void buildCommandsBehaviourContent() {
        actionType = StkActionType.COMMAND;
        selectedProject = projectsList.get(0);
        projectsSelect.select(selectedProject);
        buildBehavioursContent();
    }
    
    private void buildEventsBehaviourContent() {
        actionType = StkActionType.EVENT;
        selectedProject = projectsList.get(0);
        projectsSelect.select(selectedProject);
        buildBehavioursContent();
    }
    
    private void buildSpecialsBehaviourContent() {
        actionType = StkActionType.SPECIAL;
        selectedProject = projectsList.get(0);
        projectsSelect.select(selectedProject);
        buildBehavioursContent();
    }
    
    private void buildBehavioursContent() {
        buildBehavioursGrid();
        
        saveBehavioursButton = new Button(FontAwesome.SAVE);
        saveBehavioursButton.addClickListener(this::saveBehavioursListener);
        
        behavioursContent = new VerticalLayout(projectsSelect, behavioursGrid, saveBehavioursButton);
        windowContent.setContent(behavioursContent);
    }

    private void buildBehavioursGrid() {
        makeActionStructure();
        behavioursGrid = new BehavioursGrid(behavioursMap, behaviourReasonsService.findAll());
    }
    
    private void makeActionStructure() {
        actionsList = projectsActionsService.findByProjectAndActionType(selectedProject, actionType.name());
        behavioursMap.clear();
        
        for (ProjectsActions action: actionsList) {
            List<Behaviours> behaviours = new ArrayList<>();
            for (ProjectsActionsProperties pap: action.getProjectsActionsPropertiesList()) {
                Behaviours b = behavioursService.findByModelAndProjectActionProperty(model, pap);
                
                if (b == null) {
                    b = new Behaviours();
                    b.setModel(model);
                    b.setProjectActionProperty(pap);
                    b.setPropertyValue("");
                    b.setReason(null);
                }
                
                behaviours.add(b);
            }
            if (!behaviours.isEmpty()) {
                behavioursMap.put(action.getAction().getName(), behaviours);
            }
        }
    }
    
    private void saveBehavioursListener(Button.ClickEvent event) {
        List<Behaviours> behaviours = new ArrayList<>();
        Set<String> keys = behavioursMap.keySet();
        
        keys.stream().forEach(key -> {
            List<Behaviours> list = behavioursMap.get(key);
            behaviours.addAll(list);
        });
        
        behaviours.stream().forEach(b -> {
            if (b.getId() == null) {
                behavioursService.create(b);
            } else {
                behavioursService.edit(b);
            }
        });
    }
    
    private void buildProjectsSelect() {
        projectsList = projectsService.findAll();
        selectedProject = projectsList.get(0);
        
        projectsSelect = new NativeSelect("Project", projectsList);
        projectsSelect.select(selectedProject);
        
        projectsSelect.addValueChangeListener(new Property.ValueChangeListener() {
            @Override
            public void valueChange(Property.ValueChangeEvent event) {
                selectedProject = (Projects) event.getProperty().getValue();
                buildBehavioursContent();
            }
        });
    }
       
}
