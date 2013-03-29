package org.jboss.as.quickstarts.kitchensink_ear.rest;

import org.jboss.as.quickstarts.kitchensink_ear.dao.stations.StationDAO;
import org.jboss.as.quickstarts.kitchensink_ear.other.Member;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;

/**
 * Created with IntelliJ IDEA.
 * User: Alex
 * Date: 26.03.13
 * Time: 16:53
 * To change this template use File | Settings | File Templates.
 */
@Stateless
public class StationRegistration {

    @Inject
    private StationDAO stationDAO;

    public void addStation(Member member) throws Exception {

    }
}
