/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.test.vaadin.Components;

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
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import javax.enterprise.inject.Model;

/**
 *
 * @author alex
 */
@Model
public class ModelDetailsPopup extends Window {
    
    private final OsFacade osService;
    private final TacsFacade tacsService;
    private final ModelPropertiesFacade modelPropertiesService;
    private final PhoneTypesFacade phoneTypesService;
    private final PhoneTypePropertiesFacade phoneTypePropertiesService;
    private final ProjectsActionsFacade projectsActionsService;
    private final BehavioursFacade behavioursService;
    private final BehaviourReasonsFacade behaviourReasonsService;
    private final ProjectsFacade projectsService;
    private final ModelsFacade modelsService;
    
    private Models model;
    private VerticalLayout windowMenue;
    private final Panel windowContent = new Panel();
    private HorizontalLayout windowLayout = new HorizontalLayout();
    private VerticalLayout basicContent;
    private HorizontalLayout tacsContent;
    private HorizontalLayout typesContent;
    private StkActionType actionType;
    private VerticalLayout behavioursContent;
    
    public void buildFromModel(Models model) {
        this.model = model;
        
        setCaption(model.toString());
        buildBasicContent();
        buildTacsContent();
        buildTypesContent();
        buildLayout();
    }


    public ModelDetailsPopup(OsFacade osService, TacsFacade tacsService,
            ModelPropertiesFacade modelPropertiesService, PhoneTypesFacade phoneTypesService,
            PhoneTypePropertiesFacade phoneTypePropertiesService, ProjectsActionsFacade projectsActionsService, 
            BehavioursFacade behavioursService, BehaviourReasonsFacade behaviourReasonsService, 
            ProjectsFacade projectsService, ModelsFacade modelsService) {
        this.osService = osService;
        this.tacsService = tacsService;
        this.modelPropertiesService = modelPropertiesService;
        this.phoneTypesService = phoneTypesService;
        this.phoneTypePropertiesService = phoneTypePropertiesService;
        this.projectsActionsService = projectsActionsService;
        this.behavioursService = behavioursService;
        this.behaviourReasonsService = behaviourReasonsService;
        this.projectsService = projectsService;
        this.modelsService = modelsService;
        
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
        specialsButton.addClickListener(e -> {
            buildSpecialsBehaviourContent();
        });
        
        windowMenue = new VerticalLayout(basicButton, tacsButton, typesButton,
                commandsButton, eventsButton, specialsButton);
        
        for (Component c: windowMenue) {
            c.setStyleName("width-100");
        }
    }
    
    private void buildBasicContent() {
        basicContent = new ModelBasicDetailsContent(model, modelsService, osService);
    }

    private void setBasicContent() {
        windowContent.setContent(basicContent);
    }

    private void buildTacsContent() {
        tacsContent = new ModelTacsContent(tacsService, model);
    }
    
    private void setTacsContent() {
        windowContent.setContent(tacsContent);
    }
    
    private void buildTypesContent() {       
        typesContent = new ModelTypesContent(phoneTypesService, phoneTypePropertiesService,
                modelPropertiesService, model);
    }
    
    private void setTypesContent() {
        windowContent.setContent(typesContent);
    }
    
    private void buildCommandsBehaviourContent() {
        actionType = StkActionType.COMMAND;
        buildBehavioursContent();
    }
    
    private void buildEventsBehaviourContent() {
        actionType = StkActionType.EVENT;
        buildBehavioursContent();
    }
    
    private void buildSpecialsBehaviourContent() {
        actionType = StkActionType.SPECIAL;
        buildBehavioursContent();
    }
    
    private void buildBehavioursContent() {
        behavioursContent = new ModelBehavioursContent(behaviourReasonsService, projectsActionsService,
                behavioursService, projectsService, model, actionType);
        windowContent.setContent(behavioursContent);
    }
       
}
