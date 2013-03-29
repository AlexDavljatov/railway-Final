package org.jboss.as.quickstarts.kitchensink_ear.service;

import org.jboss.as.quickstarts.kitchensink_ear.dao.trains.TrainDAO;
import org.jboss.as.quickstarts.kitchensink_ear.data.LoginPassword;
import org.jboss.as.quickstarts.kitchensink_ear.dto.TrainDTO;
import org.jboss.as.quickstarts.kitchensink_ear.entity.Train;

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
public class TrainService implements Serializable {


    @Inject
    private TrainDAO trainDAO;

//    public boolean addTrain(LoginPassword lp, TrainDTO train){
    public boolean addTrain(TrainDTO train){
//        log.entering("TrainService", "addTrain", new String[]{lp.getEmail(), ""+train.getNumber()});

        return trainDAO.addElement(new Train(train));
    }

//    public List<TrainDTO> viewAllTrains(LoginPassword lp){

    public List<TrainDTO> viewAllTrains(){
//        log.entering("TrainService", "viewAllTrains", lp.getEmail());

        List<TrainDTO> result = new LinkedList<TrainDTO>();
        for (Train train: trainDAO.getAllElements())
            result.add(new TrainDTO(train.getNumber(), train.getSits_number()));

        return result;
    }
}
