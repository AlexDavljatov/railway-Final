package org.jboss.as.quickstarts.kitchensink_ear.service;

import org.jboss.as.quickstarts.kitchensink_ear.dao.stations.StationDAO;
import org.jboss.as.quickstarts.kitchensink_ear.data.LoginPassword;
import org.jboss.as.quickstarts.kitchensink_ear.dto.SheduleDTO;
import org.jboss.as.quickstarts.kitchensink_ear.dto.StationDTO;
import org.jboss.as.quickstarts.kitchensink_ear.dto.TrainDTO;
import org.jboss.as.quickstarts.kitchensink_ear.entity.Station;
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
 * Time: 1:54
 * To change this template use File | Settings | File Templates.
 */
@Stateless
public class StationService implements Serializable {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(StationService.class);

    @Inject
    private StationDAO stationDAO;



    //    public boolean addStation(LoginPassword lp, StationDTO station){
    public boolean addStation(StationDTO station) {

        return stationDAO.addElement(new Station(station));
    }

    public List<StationDTO> getAllStations(){
//        log.entering("TrainService", "viewAllTrains", lp.getEmail());

        List<StationDTO> result = new LinkedList<StationDTO>();
        for (Station station: stationDAO.getAllElements())
            result.add(new StationDTO(station.getName()));

        return result;
    }
}
