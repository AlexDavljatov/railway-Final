package org.jboss.as.quickstarts.kitchensink_ear.controller.admin;

import org.jboss.as.quickstarts.kitchensink_ear.dto.SheduleDTO;
import org.jboss.as.quickstarts.kitchensink_ear.dto.StationDTO;
import org.jboss.as.quickstarts.kitchensink_ear.model.SheduleModel;
import org.jboss.as.quickstarts.kitchensink_ear.model.SuitableShedule;
import org.jboss.as.quickstarts.kitchensink_ear.model.datamodel.StationDataModel;
import org.jboss.as.quickstarts.kitchensink_ear.service.SheduleService;
import org.jboss.as.quickstarts.kitchensink_ear.service.StationService;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: Alex
 * Date: 17.03.13
 * Time: 0:44
 * To change this template use File | Settings | File Templates.
 */
@Model
public class EditStationBean {
    @Inject
    private Logger log;
    @Inject
    private FacesContext facesContext;
    @Inject
    private StationService stationService;
    @Inject
    private SheduleService sheduleService;

//    private String stationName;

    private List<StationDTO> stations;
    private List<SheduleDTO> shedules;
    private LinkedList<SuitableShedule> suitableShedules;
    private StationDTO selectedStation;
    //    private List<String> stationNames;
    private StationDataModel stationDataModel;
    private String newStation;

    @PostConstruct
    private void init() {
        log.entering("EditStationBean", "init()");
        stations = new LinkedList<StationDTO>();
//        stationNames = new LinkedList<String>();
        List<StationDTO> stationDTOs = stationService.getAllStations();
        for (StationDTO stationDTO : stationDTOs) {
            stations.add(stationDTO);
//            stationNames.add(stationDTO.getName());
        }
        stationDataModel = new StationDataModel(stations);
    }

    public List<StationDTO> getStations() {
        return stations;
    }

    public StationDTO getSelectedStation() {
        return selectedStation;
    }

    public void setSelectedStation(StationDTO station) {
        this.selectedStation = station;
    }

    //TODO: get shedule by station implementation
    private void getSheduleByStation(String stationName) {
        log.info("getSheduleByStation " + stationName);
        shedules = sheduleService.viewTrainsByStation(stationName);
        suitableShedules = new LinkedList<SuitableShedule>();
        for (SheduleDTO sheduleDTO: shedules) suitableShedules.add(
                new SuitableShedule(sheduleDTO.getStationName(), sheduleDTO.getTime().toString()));
        log.info("getSheduleByStation " + suitableShedules);
    }

    public StationDataModel getStationDataModel() {
        return stationDataModel;
    }

    public void onRowSelect(SelectEvent event) {
        FacesMessage msg = new FacesMessage("Station Selected", ((StationDTO) event.getObject()).getName());
        getSheduleByStation(((StationDTO) event.getObject()).getName());
        log.info("onRowSelect " + ((StationDTO) event.getObject()).getName() + " " + shedules);
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowUnselect(SelectEvent event) {
        FacesMessage msg = new FacesMessage("Station unselected", ((StationDTO) event.getObject()).getName());

        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
//    How to add new station:


    public String getNewStation() {
        return newStation;
    }

    public void setNewStation(String newStation) {
        this.newStation = newStation;
    }

    public void addNewStation(ActionEvent actionEvent) {
        log.info("addNewStation " + newStation);
        RequestContext context = RequestContext.getCurrentInstance();
        FacesMessage msg = null;
        boolean addSuccess = false;
        StationDTO newStationDTO = new StationDTO(newStation);
        if (newStation != null) {
            if (stationService.addStation(newStationDTO)) {
                addSuccess = true;
                stations.add(newStationDTO);
                msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Station added", newStation);
            } else {
                addSuccess = false;
                msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Add station error",
                        "Such station is already registered");
            }
        } else {
            addSuccess = false;
            msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Add station error", "Name field is empty");
        }
        newStation = "";
        FacesContext.getCurrentInstance().addMessage(null, msg);
        context.addCallbackParam("addSuccess", addSuccess);
    }

    public List<SheduleDTO> getShedules() {
        return shedules;
    }

    public LinkedList<SuitableShedule> getSuitableShedules() {
        return suitableShedules;
    }
}

