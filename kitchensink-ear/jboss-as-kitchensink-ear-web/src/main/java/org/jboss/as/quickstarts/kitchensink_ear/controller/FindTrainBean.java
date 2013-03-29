package org.jboss.as.quickstarts.kitchensink_ear.controller;

import org.jboss.as.quickstarts.kitchensink_ear.data.LoginPassword;
import org.jboss.as.quickstarts.kitchensink_ear.dto.SheduleDTO;
import org.jboss.as.quickstarts.kitchensink_ear.dto.StationDTO;
import org.jboss.as.quickstarts.kitchensink_ear.dto.TrainDTO;
import org.jboss.as.quickstarts.kitchensink_ear.model.SheduleModel;
import org.jboss.as.quickstarts.kitchensink_ear.model.TrainModel;
import org.jboss.as.quickstarts.kitchensink_ear.model.datamodel.SheduleDataModel;
import org.jboss.as.quickstarts.kitchensink_ear.service.SheduleService;
import org.jboss.as.quickstarts.kitchensink_ear.service.StationService;
import org.jboss.as.quickstarts.kitchensink_ear.service.TicketService;
import org.jboss.as.quickstarts.kitchensink_ear.service.TrainService;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Alex
 * Date: 26.03.13
 * Time: 5:35
 * To change this template use File | Settings | File Templates.
 */
@Model
public class FindTrainBean {
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(FindTrainBean.class);

    @Inject
    private FacesContext facesContext;
    @Inject
    private SheduleService sheduleService;
    @Inject
    private StationService stationService;

    private List<SheduleModel> sheduleModels;
    private List<SheduleDTO> shedules;
    private List<TrainModel> trains;
    private List<TrainDTO> trainDTOs;
    private List<StationDTO> stations;
    //    private List<String> stationNames;
    private String station1;
    private String station2;
    private Date date1;
    private Date date2;


    private boolean admin;

    @PostConstruct
    private void init() {
        log.debug("AddRoutePointBean", "init()");
        /*stations = new LinkedList<StationDTO>();
        shedules = new LinkedList<SheduleDTO>();
        trains = new LinkedList<TrainModel>();
       */
        shedules = sheduleService.getAllShedules();
        sheduleModels = new LinkedList<SheduleModel>();
        stations = stationService.getAllStations();

        /*log.info("AddRoutePointBean: init(): " + sheduleModels.toString());
        log.info("AddRoutePointBean: init(): " + stations.toString());
        log.info("AddRoutePointBean: init(): " + trains.toString());*/
    }

    public void findTrain() {
        log.info("FindTrainBean: findTrain(): " + station1 + " " + station2 + " "
        + date1 + " " + date2);


        sheduleService.findTrainsFromP2P(station1, station2, date1.getTime(), date2.getTime());
        /*if (selectStation != null && selectTrain != null &&
                selectTime != null) {
            if (sheduleService.addShedule(selectStation, selectTrain, selectTime)) {
                addSuccess = true;
                msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Shedule added", selectStation + " " + selectTrain);
            } else {
                addSuccess = false;
                msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Add shedule error",
                        "That train is already registered");
            }
        } else {
            addSuccess = false;
            msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Add train error", "Invalid field format");
        }
        FacesContext.getCurrentInstance().addMessage(null, msg);
        context.addCallbackParam("addSuccess", addSuccess);
*/
    }

    public Date getDate2() {
        return date2;
    }

    public void setDate2(Date date2) {
        this.date2 = date2;
    }

    public Date getDate1() {
        return date1;
    }

    public void setDate1(Date date1) {
        this.date1 = date1;
    }

    public String getStation2() {
        return station2;
    }

    public void setStation2(String station2) {
        this.station2 = station2;
    }

    public String getStation1() {
        return station1;
    }

    public void setStation1(String station1) {
        this.station1 = station1;
    }

    public List<StationDTO> getStations() {
        return stations;
    }

    public void setStations(List<StationDTO> stations) {
        this.stations = stations;
    }

    public List<SheduleDTO> getShedules() {
        return shedules;
    }

    public void setShedules(List<SheduleDTO> shedules) {
        this.shedules = shedules;
    }
}
