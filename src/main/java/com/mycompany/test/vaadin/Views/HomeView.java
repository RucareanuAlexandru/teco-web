/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.test.vaadin.Views;

import com.mycompany.test.vaadin.Entities.Models;
import com.mycompany.test.vaadin.Facades.ModelsFacade;
import com.mycompany.test.vaadin.UI.TecoMainUi;
import com.vaadin.cdi.CDIView;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import javax.inject.Inject;

/**
 *
 * @author alex
 */
@CDIView("home")
public class HomeView extends CustomComponent implements View{

    public static final String NAME = "home"; 
    
    @Inject
    private ModelsFacade modelService;
    private BeanItemContainer<Models> modelsContainer;
    private Grid modelsGrid;
    
    
    
    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        TecoMainUi main = (TecoMainUi)getUI();
        main.getAppLayout().getComponent(0).setVisible(true);
    }
    
    public HomeView() {
        VerticalLayout p = new VerticalLayout();
        p.setSizeFull();
        Label l = new Label("Home Page");
        p.addComponent(l);
        p.setComponentAlignment(l, Alignment.MIDDLE_CENTER);

        setCompositionRoot(p);
    }
    
    private void buildLayout() {
        
    }
    
    private void buildModelsContainer() {
        modelsContainer = new BeanItemContainer<>(Models.class, modelService.findAll());
    }
    
    private void buildModelsGrid() {
        modelsGrid = new Grid();
        modelsGrid.setContainerDataSource(modelsContainer);
        
    }
    
}
