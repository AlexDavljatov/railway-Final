package org.jboss.as.quickstarts.kitchensink_ear.dao.trains.impl;

import org.jboss.as.quickstarts.kitchensink_ear.dao.trains.TrainDAO;
import org.jboss.as.quickstarts.kitchensink_ear.entity.Train;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: alex
 * Date: 3/2/13
 * Time: 2:33 PM
 * To change this template use File | Settings | File Templates.
 */
@Stateless
public class TrainDAOImpl implements TrainDAO {

    @Inject
    EntityManager em;
    @Override
    public Train getElement(Object o) {

        String s = (String) o;
        TypedQuery<Train> query = em.createNamedQuery("getTrainByNumber", Train.class);
        query.setParameter("trainNumber", s);
        return query.getSingleResult();
    }
    @Override
    public Train getElementById(String s) {

        TypedQuery<Train> query = em.createNamedQuery("getTrainById", Train.class);
        query.setParameter("trainId", s);
        return query.getSingleResult();
    }
    @Override
    public boolean addElement(Train element) {

        TypedQuery<Train> query = em.createNamedQuery("getTrainByNumber", Train.class);
        query.setParameter("trainNumber", element.getNumber());
        if (query.getResultList().isEmpty()) {
            em.persist(element);
            return true;
        }
        return false;
    }
    @Override
    public boolean updateElement(Train element) {
        return false;
    }
    @Override
    public List<Train> getAllElements() {
        Query query = em.createQuery("Select train from Train train");

//        log.debug("getAllElements() results" + query.getResultList());

        return (List<Train>) query.getResultList();
    }
    @Override
    public boolean removeElement(Train element) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
