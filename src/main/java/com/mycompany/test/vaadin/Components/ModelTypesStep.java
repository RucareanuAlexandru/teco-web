/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.test.vaadin.Components;

import com.mycompany.test.vaadin.Entities.Models;
import com.mycompany.test.vaadin.Facades.ModelPropertiesFacade;
import com.mycompany.test.vaadin.Facades.PhoneTypePropertiesFacade;
import com.mycompany.test.vaadin.Facades.PhoneTypesFacade;
import com.vaadin.ui.Component;
import org.vaadin.teemu.wizards.WizardStep;

/**
 *
 * @author alex
 */
public class ModelTypesStep implements WizardStep {

    private PhoneTypesFacade phoneTypesService;
    private PhoneTypePropertiesFacade phoneTypePropertiesService;
    private ModelPropertiesFacade modelPropertiesService;
    private ModelTypesContent modelTypesContent;
    
    private Models model;

    public ModelTypesStep(PhoneTypesFacade phoneTypesService, PhoneTypePropertiesFacade phoneTypePropertiesService, ModelPropertiesFacade modelPropertiesService, Models model) {
        this.phoneTypesService = phoneTypesService;
        this.phoneTypePropertiesService = phoneTypePropertiesService;
        this.modelPropertiesService = modelPropertiesService;
        this.model = model;
        
        modelTypesContent = new ModelTypesContent(phoneTypesService, phoneTypePropertiesService,
                modelPropertiesService, model);
    }
    
    @Override
    public String getCaption() {
        return "Types";
    }

    @Override
    public Component getContent() {
        return modelTypesContent;
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
