/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.test.vaadin.Components;

import com.mycompany.test.vaadin.Entities.Projects;
import com.vaadin.data.validator.BeanValidator;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import org.vaadin.maddon.button.MButton;
import org.vaadin.maddon.fields.MTextField;

/**
 *
 * @author alex
 */
public class AddProjectComponent extends HorizontalLayout{
    
    private Label addProjectLabel;
    private TextField projectName;
    private MButton addButton;

    public AddProjectComponent() {
        setSpacing(true);
        setVisible(false);

        addProjectLabel = new Label("Project name: ");
        addButton = new MButton(FontAwesome.SAVE);
        projectName = new MTextField();
        projectName.addValidator(new BeanValidator(Projects.class, "project"));
        projectName.setValidationVisible(false);
        
        addComponent(addProjectLabel);
        addComponent(projectName);
        addComponent(addButton);
    }
    
    public void enableValidation() {
        projectName.setValidationVisible(true);
    }

    public void addClickListener(Button.ClickListener clickListener) {
        addButton.addClickListener(clickListener);
    }

    public Label getAddProjectLabel() {
        return addProjectLabel;
    }

    public void setAddProjectLabel(Label addProjectLabel) {
        this.addProjectLabel = addProjectLabel;
    }

    public TextField getProjectName() {
        return projectName;
    }

    public void setProjectName(TextField projectName) {
        this.projectName = projectName;
    }

    public MButton getAddButton() {
        return addButton;
    }

    public void setAddButton(MButton addButton) {
        this.addButton = addButton;
    }
    
    
}
