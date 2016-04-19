/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.test.vaadin.Components;

import com.mycompany.test.vaadin.Entities.Models;
import com.mycompany.test.vaadin.Entities.Os;
import com.vaadin.data.Item;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import java.util.List;
import org.vaadin.maddon.layouts.MWindow;

/**
 *
 * @author alex
 */
public class ModelDetailsPopup extends Window {

    private Models model;
    private BasicModelDetailsForm basicForm = new BasicModelDetailsForm();
    private VerticalLayout windowMenue;
    private Panel windowContent = new Panel();
    private HorizontalLayout windowLayout = new HorizontalLayout();
    private FieldGroup modelBinder = new FieldGroup();
    private VerticalLayout basicContent;
    
    
    public ModelDetailsPopup(Models model, List<Os> oses) {
        this.model = model;
        
        configWindow();
        basicForm.buildOses(oses);
        buildBasicContent();
        buildLayout();
    }
    
    private void configWindow() {
        center();
        setModal(true);
        setWidth("1120px");
        setHeight("500px");
        setCaption(model.toString());
    }
    
    private void buildLayout() {
        buildMenue();
        setBasicContent();
        windowLayout = new HorizontalLayout(windowMenue, windowContent);
        windowLayout.setMargin(true);
        windowLayout.setSizeFull();
        
        windowLayout.setExpandRatio(windowMenue, 1.5f);
        windowLayout.setExpandRatio(windowContent, 6);
        
        setContent(windowLayout);
    }
    
    private void buildMenue() {
        Button basicButton = new Button("Basics");
        basicButton.addClickListener((Button.ClickEvent event) -> {
            setBasicContent();
        });
        
        Button tacsButton = new Button("Tacs");
        tacsButton.addClickListener((Button.ClickEvent event) -> {
            setTacsContent();
        });
        
        Button typesButton = new Button("Types");
        typesButton.addClickListener((Button.ClickEvent event) -> {
            setTypesContent();
        });
        
        windowMenue = new VerticalLayout(basicButton, tacsButton, typesButton);
        
        for (Component c: windowMenue) {
            c.setStyleName("width-100");
        }
    }
    
    private void buildBasicContent() {
        Item modelItem = new BeanItem(model);
        modelBinder.setItemDataSource(modelItem);
        modelBinder.bindMemberFields(basicForm);
        
        basicContent = new VerticalLayout(basicForm);
    }

    private void setBasicContent() {
        windowContent.setContent(basicContent);
//        windowContent.removeAllComponents();
//        windowContent.addComponent(basicContent);
    }

    private void setTacsContent() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void setTypesContent() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
}
