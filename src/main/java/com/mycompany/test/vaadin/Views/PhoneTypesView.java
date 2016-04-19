/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.test.vaadin.Views;

import com.mycompany.test.vaadin.Components.PhoneTypeForm;
import com.mycompany.test.vaadin.Components.PhoneTypePropertiesForm;
import com.mycompany.test.vaadin.Entities.PhoneTypeProperties;
import com.mycompany.test.vaadin.Entities.PhoneTypes;
import com.mycompany.test.vaadin.Facades.PhoneTypePropertiesFacade;
import com.mycompany.test.vaadin.Services.PhoneTypesService;
import com.mycompany.test.vaadin.UI.TecoMainUi;
import com.vaadin.cdi.CDIView;
import com.vaadin.data.Item;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.SelectionEvent;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.shared.ui.grid.HeightMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import org.vaadin.maddon.layouts.MVerticalLayout;

/**
 *
 * @author alex
 */
@CDIView("phoneTypes")
public class PhoneTypesView extends CustomComponent implements View{
    public static final String NAME = "phoneTypes";
    
    @Inject
    private PhoneTypesService phoneTypesService;
    
    @Inject
    private PhoneTypePropertiesFacade phoneTypePropertiesService;

    private PhoneTypeForm phoneTypeForm;
    private PhoneTypePropertiesForm phoneTypePropertiesForm;
    private Label heading;
    private Grid typesGrid;
    private Grid propsGrid;
    private BeanItemContainer<PhoneTypes> typesContainer;
    private BeanItemContainer<PhoneTypeProperties> propsContainer;
    private FieldGroup ptpBinder;
    private FieldGroup ptBinder;
    private Button savePTPButton;
    private Button savePTButton;
    private Button deletePTPButton;
    private Button deletePTButton;
    private Button clearPTPSelectionButton;
    private Button clearPTSelectionButton;
    private MarginInfo marginLeft = new MarginInfo(false, false, false, true);
    private PhoneTypeProperties ptpToAdd;
    private PhoneTypes ptToAdd;
    private VerticalLayout formPTP;
    
    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        TecoMainUi main = (TecoMainUi)getUI();
        main.getAppLayout().getComponent(0).setVisible(true);
    }
    
    @PostConstruct
    public void init() {
        buildPropsContainer();
        buildTypesContainer();
        buildPropsGrid();
        buildTypesGrid();
        buildPhoneTypePropertiesForm();
        buildPhoneTypeForm();
        bindNewPt();
        bindNewPtp();
        buildLayout();
    }
    
    public void buildLayout() {
        heading = new Label("Phone Types");
        heading.setStyleName(ValoTheme.LABEL_H2);
        
        buildButtons();
        
        HorizontalLayout buttonsPTHl = new HorizontalLayout(savePTButton, deletePTButton);
        buttonsPTHl.setSpacing(true);
        
        HorizontalLayout buttonsPTPHl = new HorizontalLayout(savePTPButton, deletePTPButton);
        buttonsPTPHl.setSpacing(true);
        
        formPTP = new VerticalLayout(clearPTPSelectionButton, phoneTypePropertiesForm, buttonsPTPHl);
        formPTP.setMargin(marginLeft);
        
        VerticalLayout formPT = new VerticalLayout(clearPTSelectionButton, phoneTypeForm, buttonsPTHl);
        formPT.setMargin(marginLeft);
        
        HorizontalLayout ptpHl = new HorizontalLayout(propsGrid, formPTP);
        
        HorizontalLayout ptHl = new HorizontalLayout(typesGrid, formPT);
        
        VerticalLayout vl = new MVerticalLayout(heading, ptHl, ptpHl);
        vl.setSpacing(true);
        vl.setMargin(marginLeft);
        
        setCompositionRoot(vl);
    }
    
    public void buildButtons() {
        savePTPButton = new Button(FontAwesome.SAVE);
        savePTPButton.addClickListener(this::savePTPListener);
        
        deletePTPButton = new Button(FontAwesome.REMOVE);
        deletePTPButton.addClickListener(this::removePTPListener);
        deletePTPButton.setVisible(false);
        
        clearPTPSelectionButton = new Button("Clear selection");
        clearPTPSelectionButton.addClickListener(this::clearPTPSelectionListener);
        clearPTPSelectionButton.setVisible(false);
        
        savePTButton = new Button(FontAwesome.SAVE);
        savePTButton.addClickListener(this::savePtListener);
        
        deletePTButton = new Button(FontAwesome.REMOVE);
        deletePTButton.addClickListener(this::removePTListener);
        deletePTButton.setVisible(false);
        
        clearPTSelectionButton = new Button("Clear selection");
        clearPTSelectionButton.addClickListener(this::clearPTSelectionListener);
        clearPTSelectionButton.setVisible(false);
    }

    
    public void buildTypesGrid() {
        typesGrid = new Grid();
        typesGrid.setContainerDataSource(typesContainer);
        typesGrid.removeColumn("phoneTypePropertiesList");
        typesGrid.removeColumn("id");
        typesGrid.setHeightByRows(5);
        typesGrid.setHeightMode(HeightMode.ROW);
        
        typesGrid.addSelectionListener(this::typesGridSelectListener);
    }
    
    public void buildPropsGrid() {
        propsGrid = new Grid();
        propsGrid.setContainerDataSource(propsContainer);
        propsGrid.removeColumn("modelPropertiesList");
        propsGrid.removeColumn("id");
        propsGrid.removeColumn("category.id");
        propsGrid.removeColumn("category.phoneTypePropertiesList");
        
        propsGrid.addSelectionListener(this::propsGridSelectListener);
    }
    
    public void buildTypesContainer() {
        List<PhoneTypes> typeses = phoneTypesService.findAll();
        typesContainer = new BeanItemContainer<>(PhoneTypes.class, typeses);
    }
    
    public void buildPropsContainer() {
        List<PhoneTypeProperties> propertieses = phoneTypePropertiesService.findAll();
        propsContainer = new BeanItemContainer<>(PhoneTypeProperties.class, propertieses);
        propsContainer.addNestedContainerBean("category");
    }
    
    public void buildPhoneTypePropertiesForm() {
        ptpBinder = new FieldGroup();
        phoneTypePropertiesForm = new PhoneTypePropertiesForm(phoneTypesService.findAll());
    }
    
    public void buildPhoneTypeForm() {
        phoneTypeForm = new PhoneTypeForm();
        ptBinder = new FieldGroup();
    }
    
    public void savePTPListener(Button.ClickEvent event) {
        phoneTypePropertiesForm.enableValidation();
        try {  
            if (!ptpBinder.isValid()) {
                Notification.show("values not good", Notification.Type.TRAY_NOTIFICATION);
                return;
            }
            ptpBinder.commit();
        } catch(FieldGroup.CommitException exception) {
            return;
        }
        
        BeanItem<PhoneTypeProperties> ptpBean = (BeanItem<PhoneTypeProperties>)ptpBinder.getItemDataSource();
        PhoneTypeProperties ptp = ptpBean.getBean();
        if (ptp.getId() == null) {
            phoneTypePropertiesService.create(ptp);
            ptp = phoneTypePropertiesService.findPTPByPropertyNameAndCategory(ptp.getPropertyName(), ptp.getCategory());
            propsContainer.addItem(ptp);
            propsGrid.setContainerDataSource(propsContainer);
            bindNewPtp();
            Notification.show("ptp created", Notification.Type.TRAY_NOTIFICATION);
        } else {
            phoneTypePropertiesService.edit(ptp);
            propsGrid.setContainerDataSource(propsContainer);
            
            propsGrid.select(null);        
            bindNewPtp();
            deletePTPButton.setVisible(false);
            
            Notification.show("ptp edited", Notification.Type.TRAY_NOTIFICATION);
        }
    }
    
    public void savePtListener(Button.ClickEvent event) {
        phoneTypeForm.enableValidation();
        try {
            if (!ptBinder.isValid()) {
                Notification.show("values not good", Notification.Type.TRAY_NOTIFICATION);
                return;
            }
            ptBinder.commit();
        } catch(FieldGroup.CommitException exception) {
            return;
        }
        
        BeanItem<PhoneTypes> ptBean = (BeanItem<PhoneTypes>) ptBinder.getItemDataSource();
        PhoneTypes pt = ptBean.getBean();
        if (pt.getId() == null) {
            phoneTypesService.create(pt);
            /*pt = phoneTypesService.findByCategoryName(pt.getCategoryName());
            typesContainer.addBean(pt);
            typesGrid.setContainerDataSource(typesContainer);
            bindNewPt();*/
            init();
            Notification.show("pt created", Notification.Type.TRAY_NOTIFICATION);
        } else {
            phoneTypesService.edit(pt);
            /*typesGrid.setContainerDataSource(typesContainer);
            
            buildPropsContainer();
            propsGrid.setContainerDataSource(propsContainer);*/
            
            init();
            
            typesGrid.select(null);
            /*bindNewPt();
            deletePTButton.setVisible(false);*/
            Notification.show("ptp edited", Notification.Type.TRAY_NOTIFICATION);
        }
            
    }
    
    public void removePTPListener(Button.ClickEvent event) {
        BeanItem<PhoneTypeProperties> ptpBean = (BeanItem<PhoneTypeProperties>)ptpBinder.getItemDataSource();
        if (ptpBean == null) {
            return;
        }
        
        PhoneTypeProperties ptp = ptpBean.getBean();
        if (ptp == null) {
            return;
        }
        
        phoneTypePropertiesService.remove(ptp);
        propsContainer.removeItem(ptp);
        propsGrid.setContainerDataSource(propsContainer);
        
        bindNewPtp();
        deletePTPButton.setVisible(false);
        clearPTPSelectionButton.setVisible(false);
        Notification.show("ptp deleted", Notification.Type.TRAY_NOTIFICATION);
    }
    
    public void removePTListener(Button.ClickEvent event) {
        BeanItem<PhoneTypes> ptBean = (BeanItem<PhoneTypes>)ptBinder.getItemDataSource();
        if (ptBean == null) {
            return;
        }
        
        PhoneTypes pt = ptBean.getBean();
        phoneTypesService.remove(pt);
        /*typesContainer.removeItem(pt);
        typesGrid.setContainerDataSource(typesContainer);
        
        bindNewPt();
        deletePTButton.setVisible(false);
        clearPTSelectionButton.setVisible(false);*/
        init();
        Notification.show("pt deleted", Notification.Type.TRAY_NOTIFICATION);
    }
    
    private void bindNewPtp() {
        ptpToAdd = new PhoneTypeProperties();
        Item item = new BeanItem(ptpToAdd);
        ptpBinder.setItemDataSource(item);
        ptpBinder.bindMemberFields(phoneTypePropertiesForm);
        
        phoneTypePropertiesForm.disableValidation();
    }
    
    public void bindNewPt() {
        ptToAdd = new PhoneTypes();
        Item item = new BeanItem(ptToAdd);
        ptBinder.setItemDataSource(item);
        ptBinder.bindMemberFields(phoneTypeForm);
        
        phoneTypeForm.disableValidation();
    }
    
    public void clearPTPSelectionListener(Button.ClickEvent event) {
        bindNewPtp();
        deletePTPButton.setVisible(false);
        propsGrid.select(null);
        clearPTPSelectionButton.setVisible(false);
    }
    
    public void clearPTSelectionListener(Button.ClickEvent event) {
        bindNewPt();
        deletePTButton.setVisible(false);
        typesGrid.select(null);
        clearPTSelectionButton.setVisible(false);
    }
    
    public void propsGridSelectListener(SelectionEvent event) {
        PhoneTypeProperties ptp = (PhoneTypeProperties)propsGrid.getSelectedRow();
        if (ptp == null) {
            return;
        }
        Item item = new BeanItem<>(ptp);
        ptpBinder.setItemDataSource(item);
        ptpBinder.bindMemberFields(phoneTypePropertiesForm);
        
        clearPTPSelectionButton.setVisible(true);
        deletePTPButton.setVisible(true);
    }
    
    public void typesGridSelectListener(SelectionEvent event) {
        PhoneTypes pt = (PhoneTypes)typesGrid.getSelectedRow();
        if (pt == null) {
            return;
        }
        
        Item item = new BeanItem(pt);
        ptBinder.setItemDataSource(item);
        ptBinder.bindMemberFields(phoneTypeForm);
        
        clearPTSelectionButton.setVisible(true);
        deletePTButton.setVisible(true);
    }
}
