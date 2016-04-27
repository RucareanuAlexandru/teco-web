/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.test.vaadin.Components;

import com.mycompany.test.vaadin.Entities.Behaviours;
import com.mycompany.test.vaadin.Entities.Models;
import com.mycompany.test.vaadin.Entities.Projects;
import com.mycompany.test.vaadin.Entities.ProjectsActions;
import com.mycompany.test.vaadin.Entities.ProjectsActionsProperties;
import com.mycompany.test.vaadin.Facades.BehaviourReasonsFacade;
import com.mycompany.test.vaadin.Facades.BehavioursFacade;
import com.mycompany.test.vaadin.Facades.ProjectsActionsFacade;
import com.mycompany.test.vaadin.Facades.ProjectsFacade;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author alex
 */
public class ModelBehavioursContent extends VerticalLayout {

    private final BehaviourReasonsFacade behaviourReasonsService;
    private final ProjectsActionsFacade projectsActionsService;
    private final BehavioursFacade behavioursService;
    private final ProjectsFacade projectsService;
    
    private Models model;
    private BehavioursGrid behavioursGrid;
    private Map<String, List<Behaviours>> behavioursMap = new HashMap<>();
    private StkActionType actionType;
    private List<ProjectsActions> actionsList;
    private Projects selectedProject;
    private List<Projects> projectsList;
    private NativeSelect projectsSelect;
    private Button saveBehavioursButton;

    public ModelBehavioursContent(BehaviourReasonsFacade behaviourReasonsService, ProjectsActionsFacade projectsActionsService, BehavioursFacade behavioursService, ProjectsFacade projectsService, Models model, StkActionType actionType) {
        this.behaviourReasonsService = behaviourReasonsService;
        this.projectsActionsService = projectsActionsService;
        this.behavioursService = behavioursService;
        this.projectsService = projectsService;
        this.model = model;
        this.actionType = actionType;
        
        buildLayout();
    }
    
    private void buildLayout() {
        buildProjectsSelect();
        
        saveBehavioursButton = new Button(FontAwesome.SAVE);
        saveBehavioursButton.addClickListener(this::saveBehavioursListener);
        
        buildBehavioursContent();
    }
    
    private void buildBehavioursContent() {
        buildBehavioursGrid();
        
        removeAllComponents();
        
        addComponent(projectsSelect);
        addComponent(behavioursGrid);
        addComponent(saveBehavioursButton);
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
                
                Notification.show("Behaviour created", Notification.Type.TRAY_NOTIFICATION);
            } else {
                behavioursService.edit(b);
                
                Notification.show("Behaviour edited", Notification.Type.TRAY_NOTIFICATION);
            }
        });
    }
    
    private void buildProjectsSelect() {
        projectsList = projectsService.findAll();
        selectedProject = projectsList.get(0);
        
        projectsSelect = new NativeSelect("Project", projectsList);
        projectsSelect.select(selectedProject);
        
        projectsSelect.addValueChangeListener(event -> {
            selectedProject = (Projects) event.getProperty().getValue();
            buildBehavioursContent();
        });
    }
}
