package org.jboss.as.quickstarts.kitchensink_ear.controller.admin;

import org.jboss.as.quickstarts.kitchensink_ear.dto.PassengerDTO;
import org.jboss.as.quickstarts.kitchensink_ear.service.PassengerService;
import org.primefaces.event.RowEditEvent;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: Alex
 * Date: 17.03.13
 * Time: 0:45
 * To change this template use File | Settings | File Templates.
 */
@Model
public class EditPassengerBean {
    @Inject
    private Logger log;
    @Inject
    private FacesContext facesContext;
    @Inject
    private PassengerService passengerService;

    private List<PassengerDTO> passengers;
    private boolean admin;
    @PostConstruct
    private void init(){
        passengers = passengerService.getAllPassengers();
        admins = new boolean[]{true, false};
        admin = (Boolean)(facesContext.getExternalContext().getSessionMap().get("admin"));
        log.info("++++++ getAllPassenger ");
    }

    @Produces
    @Named
    public List<PassengerDTO> getPassengers(){
        return passengers;
    }

    private boolean [] admins;

    @Produces
    @Named
    public boolean[] getAdmins() {
        return admins;
    }

    public void onEdit(RowEditEvent event) {
        log.info("++++++ edit " + ((PassengerDTO) event.getObject()).getEmail() + " " +
                ((PassengerDTO) event.getObject()).isAdministrator());
        FacesMessage msg = new FacesMessage("Passenger Edited", ((PassengerDTO) event.getObject()).getEmail());
        if (passengerService.update((PassengerDTO) event.getObject())) FacesContext.getCurrentInstance().addMessage(null, msg);
        else onCancel(event);
    }

    public void onCancel(RowEditEvent event) {
        log.info("++++++ cancel " + ((PassengerDTO) event.getObject()).getEmail());
        FacesMessage msg = new FacesMessage("Passenger Cancelled", ((PassengerDTO) event.getObject()).getEmail());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public boolean isAdmin() {
        return admin;
    }
}
