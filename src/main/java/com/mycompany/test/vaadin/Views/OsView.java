/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.test.vaadin.Views;

import com.mycompany.test.vaadin.Components.OsForm;
import com.mycompany.test.vaadin.Entities.Os;
import com.mycompany.test.vaadin.Facades.OsFacade;
import com.mycompany.test.vaadin.UI.TecoMainUi;
import com.vaadin.cdi.CDIView;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.util.BeanItem;
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
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import org.vaadin.maddon.button.MButton;
import org.vaadin.maddon.layouts.MHorizontalLayout;
import org.vaadin.maddon.layouts.MVerticalLayout;

/**
 *
 * @author alex
 */
@CDIView("os")
public class OsView extends CustomComponent implements View {

    public static final String NAME = "os";
    
    @Inject
    OsFacade osService;
    
    private BeanItemContainer<Os> osContainer;
    private final Grid osGrid = new Grid();
    private Label heading;
    private OsForm osForm;
    private MButton addOsButton;
    private MButton deleteOsButton;
    private Os osToAdd;
    private FieldGroup binder;
    private MVerticalLayout osFormLayout;
    private boolean osFormLayoutVisibility = false;
    private MButton toggleOsFormButton;
    
    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        TecoMainUi main = (TecoMainUi)getUI();
        main.getAppLayout().getComponent(0).setVisible(true);
    }
    
    @PostConstruct
    public void init() {
        buildContainer();
        osGrid.setContainerDataSource(osContainer);
        buildGrid();
        buildLayout();
    }
    
    private void buildLayout() {
        heading = new Label("Os");
        heading.setStyleName(ValoTheme.LABEL_H2);
        
        addOsButton = new MButton(FontAwesome.SAVE);
        addOsButton.addClickListener(this::saveOsListener);
        
        toggleOsFormButton = new MButton(FontAwesome.PLUS);
        toggleOsFormButton.addClickListener(this::toggleOsFormListener);
        
        deleteOsButton = new MButton(FontAwesome.REMOVE);
        deleteOsButton.addClickListener(this::deleteOsListener);
        
        buildOsForm();        
        
        osFormLayout = new MVerticalLayout(osForm, addOsButton);
        osFormLayout.setVisible(osFormLayoutVisibility);
        
        VerticalLayout leftSide = new MVerticalLayout(deleteOsButton, toggleOsFormButton, osFormLayout);

        HorizontalLayout content = new MHorizontalLayout(osGrid, leftSide);

        VerticalLayout vl = new VerticalLayout(heading, content);
        setCompositionRoot(vl);
    }
    
    private void buildContainer() {
        List<Os> oses = osService.findAll();
        osContainer = new BeanItemContainer<>(Os.class, oses);
        osContainer.sort(new String[]{"os"}, new boolean[] {true});
    }
    
    private void buildGrid() {
        osGrid.removeColumn("modelsList");
        osGrid.removeColumn("id");
        
        osGrid.setHeightByRows(10);
        osGrid.setHeightMode(HeightMode.ROW);
        
        osGrid.setEditorEnabled(true);
        
        Field<?> field = osGrid.getColumn("os").getEditorField();
        field.addValidator(new BeanValidator(Os.class, "os"));
        
        field = osGrid.getColumn("version").getEditorField();
        field.addValidator(new BeanValidator(Os.class, "version"));
        
        osGrid.getEditorFieldGroup().addCommitHandler(new FieldGroup.CommitHandler() {
            @Override
            public void preCommit(FieldGroup.CommitEvent commitEvent) throws FieldGroup.CommitException {
                
            }

            @Override
            public void postCommit(FieldGroup.CommitEvent commitEvent) throws FieldGroup.CommitException {
                Os o = (Os)osGrid.getEditedItemId();
                osService.edit(o);
            }
        });
    }
    
    private void buildOsForm() {
        osForm = new OsForm();
        osToAdd = new Os();
        binder = new FieldGroup();
        BeanItem<Os> item = new BeanItem<>(osToAdd);
        binder.setItemDataSource(item);
        
        binder.bindMemberFields(osForm);
    }
    
    private void saveOsListener(Button.ClickEvent event) {
        osForm.enabelValidation();
        try {
            binder.commit();
        } catch (FieldGroup.CommitException ex) {
            Logger.getLogger(OsView.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }

        osService.create(osToAdd);
        
        osToAdd = osService.findOsByOsAndVersion(osToAdd.getOs(), osToAdd.getVersion());
        
        osContainer.addItem(osToAdd);
        osGrid.setContainerDataSource(osContainer);
    }
    
    private void toggleOsFormListener(Button.ClickEvent event) {
        osFormLayout.setVisible(!osFormLayoutVisibility);
        osFormLayoutVisibility = !osFormLayoutVisibility;
        FontAwesome icon = osFormLayoutVisibility? FontAwesome.MINUS: FontAwesome.PLUS;
        toggleOsFormButton.setIcon(icon);
    }
    
    private void deleteOsListener(Button.ClickEvent event) {
        Os os = (Os)osGrid.getSelectedRow();
        if (os != null) {
            osService.remove(os);
            osContainer.removeItem(os);
            osGrid.setContainerDataSource(osContainer);
        }
    }
}
