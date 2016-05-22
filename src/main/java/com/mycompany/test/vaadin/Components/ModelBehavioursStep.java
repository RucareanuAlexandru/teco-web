/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.test.vaadin.Components;

import com.mycompany.test.vaadin.Entities.Models;
import com.mycompany.test.vaadin.Facades.BehaviourReasonsFacade;
import com.mycompany.test.vaadin.Facades.BehavioursFacade;
import com.mycompany.test.vaadin.Facades.ProjectsActionsFacade;
import com.mycompany.test.vaadin.Facades.ProjectsFacade;
import com.vaadin.ui.Component;
import org.vaadin.teemu.wizards.WizardStep;

/**
 *
 * @author alex
 */
public class ModelBehavioursStep implements WizardStep {
    
    private final StkActionType actionType;
    
    protected Models model;
    protected ModelBehavioursContent behaviourContent;

    public ModelBehavioursStep(BehaviourReasonsFacade behaviourReasonsService,
            ProjectsActionsFacade projectsActionsService, BehavioursFacade behavioursService,
            ProjectsFacade projectsService, Models model, StkActionType actionType) {
        
        this.actionType = actionType;
        this.model = model;
        
        behaviourContent = new ModelBehavioursContent(behaviourReasonsService, projectsActionsService,
                behavioursService, projectsService, model, actionType);
    }
    
    @Override
    public Component getContent() {
        return behaviourContent;
    }

    @Override
    public String getCaption() {
        switch(actionType) {
            case COMMAND:
                return "Commands";
            case EVENT:
                return "Events";
            case SPECIAL:
                return "Specials";
        }
        return "caption";
    }

    @Override
    public boolean onAdvance() {
        return true;
    }

    @Override
    public boolean onBack() {
        return true;
    }
    
}
