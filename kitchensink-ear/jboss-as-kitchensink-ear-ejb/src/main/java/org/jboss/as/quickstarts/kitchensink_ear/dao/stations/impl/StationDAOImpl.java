package org.jboss.as.quickstarts.kitchensink_ear.dao.stations.impl;


import org.jboss.as.quickstarts.kitchensink_ear.dao.stations.StationDAO;
import org.jboss.as.quickstarts.kitchensink_ear.entity.Station;
import org.slf4j.LoggerFactory;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: alex
 * Date: 3/2/13
 * Time: 10:00 PM
 * To change this template use File | Settings | File Templates.
 */
@Stateless
public class StationDAOImpl implements StationDAO {
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(StationDAOImpl.class);

    @Inject
    EntityManager em;

    @Override
    public Station getElement(Object o) {

        TypedQuery<Station> query = em.createQuery("Select station from Station station where station.name = :name",
                Station.class);
        query.setParameter("name", String.valueOf(o));
        return query.getSingleResult();
    }

    @Override
    public boolean addElement(Station element) {

        TypedQuery<Station> query = em.createNamedQuery("getStationByName", Station.class);
        query.setParameter("stationName", element.getName());
        if (query.getResultList().isEmpty()) {
            em.persist(element);
            return true;
        }
        return false;
    }

    @Override
    public boolean updateElement(Station element) {
        return false;
    }

    @Override
    public List<Station> getAllElements() {

        Query query = em.createQuery("Select station from Station station");
        return (List<Station>) query.getResultList();
    }

    @Override
    public boolean removeElement(Station element) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
