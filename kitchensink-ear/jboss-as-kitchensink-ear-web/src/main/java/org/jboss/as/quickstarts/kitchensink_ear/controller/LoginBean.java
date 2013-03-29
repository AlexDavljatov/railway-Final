package org.jboss.as.quickstarts.kitchensink_ear.controller;

import org.jboss.as.quickstarts.kitchensink_ear.data.LoginPassword;
import org.jboss.as.quickstarts.kitchensink_ear.service.PassengerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
//import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: Alex
 * Date: 16.03.13
 * Time: 21:41
 * To change this template use File | Settings | File Templates.
 */
@Model
public class LoginBean {
//    @Inject
//    private Logger log = ;
    private static final Logger log = LoggerFactory.getLogger(LoginBean.class);

    @Inject
    private FacesContext facesContext;
    @Inject
    private PassengerService passengerService;
//    @Inject
//    private UserSession session;
    //    @Inject
    private LoginPassword newAccount = new LoginPassword();

    private boolean admin;

    @Produces
    @Named
    public LoginPassword getNewAccount() {
        return newAccount;
    }

    public String login() throws Exception {
        log.info("       login " + newAccount.getEmail() );
        if (!facesContext.getExternalContext().getSessionMap().containsKey("account")) {
            log.info("not logged in " + newAccount.getEmail());
        } else {
            LoginPassword loggedPassenger = (LoginPassword)facesContext.getExternalContext().getSessionMap().get("account");
            log.info("already logged in " + loggedPassenger.getEmail());
            return "viewTickets?faces-redirect=true";
        }

        try {
            if (passengerService.login(newAccount)) {
//                facesContext.getAttributes().put("account", newAccount);
                admin = passengerService.isAdmin(newAccount.getEmail());
                facesContext.getExternalContext().getSessionMap().put("account", newAccount);
                facesContext.getExternalContext().getSessionMap().put("admin", admin);
                facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Logged in!", "Hello " +
                ((LoginPassword)facesContext.getExternalContext().getSessionMap().get("account")).getEmail()));
                log.info("       login: is admin?" + admin);
                initNewAccount();
                return "viewTickets?faces-redirect=true";
            } else {
                facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Invalid email/password combination!",
                        "Invalid email/password combination!"));
            }
        } catch (Exception e) {
            String errorMessage = getRootErrorMessage(e);
            FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage,
                    "Registration Unsuccessful");
            facesContext.addMessage(null, m);
        }
        return "login?faces-redirect=true";
    }

    public String logout(){
        facesContext.getExternalContext().invalidateSession();
        return "login?faces-redirect=true";
    }

    @PostConstruct
    public void initNewAccount() {
        newAccount = new LoginPassword();
    }

    private String getRootErrorMessage(Exception e) {
        // Default to general error message that registration failed.
        String errorMessage = "Log in failed. See server log for more information";
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

     public String register(){
         log.info("I'm really here");
         return "register?faces-redirect=true";
     }
}

