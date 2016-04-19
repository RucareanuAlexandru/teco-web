package com.mycompany.test.vaadin.Entities;

import com.mycompany.test.vaadin.Entities.ModelProperties;
import com.mycompany.test.vaadin.Entities.PhoneTypes;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-04-19T22:15:29")
@StaticMetamodel(PhoneTypeProperties.class)
public class PhoneTypeProperties_ { 

    public static volatile SingularAttribute<PhoneTypeProperties, String> propertyName;
    public static volatile ListAttribute<PhoneTypeProperties, ModelProperties> modelPropertiesList;
    public static volatile SingularAttribute<PhoneTypeProperties, Long> id;
    public static volatile SingularAttribute<PhoneTypeProperties, PhoneTypes> category;

}