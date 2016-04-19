package com.mycompany.test.vaadin.Entities;

import com.mycompany.test.vaadin.Entities.BehaviourReasons;
import com.mycompany.test.vaadin.Entities.Models;
import com.mycompany.test.vaadin.Entities.ProjectsActionsProperties;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-04-19T15:53:01")
@StaticMetamodel(Behaviours.class)
public class Behaviours_ { 

    public static volatile SingularAttribute<Behaviours, BehaviourReasons> reason;
    public static volatile SingularAttribute<Behaviours, String> propertyValue;
    public static volatile SingularAttribute<Behaviours, Models> model;
    public static volatile SingularAttribute<Behaviours, ProjectsActionsProperties> projectActionProperty;
    public static volatile SingularAttribute<Behaviours, Long> id;

}