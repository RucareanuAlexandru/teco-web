/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.test.vaadin.Components;

import com.mycompany.test.vaadin.Entities.Tacs;
import com.vaadin.data.validator.BeanValidator;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import org.vaadin.maddon.button.MButton;

/**
 *
 * @author alex
 */
public class TacForm extends AbstractVerticalForm {
    
    private TextField tac = new TextField("Tac");
    private TextField snr = new TextField("snr");
    private TextField terminalProfile = new TextField("Terminal profile");

    public TacForm() {       
        tac.setNullRepresentation("");
        tac.addValidator(new BeanValidator(Tacs.class, "tac"));
        
        snr.setNullRepresentation("");
        snr.addValidator(new BeanValidator(Tacs.class, "snr"));
        
        terminalProfile.setNullRepresentation("");
        terminalProfile.addValidator(new BeanValidator(Tacs.class, "terminalProfile"));
        
        hideDeleteAndClearSelectionButtons();
        
        disableValidation();
        addComponent(clearSelectionButton);
        addComponent(tac);
        addComponent(snr);
        addComponent(terminalProfile);
        addComponent(saveRemoceHl);
    }
 
    public void enableValidation() {
        tac.setValidationVisible(true);
        snr.setValidationVisible(true);
        terminalProfile.setValidationVisible(true);
    }
    
    public void disableValidation() {
        tac.setValidationVisible(false);
        snr.setValidationVisible(false);
        terminalProfile.setValidationVisible(false);
    }    
}
