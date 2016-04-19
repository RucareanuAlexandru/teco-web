package com.mycompany.test.vaadin.Entities;

import com.vaadin.ui.Component;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.TextField;
import org.vaadin.maddon.fields.MTextField;
import org.vaadin.maddon.form.AbstractForm;
import org.vaadin.maddon.layouts.MVerticalLayout;

public class ProjectsForm extends AbstractForm<Projects> {
    
    private TextField id = new MTextField("id");
    private TextField project = new MTextField("project");
    private TextField projectsActionsList = new MTextField("projectsActionsList");
    
    @Override
    protected Component createContent() {
        return new MVerticalLayout(
                new FormLayout(
                        id,
                        project,
                        projectsActionsList
                ),
                getToolbar()
        );
    }
    
}
