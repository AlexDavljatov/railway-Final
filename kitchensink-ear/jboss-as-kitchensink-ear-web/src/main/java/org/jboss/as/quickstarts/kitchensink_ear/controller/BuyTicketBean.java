package org.jboss.as.quickstarts.kitchensink_ear.controller;

import org.jboss.as.quickstarts.kitchensink_ear.dto.TicketDTO;
import org.jboss.as.quickstarts.kitchensink_ear.service.TicketService;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Alex
 * Date: 17.03.13
 * Time: 0:47
 * To change this template use File | Settings | File Templates.
 */
@Model
public class BuyTicketBean {
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(BuyTicketBean.class);
    @Inject
    private FacesContext facesContext;
    @Inject
    private TicketService ticketService;

    private List<TicketDTO> tickets;

    //TODO: passengers name
    @PostConstruct
    private void init(){
        log.info("BuyTicketBean" + "init()");

        log.info("BuyTicketBean" + "init()" + tickets);
    }

    @Produces
    @Named
    public List<TicketDTO> getTickets() {
        return tickets;
    }


}
