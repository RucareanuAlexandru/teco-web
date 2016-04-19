/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.test.vaadin.Views;

import com.mycompany.test.vaadin.Components.AddProjectComponent;
import com.mycompany.test.vaadin.Entities.Projects;
import com.mycompany.test.vaadin.Facades.ProjectsFacade;
import com.mycompany.test.vaadin.UI.TecoMainUi;
import com.vaadin.cdi.CDIView;
import com.vaadin.data.Validator;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.validator.BeanValidator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.grid.HeightMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Field;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import org.vaadin.maddon.button.MButton;
import org.vaadin.maddon.layouts.MHorizontalLayout;
import org.vaadin.maddon.layouts.MVerticalLayout;

/**
 *
 * @author alex
 */
@CDIView("projects")
public class ProjectsView extends CustomComponent implements View{

    @Inject
    ProjectsFacade projectsService;
    
    public static final String NAME = "projects";
    private final Grid projectsGrid = new Grid();
    private BeanItemContainer<Projects> projectsContainer;
    private AddProjectComponent apc;
    private boolean addProjectFormVisibility = true;
    private Label heading;
    private MButton add;
    private MButton delete;

    
    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        TecoMainUi main = (TecoMainUi)getUI();
        main.getAppLayout().getComponent(0).setVisible(true);
    }
    
    @PostConstruct
    private void init() {
        buildContainer();
        projectsGrid.setContainerDataSource(projectsContainer);
        styleGrid();
        buildLayout();
    }

    private void buildLayout() {
        heading = new Label("Projects");
        heading.setStyleName(ValoTheme.LABEL_H1);
        
        add = new MButton(FontAwesome.PLUS);
        add.addClickListener(this::toggleAddProjectForm);
        
        delete = new MButton(FontAwesome.REMOVE);
        delete.addClickListener(this::deleteProjectListener);
        delete.setDescription("Delete selected Project");
        
        apc = new AddProjectComponent();
        apc.addClickListener(this::projectSaveListener);
        
        VerticalLayout addProjectLayout = new MVerticalLayout(add, apc, delete);
        
        HorizontalLayout hl = new MHorizontalLayout(projectsGrid, addProjectLayout);
        hl.setSpacing(true);
        VerticalLayout vl = new MVerticalLayout(heading, hl);
        vl.setSpacing(true);
        
        setCompositionRoot(vl);
    }
    
    private void buildContainer() {
        List<Projects> projects = projectsService.findAll();
        projectsContainer = new BeanItemContainer<>(Projects.class, projects);
        projectsContainer.sort(new String[] {"project"}, new boolean[] {true});
    }
 
    private void styleGrid() {
        projectsGrid.setHeightByRows(10);
        projectsGrid.setHeightMode(HeightMode.ROW);
        projectsGrid.setEditorEnabled(true);
        projectsGrid.setWidth(270, Unit.PIXELS);
        projectsGrid.removeColumn("projectsActionsList");
        projectsGrid.removeColumn("id");
        
        projectsGrid.getColumn("project").setSortable(true);
        projectsGrid.getColumn("project").setWidth(270);
        
        Field<?> editorField = projectsGrid.getColumn("project").getEditorField();
        editorField.addValidator(new BeanValidator(Projects.class, "project"));
        
        projectsGrid.getEditorFieldGroup().addCommitHandler(new FieldGroup.CommitHandler() {
            @Override
            public void preCommit(FieldGroup.CommitEvent commitEvent) throws FieldGroup.CommitException {
                
            }

            @Override
            public void postCommit(FieldGroup.CommitEvent commitEvent) throws FieldGroup.CommitException {
                Projects p = (Projects)projectsGrid.getEditedItemId();
                projectsService.edit(p);
            }
        });
    }
    
    public void projectSaveListener(Button.ClickEvent event) {
        apc.enableValidation();
        try{
            apc.getProjectName().validate();
        } catch(Validator.InvalidValueException exception) {
            return;
        }
        Projects p = new Projects();
        p.setProject(apc.getProjectName().getValue());
        projectsService.create(p);

        p = projectsService.findProjectByProjectName(p.getProject());

        projectsContainer.addItem(p);
        projectsContainer.sort(new String[] {"project"}, new boolean[] {true});
        projectsGrid.setContainerDataSource(projectsContainer);

        apc.setVisible(false);
        Notification.show("Project added successfully", Notification.Type.TRAY_NOTIFICATION);
    }
    
    public void toggleAddProjectForm(Button.ClickEvent event) {
        apc.setVisible(addProjectFormVisibility);
        addProjectFormVisibility = !addProjectFormVisibility;
        FontAwesome buttonIcon = addProjectFormVisibility? FontAwesome.PLUS:FontAwesome.MINUS;
        event.getButton().setIcon(buttonIcon);
    }
    
    public void deleteProjectListener(Button.ClickEvent event) {
        Projects p = (Projects)projectsGrid.getSelectedRow();
        if (p != null) {
            projectsService.remove(p);
            projectsContainer.removeItem(p);
            projectsGrid.setContainerDataSource(projectsContainer);
        }
    }
}
