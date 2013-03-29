package org.jboss.as.quickstarts.kitchensink_ear.dao.shedule.impl;

import org.jboss.as.quickstarts.kitchensink_ear.dao.shedule.SheduleDAO;
import org.jboss.as.quickstarts.kitchensink_ear.entity.Shedule;
import org.jboss.as.quickstarts.kitchensink_ear.entity.Station;
import org.jboss.as.quickstarts.kitchensink_ear.entity.Train;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceUnit;
import javax.persistence.TypedQuery;
import java.util.*;
import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: alex
 * Date: 3/4/13
 * Time: 3:18 PM
 * To change this template use File | Settings | File Templates.
 */
@Stateless
public class SheduleDAOImpl implements SheduleDAO {
    @Inject
    private Logger log;
    @Inject
    EntityManager em;

    @Override
    public Shedule getElement(Object o) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean updateElement(Shedule element) {
        //To change body of implemented methods use File | Settings | File Templates.
        return false;
    }

    @Override
    public boolean addElement(Shedule element) {
        log.entering("SheduleDAOImpl", "addElement", element);
        TypedQuery<Shedule> query = em.createQuery(
                "select shedule from Shedule shedule where shedule.train = :train and shedule.station = :station and shedule.time = :time",
                Shedule.class);
        query.setParameter("station", element.getStation());
        query.setParameter("train", element.getTrain());
        query.setParameter("time", element.getTime());
        if (query.getResultList().isEmpty()) {
            em.persist(element);

            return true;
        }

        return false;
    }

    @Override
    public List<Shedule> getAllElements() {
        log.entering("SheduleDAOImpl", "getAllElements");
        TypedQuery<Shedule> query = em.createNamedQuery("getAllAnotherShedules", Shedule.class);

        return query.getResultList();
    }

    @Override
    public boolean removeElement(Shedule element) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<Shedule> getSheduleByStationName(String station) {
        log.entering("SheduleDAOImpl", "getSheduleByStationName", station);
        TypedQuery<Shedule> query = em.createNamedQuery("getTrainByStationUsingAnotherShedule", Shedule.class);
        query.setParameter("stationName", station);
//        log.debug("getAnotherSheduleByStationName " + station + " " + query.getResultList());

        return query.getResultList();
    }
    @Override
    public List<Shedule> findTrains(String station1, String station2, long time1, long time2) {
        log.entering("SheduleDAOImpl", "findTrains", new String[]{station1, station2, ""+time1, ""+time2});
        TypedQuery<Shedule> query = em.createNamedQuery("getTrainByStationUsingAnotherShedule", Shedule.class);
        List<Shedule> shedules1 = query.setParameter("stationName", station1).getResultList();
//        log.debug("shedule1" + shedules1);
        List<Shedule> shedules2 = query.setParameter("stationName", station2).getResultList();
//        log.debug("shedule1 " + shedules1);
//        log.debug("shedule2 " + shedules2);
        List<Shedule> result1 = new LinkedList<Shedule>();
        List<Shedule> result2 = new LinkedList<Shedule>();
        if (shedules1 == null || shedules2 == null) return null;
        Set<Train> result = new TreeSet<Train>();
        for (Shedule shedule : shedules1) {
//            log.debug("   " + shedule.getTime().getTime() + " " + shedule.getTime());
            if (shedule.getTime().getTime() >= time1) {
//                log.debug("***" + shedule.getTime().getTime() + " " + shedule.getTime());
                result.add(shedule.getTrain());
//                result1.add(shedule);
            }
        }
//        log.debug("result1 " + result1);
        List<Shedule> response = new LinkedList<Shedule>();
        for (Shedule shedule : shedules2) {
//            log.debug("   " + shedule.getTime().getTime() + " " + shedule.getTime());
            if (shedule.getTime().getTime() <= time2 && result.contains(shedule.getTrain())) {
//                log.debug("***" + shedule.getTime().getTime() + " " + shedule.getTime());
//                result2.add(shedule);
                response.add(shedule);
            }
        }
//        log.debug("result2 " + result2);

//        List<SheduleDTO> result = new Lin

        result1.retainAll(result2);
//        log.debug(" " + result1);
        return response;
//        return result1;
    }

    @Override
    public boolean addElement(String station, String trainNumber, long time) {
        log.entering("SheduleDAOImpl", "addElement", new String[]{station, trainNumber, ""+time});
        TypedQuery<Train> trainQuery = em.createNamedQuery("getTrainByNumber", Train.class);
        trainQuery.setParameter("trainNumber", Integer.valueOf(trainNumber));

        TypedQuery<Station> stationQuery = em.createNamedQuery("getStationByName", Station.class);
        stationQuery.setParameter("stationName", station);

        Shedule shedule = new Shedule(stationQuery.getSingleResult(), trainQuery.getSingleResult(), new Date(time));

        TypedQuery<Shedule> query = em.createQuery(
                "select shedule from Shedule shedule where shedule.train = :train and shedule.station = :station and shedule.time = :time",
                Shedule.class);
        query.setParameter("station", shedule.getStation());
        query.setParameter("train", shedule.getTrain());
        query.setParameter("time", shedule.getTime());
        if (query.getResultList().isEmpty()) {
            em.persist(shedule);
            return true;
        }
        return false;
    }
}
