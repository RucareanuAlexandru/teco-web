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
import com.vaadin.ui.Notification;
import com.vaadin.ui.Window;
import org.vaadin.teemu.wizards.Wizard;

/**
 *
 * @author alex
 */
public class WizardModelPopup extends Window {

    private ModelsFacade modelService;
    private OsFacade osService;
    private TacsFacade tacsService;
    private PhoneTypesFacade phoneTypesService;
    private PhoneTypePropertiesFacade phoneTypePropertiesService;
    private ModelPropertiesFacade modelPropertiesService;
    private BehaviourReasonsFacade behaviourReasonsService;
    private ProjectsActionsFacade projectsActionsService;
    private BehavioursFacade behavioursService;
    private ProjectsFacade projectsService;
    
    private Models model;
    private Wizard wizard;

    public WizardModelPopup(ModelsFacade modelService, OsFacade osService, TacsFacade tacsService, PhoneTypesFacade phoneTypesService, PhoneTypePropertiesFacade phoneTypePropertiesService, ModelPropertiesFacade modelPropertiesService, BehaviourReasonsFacade behaviourReasonsService, ProjectsActionsFacade projectsActionsService, BehavioursFacade behavioursService, ProjectsFacade projectsService, Models model) {
        this.modelService = modelService;
        this.osService = osService;
        this.tacsService = tacsService;
        this.phoneTypesService = phoneTypesService;
        this.phoneTypePropertiesService = phoneTypePropertiesService;
        this.modelPropertiesService = modelPropertiesService;
        this.behaviourReasonsService = behaviourReasonsService;
        this.projectsActionsService = projectsActionsService;
        this.behavioursService = behavioursService;
        this.projectsService = projectsService;
        this.model = model;
        
        buildLayout();
        configWindow();
    }

    public void buildLayout() {
        wizard = new Wizard();
        wizard.addStep(new ModelBasicStep(model, modelService, osService)); // the model will be saved and put in "model" variable
        wizard.addStep(new ModelTacsStep(tacsService, model));
        wizard.addStep(new ModelTypesStep(phoneTypesService, phoneTypePropertiesService,
                modelPropertiesService, model));
        wizard.addStep(new ModelBehavioursStep(behaviourReasonsService, projectsActionsService,
                behavioursService, projectsService, model, StkActionType.COMMAND));
        wizard.addStep(new ModelBehavioursStep(behaviourReasonsService, projectsActionsService,
                behavioursService, projectsService, model, StkActionType.EVENT));
        wizard.addStep(new ModelBehavioursStep(behaviourReasonsService, projectsActionsService,
                behavioursService, projectsService, model, StkActionType.SPECIAL));
        
        wizard.getNextButton().addClickListener(e -> {
            if (model.getModelId() == null) {
                Notification.show("Save the model before advancing", Notification.Type.TRAY_NOTIFICATION);
                wizard.back();
                return;
            }
        });
        
        wizard.getCancelButton().addClickListener(e -> {
            if (model.getModelId() != null) {
                modelService.remove(model);
            }
            model = null;
            close();
        });
        
        wizard.getFinishButton().addClickListener(e -> {
            close();
        });
        
        setContent(wizard);
    }

    private void configWindow() {
        center();
        setModal(true);
        setDraggable(true);
        setClosable(false);
        setResizable(false);
        
        setWidth("900px");
        setHeight("530px");
    }
        
}
