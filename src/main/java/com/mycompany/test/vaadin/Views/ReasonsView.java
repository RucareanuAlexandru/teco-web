/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.test.vaadin.Views;

import com.mycompany.test.vaadin.Components.ReasonsForm;
import com.mycompany.test.vaadin.Entities.BehaviourReasons;
import com.mycompany.test.vaadin.Facades.BehaviourReasonsFacade;
import com.mycompany.test.vaadin.UI.TecoMainUi;
import com.vaadin.cdi.CDIView;
import com.vaadin.data.Item;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.SelectionEvent;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import org.vaadin.maddon.label.Header;

/**
 *
 * @author alex
 */

@CDIView("reasons")
public class ReasonsView extends CustomComponent implements View {

    public static final String NAME = "reasons";
    
    @Inject
    private BehaviourReasonsFacade reasonsService;
    
    
    private TecoMainUi main;
    private List<BehaviourReasons> reasons;
    private BeanItemContainer<BehaviourReasons> reasonsContaner;
    private Grid reasonsGrid;
    private FieldGroup reasonBinder = new FieldGroup();
    private ReasonsForm reasonsForm = new ReasonsForm();
    private BehaviourReasons reasonToAdd;
    
    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        main = (TecoMainUi)getUI();
        main.getAppLayout().getComponent(0).setVisible(true);
    }
    
    @PostConstruct
    public void init() {
        buildReasonsContainer();
        buildReasonsGrid();
        bindNewReason();
        
        buildLayout();
    }
    
    private void buildLayout() {
        Label header = new Header("Reasons");
        HorizontalLayout hl = new HorizontalLayout(reasonsGrid, reasonsForm);
        VerticalLayout layout = new VerticalLayout(header, hl);
        
        setCompositionRoot(layout);
    }
    
    private void buildReasonsContainer() {
        reasons = reasonsService.findAll();
        reasonsContaner = new BeanItemContainer<>(BehaviourReasons.class, reasons);
    }
    
    private void buildReasonsGrid() {
        reasonsGrid = new Grid();
        reasonsGrid.setContainerDataSource(reasonsContaner);
        reasonsGrid.removeColumn("behavioursList");
        reasonsGrid.removeColumn("id");
        
        reasonsGrid.addSelectionListener(this::reasonGridSelectListener);
    }
    
    private void reasonGridSelectListener(SelectionEvent event) {
        BehaviourReasons r = (BehaviourReasons) reasonsGrid.getSelectedRow();
        if (r == null) {
            return;
        }
        
        Item item = new BeanItem(r);
        reasonBinder.setItemDataSource(item);
        reasonBinder.bindMemberFields(reasonsForm);
        
        reasonsForm.showDeleteAndClearSelectionButtons();
    }
    
    private void bindNewReason() {
        reasonToAdd = new BehaviourReasons();
        Item item = new BeanItem(reasonToAdd);
        reasonBinder.setItemDataSource(item);
        reasonBinder.bindMemberFields(reasonsForm);
        
        reasonsForm.hideDeleteAndClearSelectionButtons();
    }
    
}
