/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.test.vaadin.Services;

import com.mycompany.test.vaadin.Entities.PhoneTypes;
import com.mycompany.test.vaadin.Facades.PhoneTypesFacade;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author alex
 */

@Stateless
public class PhoneTypesService {
    
    @Inject
    private PhoneTypesFacade phoneTypesFacade;
    
    public void create(PhoneTypes pt) {
        phoneTypesFacade.create(pt);
    }
    
    public void edit(PhoneTypes pt) {
        phoneTypesFacade.edit(pt);
    }
    
    public void remove(PhoneTypes pt) {
        phoneTypesFacade.remove(pt);
    }
    
    public PhoneTypes find(Integer id) {
        return phoneTypesFacade.find(id);
    }
    
    public List<PhoneTypes> findAll() {
        return phoneTypesFacade.findAll();
    }
    
    public PhoneTypes findByCategoryName(String category) {
        return phoneTypesFacade.findByCategoryName(category);
    }
}
