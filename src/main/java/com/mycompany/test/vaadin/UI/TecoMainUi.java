package com.mycompany.test.vaadin.UI;

import com.mycompany.test.vaadin.Components.SideMenuComponent;
import com.mycompany.test.vaadin.Views.ActionsView;
import com.mycompany.test.vaadin.Views.HomeView;
import com.mycompany.test.vaadin.Views.LoginView;
import com.mycompany.test.vaadin.Views.OsView;
import com.mycompany.test.vaadin.Views.PhoneTypesView;
import com.mycompany.test.vaadin.Views.ProjectsView;
import com.mycompany.test.vaadin.Views.ReportsView;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Widgetset;
import com.vaadin.cdi.CDIUI;
import com.vaadin.cdi.CDIViewProvider;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.Resource;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import javax.inject.Inject;

/**
 *
 */
@Theme("mytheme")
@Widgetset("com.mycompany.test.vaadin.MyAppWidgetset")
@CDIUI("")
public class TecoMainUi extends UI {

    HorizontalLayout appLayout;
    Navigator navigator;
    SideMenuComponent sideMenu;
    Panel contentPanel = new Panel();
    
    LoginView loginView;
    HomeView homeView;
    ReportsView reportsView;

    ProjectsView projectsView;
    
    @Inject
    CDIViewProvider viewProvider;
    
    @Override
    protected void init(VaadinRequest vaadinRequest) {
        setSizeFull();
        
        Resource res = new ThemeResource("img/teco_icon.png");
        Image img = new Image(null, res);
        img.setWidth(100, Unit.PERCENTAGE);
        
        sideMenu = new SideMenuComponent();
        VerticalLayout leftSide = new VerticalLayout(img, sideMenu);
        leftSide.addStyleName("width-15");
        contentPanel.setSizeFull();
        appLayout = new HorizontalLayout(leftSide, contentPanel);
        appLayout.setSizeFull();
        
        appLayout.setExpandRatio(leftSide, 1);
        appLayout.setExpandRatio(contentPanel, 5);
        
        setContent(appLayout);
        
        navigator = new Navigator(this, contentPanel);
        
        loginView = new LoginView();
        homeView = new HomeView();
        reportsView = new ReportsView();
        
        navigator.addProvider(viewProvider);
        
        /*navigator.addView(LoginView.NAME, loginView);
        navigator.addView(HomeView.NAME, homeView);
        navigator.addView(ReportsView.NAME, reportsView);
        navigator.addView(ProjectsView.NAME, projectsView);*/
        navigator.navigateTo(LoginView.NAME);
    }
    
    public HorizontalLayout getAppLayout() {
        return appLayout;
    }
    
    public void switchView(String view) {
        switch(view){
            case HomeView.NAME:
                navigator.navigateTo(view);
                break;
            case ReportsView.NAME:
                navigator.navigateTo(view);
                break;
            case ProjectsView.NAME:
                navigator.navigateTo(view);
                break;
            case OsView.NAME:
                navigator.navigateTo(view);
                break;    
            case PhoneTypesView.NAME:
                navigator.navigateTo(view);
                break;
            case ActionsView.NAME:
                navigator.navigateTo(view);
                break;
        }        
    }
}
