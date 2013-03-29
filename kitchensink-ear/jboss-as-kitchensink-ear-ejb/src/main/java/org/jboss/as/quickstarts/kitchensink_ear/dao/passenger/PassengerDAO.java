package org.jboss.as.quickstarts.kitchensink_ear.dao.passenger;


import org.jboss.as.quickstarts.kitchensink_ear.dao.BaseDAO;
import org.jboss.as.quickstarts.kitchensink_ear.entity.Passenger;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: alex
 * Date: 3/3/13
 * Time: 10:41 PM
 * To change this template use File | Settings | File Templates.
 */
public interface PassengerDAO extends BaseDAO<Passenger> {
    boolean isAdmin(String email);

    List<Passenger> getPassengersByTrainNumber(String number);
}
