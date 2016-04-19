/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.test.vaadin.Components;

/**
 *
 * @author alex
 */
public enum StkActionType {
    COMMAND,
    EVENT,
    SPECIAL;
    
    public static StkActionType getStkActionTypeFromString(String name) {
        switch(name.toLowerCase()) {
            case "command":
                return COMMAND;
            case "event":
                return EVENT;
            case "special":
                return SPECIAL;
            default:
                return null;    
        }
    }
}
