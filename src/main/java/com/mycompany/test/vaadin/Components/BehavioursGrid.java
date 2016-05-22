/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.test.vaadin.Components;

import com.mycompany.test.vaadin.Entities.BehaviourReasons;
import com.mycompany.test.vaadin.Entities.Behaviours;
import com.vaadin.data.Property;
import com.vaadin.data.util.ObjectProperty;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.TextField;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.jsoup.select.Collector;

/**
 *
 * @author alex
 */
public class BehavioursGrid extends GridLayout {

    private Map<String, List<Behaviours>> behavioursMap;
    private int nrRows;
    private List<BehaviourReasons> reasons;

    public BehavioursGrid(Map behavioursMap, List<BehaviourReasons> reasons) {
        this.behavioursMap = behavioursMap;
        this.reasons = reasons;
        
        calculateNumberOfRows();
        setRows(nrRows);
        setColumns(4);
        
        buildLayout();
        
        setSizeFull();
        setColumnExpandRatio(3, 1);
    }

    private void buildLayout() {
        buildGridHeader();
        
        int row = 1;
        Set<String> keys = behavioursMap.keySet();
        Iterator<String> it = keys.iterator();
        
        while (it.hasNext()) {
            String actionName = it.next();
            List<Behaviours> bs = behavioursMap.get(actionName);
            
            if (bs == null || bs.isEmpty()) {
                return;
            }
            Label actionLabel = new Label(actionName);
            actionLabel.setSizeUndefined();
            actionLabel.setStyleName("label-margin");
            
            addComponent(actionLabel, 0, row, 0, row + bs.size() - 1);
            setComponentAlignment(actionLabel, Alignment.MIDDLE_CENTER);
            for (Behaviours b: bs) {
                String propertyName = b.getProjectActionProperty().getProperty().getName();
                Label propertyLabel = new Label(propertyName);
                propertyLabel.setSizeUndefined();
                addComponent(propertyLabel, 1, row);
                setComponentAlignment(propertyLabel, Alignment.MIDDLE_CENTER);
                
                ObjectProperty<String> propValue = new ObjectProperty<>(b.getPropertyValue());                
                TextField field = new TextField(propValue);
                field.addValueChangeListener(new Property.ValueChangeListener() {
                    @Override
                    public void valueChange(Property.ValueChangeEvent event) {
                        String value = (String) event.getProperty().getValue();
                        b.setPropertyValue(value);
                    }
                });
                field.setSizeUndefined();
                addComponent(field, 2, row);
                
                NativeSelect ns = new NativeSelect();
                ns.addItems(reasons);
                
                if (b.getReason() != null) {
                    ns.select(b.getReason());
                } else {
                    List<BehaviourReasons> defaultList = reasons.stream()
                            .filter(r -> r.getReasonDescription().equalsIgnoreCase("Default"))
                            .collect(Collectors.toList());
                    if (defaultList != null && !defaultList.isEmpty()) {
                        ns.select(defaultList.get(0));
                    }
                }
                ns.setSizeFull();
                ns.setNullSelectionAllowed(false);
                addComponent(ns, 3, row);
                ns.addValueChangeListener(new Property.ValueChangeListener() {
                    @Override
                    public void valueChange(Property.ValueChangeEvent event) {
                        BehaviourReasons br = (BehaviourReasons) event.getProperty().getValue();
                        b.setReason(br);
                    }
                });
                
                row++;
            }
        }
    }

    private void calculateNumberOfRows() {
        nrRows = 1; // starts with one because of the header
        Collection<List<Behaviours>> values = behavioursMap.values();
        values.stream().forEach(elem -> nrRows += elem.size());
    }

    private void buildGridHeader() {
        Label actionNameHeader = new Label("Action name");
        actionNameHeader.setSizeUndefined();
        actionNameHeader.setStyleName("label-margin");
        addComponent(actionNameHeader, 0, 0);
        Label propertyNameHeader = new Label("Property name");
        propertyNameHeader.setSizeUndefined();
        propertyNameHeader.setStyleName("label-margin");
        addComponent(propertyNameHeader, 1, 0);
        addComponent(new Label("Property value"), 2, 0);
        addComponent(new Label("Reason"), 3, 0);
    }

}
