/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.test.vaadin.Components;

import com.mycompany.test.vaadin.Entities.Models;
import com.mycompany.test.vaadin.Facades.TacsFacade;
import com.vaadin.ui.Component;
import org.vaadin.teemu.wizards.WizardStep;

/**
 *
 * @author alex
 */
public class ModelTacsStep implements WizardStep {

    private TacsFacade tacsService;
    
    private Models model;
    private ModelTacsContent tacsContent;

    public ModelTacsStep(TacsFacade tacsService, Models model) {
        this.tacsService = tacsService;
        this.model = model;
        
        tacsContent = new ModelTacsContent(tacsService, model);
    } 
    
    @Override
    public String getCaption() {
        return "Tacs";
    }

    @Override
    public Component getContent() {
        
        return tacsContent;
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
