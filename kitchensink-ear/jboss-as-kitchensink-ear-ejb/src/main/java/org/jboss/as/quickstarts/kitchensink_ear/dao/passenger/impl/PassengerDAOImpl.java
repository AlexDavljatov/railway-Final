package org.jboss.as.quickstarts.kitchensink_ear.dao.passenger.impl;

import org.jboss.as.quickstarts.kitchensink_ear.dao.passenger.PassengerDAO;
import org.jboss.as.quickstarts.kitchensink_ear.dto.TicketDTO;
import org.jboss.as.quickstarts.kitchensink_ear.entity.Passenger;
import org.jboss.as.quickstarts.kitchensink_ear.entity.Ticket;
import org.jboss.as.quickstarts.kitchensink_ear.entity.Train;
import org.slf4j.LoggerFactory;

import javax.ejb.Stateless;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: alex
 * Date: 3/3/13
 * Time: 10:41 PM
 */
@Stateless
public class PassengerDAOImpl implements PassengerDAO {

    @Inject
    EntityManager em;
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(PassengerDAOImpl.class);

    @Override
    public Passenger getElement(Object o) {
        String email = (String) o;
        log.info("****** get " + email);
        TypedQuery<Passenger> query = em.createNamedQuery("getPassengerByEmail", Passenger.class);
        query.setParameter("email", email);
        if (query.getResultList().isEmpty()) return null;
        return query.getSingleResult();
    }

    @Override
    public boolean addElement(Passenger element) {
        log.info("****** add " + element.getEmail());
        TypedQuery<Passenger> query = em.createNamedQuery("getPassengerByEmail", Passenger.class);
        query.setParameter("email", element.getEmail());
//        log.debug("" + (query.getResultList() == null));
//        log.debug("" + query.getResultList().isEmpty());
////        log.debug("DoublePassenger is " + query.getSingleResult() + " " + (query.getSingleResult() == null));
//        log.debug("" + query.getResultList());
        if (query.getResultList().isEmpty()) {
            em.persist(element);
            return true;
        }
        return false;
    }

    @Override
    public boolean updateElement(Passenger element) {
        //To change body of implemented methods use File | Settings | File Templates.
        log.info("****** getElements " + element.getEmail());
        try {
            Passenger passenger = getElement(element.getEmail());
            passenger.setName(element.getName());
            passenger.setSurname(element.getSurname());
            passenger.setAdministrator(element.isAdministrator());
            passenger.setBirthdayDate(element.getBirthdayDate());
            passenger.setPassword(element.getPassword());
            return true;
        } catch (Exception e) {

            return false;
        }
    }

    @Override
    public List<Passenger> getAllElements() {
        log.info("****** getElements ");
        TypedQuery query = em.createNamedQuery("getAllPassengers", Passenger.class);

        return (List<Passenger>) query.getResultList();
    }

    @Override
    public boolean removeElement(Passenger element) {
        log.info("****** remove " + element.getEmail());
        Passenger passenger = getElement(element.getEmail());
        em.remove(passenger);
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }


    /*public List<Ticket> getPassengerTicketsByEmail(String email) {

        Query query = em.createNamedQuery("getPassengerTicketsByEmail");
        query.setParameter("email", email);

        return (List<Ticket>) query.getResultList();
    }
*/
    @Override
    public boolean isAdmin(String email) {
        Query query = em.createNamedQuery("isPassengerAdministrator");
        query.setParameter("email", email);
        return ((Boolean) query.getSingleResult());
    }

    @Override
    public List<Passenger> getPassengersByTrainNumber(String number) {
        log.info("PassengerDAOImpl + getPassengersByTrainNumber " + number);
        List<Passenger> result = new LinkedList<Passenger>();
        /*Query query = em.createNamedQuery("getTrainByNumber");
        query.setParameter("trainNumber", Integer.valueOf(number));*/
        TypedQuery<Train> trainTypedQuery = em.createNamedQuery("getTrainByNumber", Train.class);
        trainTypedQuery.setParameter("trainNumber", Integer.valueOf(number));


        TypedQuery<Ticket> ticketTypedQuery = em.createNamedQuery("getPassengerByTrain", Ticket.class);
        ticketTypedQuery.setParameter("train", trainTypedQuery.getSingleResult());
//        TypedQuery<Ticket> query = em.createNamedQuery("", Ticket.class);
//        query.setParameter("trainNumber", Integer.valueOf(number));
        log.info("PassengerDAOImpl + getPassengersByTrainNumber " + (List<Ticket>) ticketTypedQuery.getResultList());
        if (ticketTypedQuery.getResultList() != null) {
            for (Ticket ticket : ticketTypedQuery.getResultList()) {
                if (ticket != null)
                    result.add(ticket.getPassenger());
            }
            return result;
        }

        log.info("PassengerDAOImpl + getPassengersByTrainNumber " + result);
        return result;
    }
}
