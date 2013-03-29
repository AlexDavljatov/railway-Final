package org.jboss.as.quickstarts.kitchensink_ear.rest;

import org.jboss.as.quickstarts.kitchensink_ear.dao.stations.StationDAO;
import org.jboss.as.quickstarts.kitchensink_ear.dto.StationDTO;
import org.jboss.as.quickstarts.kitchensink_ear.entity.Station;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: Alex
 * Date: 26.03.13
 * Time: 13:20
 * To change this template use File | Settings | File Templates.
 */
@ApplicationScoped
public class StationRepository {
    @Inject
    private StationDAO stationDAO;

    @Inject
    private Logger log;

    public boolean addStation(StationDTO station) {
        log.info("addStations " + station.getName());
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
