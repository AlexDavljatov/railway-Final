package org.jboss.as.quickstarts.kitchensink_ear.controller;

import org.jboss.as.quickstarts.kitchensink_ear.dto.PassengerDTO;
import org.jboss.as.quickstarts.kitchensink_ear.service.PassengerService;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created with IntelliJ IDEA.
 * User: Alex
 * Date: 18.03.13
 * Time: 0:39
 * To change this template use File | Settings | File Templates.
 */
@Model
public class RegisterBean {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(RegisterBean.class);

    @Inject
    private FacesContext facesContext;
    @Inject
    private PassengerService passengerService;

    private PassengerDTO newPassenger;

    @Produces
    @Named
    public PassengerDTO getNewPassenger() {
        return newPassenger;
    }

    public String register() throws Exception {
        log.info("register " + newPassenger.getEmail());
        log.info("register " + newPassenger.getBirthdayDate().toString());
        try {
            if (passengerService.register(newPassenger)) {
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Registered!", "Registration successful"));
            initNewMember();
            return "login";
            } else {
                facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Duplicate email", "Registration Unsuccessful"));
            }
        } catch (Exception e) {
            String errorMessage = getRootErrorMessage(e);
            FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage,
                    "Registration Unsuccessful");
            facesContext.addMessage(null, m);
        }
        return "register";
    }

    @PostConstruct
    public void initNewMember() {
        newPassenger = new PassengerDTO();
    }

    private String getRootErrorMessage(Exception e) {
        // Default to general error message that registration failed.
        String errorMessage = "Registration failed. See server log for more information";
        if (e == null) {
            // This shouldn't happen, but return the default messages
            return errorMessage;
        }

        // Start with the exception and recurse to find the root cause
        Throwable t = e;
        while (t != null) {
            // Get the message from the Throwable class instance
            errorMessage = t.getLocalizedMessage();
            t = t.getCause();
        }
        // This is the root cause message
        return errorMessage;
    }

}
