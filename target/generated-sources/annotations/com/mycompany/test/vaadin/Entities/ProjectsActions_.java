package com.mycompany.test.vaadin.Entities;

import com.mycompany.test.vaadin.Entities.Projects;
import com.mycompany.test.vaadin.Entities.ProjectsActionsProperties;
import com.mycompany.test.vaadin.Entities.StkActions;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-04-19T22:15:29")
@StaticMetamodel(ProjectsActions.class)
public class ProjectsActions_ { 

    public static volatile ListAttribute<ProjectsActions, ProjectsActionsProperties> projectsActionsPropertiesList;
    public static volatile SingularAttribute<ProjectsActions, Projects> project;
    public static volatile SingularAttribute<ProjectsActions, StkActions> action;
    public static volatile SingularAttribute<ProjectsActions, Long> id;

}