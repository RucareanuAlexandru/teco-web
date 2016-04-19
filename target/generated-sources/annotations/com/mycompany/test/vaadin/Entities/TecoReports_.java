package com.mycompany.test.vaadin.Entities;

import com.mycompany.test.vaadin.Entities.TecoReportParameters;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-04-19T15:53:01")
@StaticMetamodel(TecoReports.class)
public class TecoReports_ { 

    public static volatile SingularAttribute<TecoReports, String> reportName;
    public static volatile SingularAttribute<TecoReports, byte[]> jrxmlPdfFile;
    public static volatile SingularAttribute<TecoReports, byte[]> jrxmlCsvFile;
    public static volatile SingularAttribute<TecoReports, String> reportDescription;
    public static volatile SingularAttribute<TecoReports, Integer> id;
    public static volatile ListAttribute<TecoReports, TecoReportParameters> tecoReportParametersList;

}