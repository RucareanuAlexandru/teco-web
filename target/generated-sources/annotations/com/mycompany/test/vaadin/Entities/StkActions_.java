package com.mycompany.test.vaadin.Entities;

import com.mycompany.test.vaadin.Entities.ProjectsActions;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-04-19T22:15:29")
@StaticMetamodel(StkActions.class)
public class StkActions_ { 

    public static volatile SingularAttribute<StkActions, String> name;
    public static volatile SingularAttribute<StkActions, String> description;
    public static volatile ListAttribute<StkActions, ProjectsActions> projectsActionsList;
    public static volatile SingularAttribute<StkActions, Long> id;
    public static volatile SingularAttribute<StkActions, String> type;

}