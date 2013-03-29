package org.jboss.as.quickstarts.kitchensink_ear.dao.tickets.impl;

import org.jboss.as.quickstarts.kitchensink_ear.dao.tickets.TicketDAO;
import org.jboss.as.quickstarts.kitchensink_ear.data.LoginPassword;
import org.jboss.as.quickstarts.kitchensink_ear.entity.Passenger;
import org.jboss.as.quickstarts.kitchensink_ear.entity.Shedule;
import org.jboss.as.quickstarts.kitchensink_ear.entity.Ticket;
import org.jboss.as.quickstarts.kitchensink_ear.entity.Train;
import org.slf4j.LoggerFactory;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

//import org.jboss.as.quickstarts.greeter.others.LoginPassword;

/**
 * Created with IntelliJ IDEA.
 * User: alex
 * Date: 3/3/13
 * Time: 2:55 AM
 * To change this template use File | Settings | File Templates.
 */
@Stateless
public class TicketDAOImpl implements TicketDAO {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(TicketDAOImpl.class);

    @Inject
    EntityManager em;
    @Override
    public Ticket getElement(Object o) {
        Passenger passenger = (Passenger) o;

        TypedQuery<Ticket> query = em.createNamedQuery("getPassengerTicketOwner", Ticket.class);
        return query.getSingleResult();
    }

    @Override
    public boolean addElement(Ticket element) {
        em.persist(element);

        return true;
    }

    @Override
    public boolean updateElement(Ticket element) {
        //To change body of implemented methods use File | Settings | File Templates.
        return false;
    }

    @Override
    public List<Ticket> getAllElements() {
        Query query = em.createQuery("Select ticket from Ticket ticket");
        return (List<Ticket>) query.getResultList();
    }

    @Override
    public boolean removeElement(Ticket element) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<Ticket> getTicketsByPassengerEmail(String email) {
        Query ticketTypedQuery = em.createNamedQuery("getPassengerTicketsByEmail");
        ticketTypedQuery.setParameter("email", email);
        log.info("TicketDAOImpl getTicketsByPassengerEmail " + ticketTypedQuery.getResultList());
        return (List<Ticket>) ticketTypedQuery.getResultList();
    }

    @Override
    public boolean buyTicket(LoginPassword loginPassword, String trainNumber, String stationName) {

//        TypedQuery<Ticket> ticketsByTrainNumberQuery = em.createNamedQuery("getTicketsByTrainNumber", Ticket.class);
//        ticketsByTrainNumberQuery.setParameter("trainNumber", trainNumber);
        TypedQuery<Passenger> passengerQuery = em.createNamedQuery("getPassengerByEmail", Passenger.class);
        passengerQuery.setParameter("email", loginPassword.getEmail());
        // <10 minutes
        TypedQuery<Shedule> sheduleQuery = em.createNamedQuery("getAnotherShedulerByStationAndTrain", Shedule.class);
        sheduleQuery.setParameter("trainNumber", Integer.valueOf(trainNumber));
        sheduleQuery.setParameter("stationName", stationName);


        if (sheduleQuery.getResultList().isEmpty()) {
            return false;
        }

//        SheduleDTO shedule = sheduleQuery.getSingleResult();
        Shedule shedule = sheduleQuery.getResultList().get(0);
        Date trainArrivalDate = shedule.getTime();
        Train train = shedule.getTrain();
        if (Math.abs(trainArrivalDate.getTime() - System.currentTimeMillis()) < 600000) {
            return false;
        }


        // available sits exist
        if (train.getTickets().size() >=
                shedule.getTrain().getSits_number()) {
            return false;
        }

        //TODO: check, whether passenger is registered
        Passenger passenger = passengerQuery.getSingleResult();

        //TODO: no passengers with the same name, surname, birthday Date

        for (Ticket ticket : train.getTickets()) {
            Passenger rival = ticket.getPassenger();
//            log.debug("Buy ticket " + passenger.getName() + " < - > " + rival.getName() + "  " +
//                    passenger.getSurname() + " < - > " + rival.getSurname() + "  " +
//                    passenger.getBirthdayDate() + " < - > " + rival.getBirthdayDate());
//            log.debug("Buy ticket equals " + passenger.getName().equals(rival.getName()) + " " +
//                    passenger.getSurname().equals(rival.getSurname()) + " " +
//                    passenger.getBirthdayDate().equals(rival.getBirthdayDate()));
//            if (passenger.getName().equals(rival.getName()) && passenger.getSurname().equals(rival.getSurname())
//                    && passenger.getBirthdayDate().equals(rival.getBirthdayDate())) return false;
            if (passenger.equals(rival)) {
                return false;
            }
        }

        //TODO: 10 minutes before train's arrival
        Ticket newTicket = new Ticket(train.getTickets().size() + 1, passenger, train);
        train.getTickets().add(newTicket);
        em.persist(newTicket);
        return true;
    }

    @Override
    public boolean buyTicket(String email, String trainNumber, String stationName) {

//        TypedQuery<Ticket> ticketsByTrainNumberQuery = em.createNamedQuery("getTicketsByTrainNumber", Ticket.class);
//        ticketsByTrainNumberQuery.setParameter("trainNumber", trainNumber);
        TypedQuery<Passenger> passengerQuery = em.createNamedQuery("getPassengerByEmail", Passenger.class);
        passengerQuery.setParameter("email", email);
        // <10 minutes
        TypedQuery<Shedule> sheduleQuery = em.createNamedQuery("getAnotherShedulerByStationAndTrain", Shedule.class);
        sheduleQuery.setParameter("trainNumber", Integer.valueOf(trainNumber));
        sheduleQuery.setParameter("stationName", stationName);


        if (sheduleQuery.getResultList().isEmpty()) {
            return false;
        }

//        SheduleDTO shedule = sheduleQuery.getSingleResult();
        Shedule shedule = sheduleQuery.getResultList().get(0);
        Date trainArrivalDate = shedule.getTime();
        Train train = shedule.getTrain();
        if (Math.abs(trainArrivalDate.getTime() - System.currentTimeMillis()) < 600000) {
            return false;
        }


        // available sits exist
        if (train.getTickets().size() >=
                shedule.getTrain().getSits_number()) {
            return false;
        }

        //TODO: check, whether passenger is registered
        Passenger passenger = passengerQuery.getSingleResult();

        //TODO: no passengers with the same name, surname, birthday Date

        for (Ticket ticket : train.getTickets()) {
            Passenger rival = ticket.getPassenger();
//            log.debug("Buy ticket " + passenger.getName() + " < - > " + rival.getName() + "  " +
//                    passenger.getSurname() + " < - > " + rival.getSurname() + "  " +
//                    passenger.getBirthdayDate() + " < - > " + rival.getBirthdayDate());
//            log.debug("Buy ticket equals " + passenger.getName().equals(rival.getName()) + " " +
//                    passenger.getSurname().equals(rival.getSurname()) + " " +
//                    passenger.getBirthdayDate().equals(rival.getBirthdayDate()));
//            if (passenger.getName().equals(rival.getName()) && passenger.getSurname().equals(rival.getSurname())
//                    && passenger.getBirthdayDate().equals(rival.getBirthdayDate())) return false;
            if (passenger.equals(rival)) {
                return false;
            }
        }

        //TODO: 10 minutes before train's arrival
        Ticket newTicket = new Ticket(train.getTickets().size() + 1, passenger, train);
        train.getTickets().add(newTicket);
        em.persist(newTicket);
        return true;
    }
}