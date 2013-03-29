package org.jboss.as.quickstarts.kitchensink_ear.dao.shedule;


import org.jboss.as.quickstarts.kitchensink_ear.dao.BaseDAO;
import org.jboss.as.quickstarts.kitchensink_ear.entity.Shedule;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: alex
 * Date: 3/4/13
 * Time: 3:47 PM
 * To change this template use File | Settings | File Templates.
 */
public interface SheduleDAO extends BaseDAO<Shedule> {
    List<Shedule> getSheduleByStationName(String station);

    List<Shedule> findTrains(String station1, String station2, long time1, long time2);

    boolean addElement(String station, String trainNumber, long time);
}
