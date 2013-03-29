package org.jboss.as.quickstarts.kitchensink_ear.service;

import org.jboss.as.quickstarts.kitchensink_ear.dao.passenger.PassengerDAO;
import org.jboss.as.quickstarts.kitchensink_ear.data.LoginPassword;
import org.jboss.as.quickstarts.kitchensink_ear.dto.PassengerDTO;
import org.jboss.as.quickstarts.kitchensink_ear.entity.Passenger;
import org.slf4j.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
//import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: Alex
 * Date: 17.03.13
 * Time: 1:54
 * To change this template use File | Settings | File Templates.
 */
@Stateless
public class PassengerService implements Serializable{
    @Inject
    private Logger log;

    @Inject
    private PassengerDAO passengerDAO;

    public boolean login(LoginPassword account) {
//
        if (passengerDAO.getElement(account.getEmail()) != null) {
            Passenger passenger = passengerDAO.getElement(account.getEmail());
            if (passenger.getPassword().equals(account.getPassword())) {
//                session.setEmail(account.getEmail());
//                session.setPassword(account.getPassword());
//                session.setLogged(true);
//                session.setAdministrator(passenger.isAdministrator());
                return true;
            }
        }
        return false;
    }

    public boolean register(PassengerDTO passengerDTO) {
        log.info("------ Registering " + passengerDTO.getEmail());
        return passengerDAO.addElement(getPassengerFromPassengerDTO(passengerDTO));
    }

    private Passenger getPassengerFromPassengerDTO(PassengerDTO passengerDTO) {
        log.info("------ Taking passenger from user " + passengerDTO.getEmail());
        return new Passenger(passengerDTO.getName(), passengerDTO.getSurname(), passengerDTO.getEmail(),
                passengerDTO.getPassword(), passengerDTO.getBirthdayDate());
    }

    public boolean update(PassengerDTO passengerDTO){
        log.info("------ Editing " + passengerDTO.getEmail()+ passengerDTO.isAdministrator());
        return passengerDAO.updateElement(new Passenger(passengerDTO.getName(), passengerDTO.getSurname(), passengerDTO.getEmail(),
                passengerDTO.getPassword(), passengerDTO.getBirthdayDate(), passengerDTO.isAdministrator()));
    }

    public List<PassengerDTO> getAllPassengers(){
        log.info("------ Taking passengers ");
        List<PassengerDTO> result = new LinkedList<PassengerDTO>();
        for (Passenger passenger: passengerDAO.getAllElements())
            result.add(new PassengerDTO(passenger));
        log.info("------ Taking passengers " + result);
        return result;
    }

    public List<PassengerDTO> getPassengersByTrainNumber(String number){
        log.info("PassengerService getPassengersByTrainNumber "+ number);
        List<PassengerDTO> result = new LinkedList<PassengerDTO>();
        log.info("PassengerService getPassengersByTrainNumber "+
                passengerDAO.getPassengersByTrainNumber(number));
        for (Passenger passenger: passengerDAO.getPassengersByTrainNumber(number))
            result.add(new PassengerDTO(passenger));
        log.info("PassengerService getPassengersByTrainNumber" + result);
        return result;
    }

    public boolean isAdmin(String email) {
        log.info("PassengerService" + " isAdmin " + email);

        return passengerDAO.isAdmin(email);
    }
}
