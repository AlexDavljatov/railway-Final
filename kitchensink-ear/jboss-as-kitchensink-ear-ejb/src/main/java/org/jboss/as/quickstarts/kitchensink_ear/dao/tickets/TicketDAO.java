package org.jboss.as.quickstarts.kitchensink_ear.dao.tickets;


import org.jboss.as.quickstarts.kitchensink_ear.dao.BaseDAO;
import org.jboss.as.quickstarts.kitchensink_ear.data.LoginPassword;
import org.jboss.as.quickstarts.kitchensink_ear.entity.Ticket;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: alex
 * Date: 3/3/13
 * Time: 2:55 AM
 * To change this template use File | Settings | File Templates.
 */
public interface TicketDAO extends BaseDAO<Ticket> {

    List<Ticket> getTicketsByPassengerEmail(String email);

    boolean buyTicket(LoginPassword loginPassword, String trainNumber, String stationName);

    boolean buyTicket(String email, String trainNumber, String stationName);
}
