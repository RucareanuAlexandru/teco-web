package com.mycompany.test.vaadin.Entities;

import com.mycompany.test.vaadin.Entities.Behaviours;
import com.mycompany.test.vaadin.Entities.ModelProperties;
import com.mycompany.test.vaadin.Entities.Os;
import com.mycompany.test.vaadin.Entities.Tacs;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-04-19T22:15:29")
@StaticMetamodel(Models.class)
public class Models_ { 

    public static volatile ListAttribute<Models, Behaviours> behavioursList;
    public static volatile SingularAttribute<Models, String> modelName;
    public static volatile SingularAttribute<Models, String> brandName;
    public static volatile SingularAttribute<Models, Os> os;
    public static volatile SingularAttribute<Models, String> modelId;
    public static volatile ListAttribute<Models, Tacs> tacsList;
    public static volatile ListAttribute<Models, ModelProperties> modelPropertiesList;

}