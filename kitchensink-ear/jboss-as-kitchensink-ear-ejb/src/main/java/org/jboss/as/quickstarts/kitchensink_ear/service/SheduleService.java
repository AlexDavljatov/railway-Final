package org.jboss.as.quickstarts.kitchensink_ear.service;

import org.jboss.as.quickstarts.kitchensink_ear.dao.shedule.SheduleDAO;
import org.jboss.as.quickstarts.kitchensink_ear.data.LoginPassword;
import org.jboss.as.quickstarts.kitchensink_ear.dto.SheduleDTO;
import org.jboss.as.quickstarts.kitchensink_ear.dto.TrainDTO;
import org.jboss.as.quickstarts.kitchensink_ear.entity.Shedule;
import org.jboss.as.quickstarts.kitchensink_ear.entity.Train;
import org.slf4j.LoggerFactory;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.*;
import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: Alex
 * Date: 17.03.13
 * Time: 1:55
 * To change this template use File | Settings | File Templates.
 */
@Stateless
public class SheduleService implements Serializable {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(SheduleService.class);
    @Inject
    private SheduleDAO sheduleDAO;

    //TODO: routes
//    public boolean addShedule(LoginPassword lp, String station, String train, Date date){
    public boolean addShedule(String station, String train, Date date) {
//        log.entering("SheduleService", "addShedule", new String[]{lp.getEmail(), station, train, date.toString()});

        return sheduleDAO.addElement(station, train, date.getTime());
    }

    public List<SheduleDTO> viewTrainsByStation(String station) {
        List<SheduleDTO> result = new LinkedList<SheduleDTO>();
        for (Shedule shedule : sheduleDAO.getSheduleByStationName(station))
            result.add(new SheduleDTO(shedule.getStation().getName(), "" + shedule.getTrain().getNumber(), shedule.getTime()));
        return result;
    }

    //TODO: Dejkstra
    //TODO: implementation!
    public List<TrainDTO> findTrainsFromP2P(String st1, String st2, long t1, long t2) {
        List<TrainDTO> result = new ArrayList<TrainDTO>();
        log.info("findTrainsFromP2P " + st1 + " " + st2 + " " + t1 + " " + t2);
        List<Shedule> shedules = sheduleDAO.getAllElements();
        List<Shedule> tempShedules = new LinkedList<Shedule>();
        log.info("findTrainsFromP2P " + shedules);
        Map<Integer, Boolean> removed = new TreeMap<Integer, Boolean>();
        Collections.sort(shedules);


        for (Shedule shedule : shedules) {
            log.info("**remove" + removed.get(Integer.valueOf(777)));
            log.info("**contains" + removed.containsKey(Integer.valueOf(777)));
            log.info("**" + shedule.getTrain().getNumber() + " " + shedule + " " +
                    shedule.getStation() + " " + shedule.getTime().getTime());
            if ((shedule.getTime().getTime() < t1)
                    || (shedule.getTime().getTime() > t2) ||
                    (removed.containsKey(777) && removed.get(Integer.valueOf(777)))) {

                log.info("*" + shedule.getTrain().getNumber() + " " + shedule + " " +
                        shedule.getStation() + " " + shedule.getTime().getTime());

                removed.put(shedule.getTrain().getNumber(), Boolean.TRUE);
            } else {
                tempShedules.add(shedule);
            }
        }
        log.info("*" + removed);
        shedules = tempShedules;
        log.info("" + tempShedules);
        Collections.sort(shedules);

//        for (Shedule shedule: shedules) log.info(" " + shedule.getTrain().getNumber() + " " + shedule.getTime());

        Map<String, LinkedList<Pair>> graf = new TreeMap<String, LinkedList<Pair>>();
        Map<String, Long> answer = new TreeMap<String, Long>();
        Map<String, String> ancestor = new TreeMap<String, String>();
        TreeSet<String> was = new TreeSet<String>();
        Shedule cur = shedules.get(0);
        ancestor.put(st1, "");
        for (int i = 0; i < shedules.size(); i++) {

            Shedule shedule = shedules.get(i);

            if (!answer.containsKey(cur.getStation().getName())) answer.put(cur.getStation().getName(), Long.MAX_VALUE);
            if (!graf.containsKey(cur.getStation().getName()))
                graf.put(cur.getStation().getName(), new LinkedList<Pair>());

            graf.get(cur.getStation().getName()).add(new Pair(shedule.getStation().getName(),
                    shedule.getTime().getTime()));
            cur = shedule;


        }

        for (String station : graf.keySet()) {
            log.info(station + " : ");
            for (Pair p : graf.get(station)) {
                log.info("          " + p.getStation() + " " + p.getTime());
            }
        }
        log.info("answer before: " + answer);
        answer.put(st1, t1);
        log.info("findTrainsFromP2P " + st1 + " " + st2 + " " + t1 + " " + t2);

        for (int i = 0; i < graf.size(); i++) {
            String min = "";
            for (String s : answer.keySet())
                if (!was.contains(s) && (min.equals("") || (answer.get(min) > answer.get(s))))
                    min = s;
            log.info(min + " " + new Date(answer.get(min)).toString());
            if (answer.get(min) == Long.MAX_VALUE) break;
            was.add(min);
            log.info("min: " + min);
            for (Pair pair : graf.get(min)) {
                if (pair.getTime() < answer.get(pair.getStation())) {
                    answer.put(pair.getStation(), pair.getTime());
                    ancestor.put(pair.getStation(), min);
                }
            }
        }

        log.info("answer after: " + answer);
        log.info("ancestors: " + ancestor);
        return result;
    }

    public List<SheduleDTO> getAllShedules() {
        log.info("SheduleService: getAllShedules()");
        List<SheduleDTO> result = new LinkedList<SheduleDTO>();
        for (Shedule shedule : sheduleDAO.getAllElements())
            result.add(new SheduleDTO("" + shedule.getTrain().getNumber(), shedule.getStation().getName(), shedule.getTime()));
//        log.info("SheduleService: getAllShedules()" + result);
        return result;
    }
}

class Pair {
    String station;
    String trainNumber;
    long time;

    Pair(String station, long time) {
        this.station = station;
        this.time = time;
    }

    Pair(String station, String trainNumber, long time) {
        this.station = station;
        this.trainNumber = trainNumber;
        this.time = time;
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getTrainNumber() {
        return trainNumber;
    }

    public void setTrainNumber(String trainNumber) {
        this.trainNumber = trainNumber;
    }
}
