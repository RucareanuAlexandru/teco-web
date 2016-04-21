/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.test.vaadin.Components;

import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;
import org.vaadin.maddon.button.MButton;

/**
 *
 * @author alex
 */
public abstract class AbstractVerticalForm extends VerticalLayout {
    
    protected MButton saveButton = new MButton(FontAwesome.SAVE);
    protected MButton deleteButton = new MButton(FontAwesome.REMOVE);
    protected final MButton clearSelectionButton = new MButton("Clear selection");
    protected final HorizontalLayout saveRemoceHl;

    public AbstractVerticalForm() {
        setMargin(true);
        setSpacing(true);
        
        saveRemoceHl = new HorizontalLayout(saveButton, deleteButton);
        saveRemoceHl.setSpacing(true);
    }

    public void hideDeleteAndClearSelectionButtons() {
        deleteButton.setVisible(false);
        clearSelectionButton.setVisible(false);
    }
    
    public void showDeleteAndClearSelectionButtons() {
        deleteButton.setVisible(true);
        clearSelectionButton.setVisible(true);
    }
    
    public void addSaveListener(Button.ClickListener saveListener) {
        saveButton.addClickListener(saveListener);
    }
    
    public void addDeleteListener(Button.ClickListener deleteListener) {
        deleteButton.addClickListener(deleteListener);
    }     
    
    public void addClearSelectionListener(Button.ClickListener clearListener) {
        clearSelectionButton.addClickListener(clearListener);
    } 
    
    public void toggleDeleteButton() {
        deleteButton.setVisible(!deleteButton.isVisible());
    }
    
    public void toggleClearSelectionButton() {
        clearSelectionButton.setVisible(!clearSelectionButton.isVisible());
    }
}
