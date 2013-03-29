package org.jboss.as.quickstarts.kitchensink_ear.service;

import org.jboss.as.quickstarts.kitchensink_ear.dao.tickets.TicketDAO;
import org.jboss.as.quickstarts.kitchensink_ear.data.LoginPassword;
import org.jboss.as.quickstarts.kitchensink_ear.dto.PassengerDTO;
import org.jboss.as.quickstarts.kitchensink_ear.dto.TicketDTO;
import org.jboss.as.quickstarts.kitchensink_ear.dto.TrainDTO;
import org.jboss.as.quickstarts.kitchensink_ear.entity.Ticket;
import org.jboss.as.quickstarts.kitchensink_ear.entity.Train;
import org.slf4j.LoggerFactory;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: Alex
 * Date: 17.03.13
 * Time: 1:55
 * To change this template use File | Settings | File Templates.
 */
@Stateless
public class TicketService implements Serializable {
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(TicketService.class);
    @Inject
    private TicketDAO ticketDAO;
    //log.entering("TicketService", "", new String[]{lp.getEmail(), ""+train.getNumber()});

    public boolean buyTicket(String email, String train, String station){
        log.info("TicketService: buyTicket: " + email + " " + train + " " + station);
//        ticketDAO.buyTicket(email, train, station);
        return ticketDAO.buyTicket(email, train, station);
    }

    public List<TicketDTO> getPassengersTickets(String email){
        log.info("TicketService " + " getPassengersTickets " +  email);
        List<TicketDTO> result = new LinkedList<TicketDTO>();
        for (Ticket ticket: ticketDAO.getTicketsByPassengerEmail(email))
            result.add(new TicketDTO(ticket.getNumber(),
                    new PassengerDTO(ticket.getPassenger().getName(), ticket.getPassenger().getSurname(),
                            ticket.getPassenger().getEmail(), ticket.getPassenger().getPassword(),
                            ticket.getPassenger().getBirthdayDate()),
                    new TrainDTO(ticket.getTrain().getNumber(), ticket.getTrain().getSits_number())));
        log.info("TicketService " + " getPassengersTickets " +  result);
        return result;
    }
}
