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
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.TextField;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
            
            addComponent(new Label(actionName), 0, row, 0, row + bs.size() - 1);
            for (Behaviours b: bs) {
                String propertyName = b.getProjectActionProperty().getProperty().getName();
                addComponent(new Label(propertyName), 1, row);
                
                ObjectProperty<String> propValue = new ObjectProperty<>(b.getPropertyValue());                
                TextField field = new TextField(propValue);
                field.addValueChangeListener(new Property.ValueChangeListener() {
                    @Override
                    public void valueChange(Property.ValueChangeEvent event) {
                        String value = (String) event.getProperty().getValue();
                        b.setPropertyValue(value);
                    }
                });
                addComponent(field, 2, row);
                
                NativeSelect ns = new NativeSelect();
                ns.addItems(reasons);
                
                if (b.getReason() != null) {
                    ns.select(b.getReason());
                }
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
        addComponent(new Label("Action name"), 0, 0);
        addComponent(new Label("Property name"), 1, 0);
        addComponent(new Label("Property value"), 2, 0);
        addComponent(new Label("Reason"), 3, 0);
    }

}
