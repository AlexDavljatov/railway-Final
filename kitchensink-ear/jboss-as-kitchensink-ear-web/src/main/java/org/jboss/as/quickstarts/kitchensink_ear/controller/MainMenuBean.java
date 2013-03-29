package org.jboss.as.quickstarts.kitchensink_ear.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.faces.application.FacesMessage;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: Alex
 * Date: 25.03.13
 * Time: 17:21
 * To change this template use File | Settings | File Templates.
 */
@Model
public class MainMenuBean implements Serializable{

    private static final Logger log = LoggerFactory.getLogger(MainMenuBean.class);

    @Inject
    FacesContext facesContext;

    private boolean admin;

    @PostConstruct
    public void init(){
        admin = (Boolean)(facesContext.getExternalContext().getSessionMap().get("admin"));
        log.info("admin: " + admin);
    }

    public void save() {
        addMessage("Data saved");
    }

    public void update() {
        addMessage("Data updated");
    }

    public void delete() {
        addMessage("Data deleted");
    }

    public String gotoMyCabinet(){
        addMessage("View your personal data");
        return "viewTickets?faces-redirect=true";
    }

    public String gotoEditPassengers(){
        addMessage("You can edit passengers here");
        return "editPassengers?faces-redirect=true";
    }
    public String gotoEditTrains(){
        addMessage("You can add trains here");
        return "editTrains?faces-redirect=true";
    }
    public String gotoEditStations(){
        addMessage("You can edit stations here");
        return "editStations?faces-redirect=true";
    }
    public String gotoEditShedule(){
        addMessage("You can buy ticket here");
        return "editShedule?faces-redirect=true";
    }
    public String gotoBuyTicket(){
        addMessage("You can buy ticket here");
        return "buyTicket?faces-redirect=true";
    }
    public void addMessage(String summary) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary,  null);
        log.info("I really logged out");
        facesContext.addMessage(null, message);
    }

    public String gotoRegisterPage(){
        addMessage("You can register here");
        return "register?faces-redirect=true";

    }

    public String logout(){
        facesContext.getExternalContext().getSessionMap().clear();
        facesContext.getExternalContext().invalidateSession();
        log.info("Really logged out");
        return "login?faces-redirect=true";
    }

    public String gotoFindTrain(){
        return "findTrain?faces-redirect=true";
    }

    public boolean isAdmin() {
        return admin;
    }
}
