package com.mycompany.test.vaadin.Entities;

import com.mycompany.test.vaadin.Entities.Behaviours;
import com.mycompany.test.vaadin.Entities.ProjectsActions;
import com.mycompany.test.vaadin.Entities.StkActionProperties;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-04-19T22:15:29")
@StaticMetamodel(ProjectsActionsProperties.class)
public class ProjectsActionsProperties_ { 

    public static volatile ListAttribute<ProjectsActionsProperties, Behaviours> behavioursList;
    public static volatile SingularAttribute<ProjectsActionsProperties, ProjectsActions> projectAction;
    public static volatile SingularAttribute<ProjectsActionsProperties, StkActionProperties> property;
    public static volatile SingularAttribute<ProjectsActionsProperties, Long> id;

}