/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.test.vaadin.Components;

import com.mycompany.test.vaadin.Entities.Models;
import com.mycompany.test.vaadin.Facades.ModelsFacade;
import com.mycompany.test.vaadin.Facades.OsFacade;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.Component;
import com.vaadin.ui.Notification;
import org.vaadin.teemu.wizards.WizardStep;

/**
 *
 * @author alex
 */
public class ModelBasicStep implements WizardStep {

    private Models model;
    private ModelBasicDetailsContent basicContent;
    private ModelsFacade modelService;
    private OsFacade osService;

    public ModelBasicStep(Models model, ModelsFacade modelService, OsFacade osService) {
        this.model = model;
        this.modelService = modelService;
        this.osService = osService;
    }
    
    @Override
    public String getCaption() {
        return "Basic details";
    }

    @Override
    public Component getContent() {
        basicContent = new ModelBasicDetailsContent(model, modelService, osService);
        return basicContent;
    }

    @Override
    public boolean onAdvance() {
        return true;
    }

    @Override
    public boolean onBack() {
        return false;
    }
    
}
