package org.jboss.as.quickstarts.kitchensink_ear.controller.admin;

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
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Model;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Alex
 * Date: 17.03.13
 * Time: 0:45
 * To change this template use File | Settings | File Templates.
 */
@Named
//@ConversationScoped
@SessionScoped
public class AddRoutePointBean implements Serializable{
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(AddRoutePointBean.class);

    @Inject
    private FacesContext facesContext;
    @Inject
    private SheduleService sheduleService;
    @Inject
    private TrainService trainService;
    @Inject
    private StationService stationService;
    //    private String stationName;
    @Inject
    private TicketService ticketService;

//    @Inject
//    private Conversation conversation;

    private List<StationDTO> stations;

    private List<SheduleModel> sheduleModels;
    private List<SheduleDTO> shedules;
    private List<TrainModel> trains;
    //    private List<String> stationNames;
    private String selectStation;
    private String selectTrain;
    private Date selectTime;

    private SheduleModel selectedSheduleModel;
    private SheduleDataModel sheduleDataModel;

    private String ticketStation;
    private String ticketTrain;
    private String ticketDate;
    private boolean admin;

    @PostConstruct
    private void init() {
        log.debug("AddRoutePointBean", "init()");
        /*stations = new LinkedList<StationDTO>();
        shedules = new LinkedList<SheduleDTO>();
        trains = new LinkedList<TrainModel>();
       */
        stations = stationService.getAllStations();
        shedules = sheduleService.getAllShedules();
        sheduleModels = new LinkedList<SheduleModel>();
        for (SheduleDTO sheduleDTO : shedules) sheduleModels.add(new SheduleModel(sheduleDTO));
        sheduleDataModel = new SheduleDataModel(sheduleModels);

        List<TrainDTO> trainDTOs = trainService.viewAllTrains();
        trains = new LinkedList<TrainModel>();
        for (TrainDTO trainDTO : trainDTOs) trains.add(new TrainModel(trainDTO));
//        start();
        /*log.info("AddRoutePointBean: init(): " + sheduleModels.toString());
        log.info("AddRoutePointBean: init(): " + stations.toString());
        log.info("AddRoutePointBean: init(): " + trains.toString());*/
    }

    public void addRoutePoint() {
        log.info("AddRoutePointBean: addRoutePoint(): ");
        log.info("AddRoutePointBean: addRoutePoint(): " + selectStation);
        log.info("AddRoutePointBean: addRoutePoint(): " + selectTrain);
        log.info("AddRoutePointBean: addRoutePoint(): " + selectTime);

        RequestContext context = RequestContext.getCurrentInstance();
        FacesMessage msg = null;
        boolean addSuccess = false;

        if (selectStation != null && selectTrain != null &&
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

    public List<TrainModel> getTrains() {
        return trains;
    }

    public void setTrains(List<TrainModel> trains) {
        this.trains = trains;
    }

    public String getSelectStation() {
        return selectStation;
    }

    public void setSelectStation(String selectStation) {
        this.selectStation = selectStation;
    }

    public String getSelectTrain() {
        return selectTrain;
    }

    public void setSelectTrain(String selectTrain) {
        this.selectTrain = selectTrain;
    }

    public Date getSelectTime() {
        return selectTime;
    }

    public void setSelectTime(Date selectTime) {
        this.selectTime = selectTime;
    }

    public SheduleModel getSelectedSheduleModel() {
        return selectedSheduleModel;
    }

    public void setSelectedSheduleModel(SheduleModel selectedSheduleModel) {
        this.selectedSheduleModel = selectedSheduleModel;
    }

    public List<SheduleModel> getSheduleModels() {
        return sheduleModels;
    }

    public void setSheduleModels(List<SheduleModel> sheduleModels) {
        this.sheduleModels = sheduleModels;
    }

    public StationService getStationService() {
        return stationService;
    }

    public void setStationService(StationService stationService) {
        this.stationService = stationService;
    }

    public TrainService getTrainService() {
        return trainService;
    }

    public void setTrainService(TrainService trainService) {
        this.trainService = trainService;
    }

    public SheduleService getSheduleService() {
        return sheduleService;
    }

    public void setSheduleService(SheduleService sheduleService) {
        this.sheduleService = sheduleService;
    }

    public void onRowSelect(SelectEvent event) {
        log.info("onRowSelect " + ((SheduleModel) event.getObject()).getStationName() +
                " " + ((SheduleModel) event.getObject()).getTrainNumber());
        ticketStation = ((SheduleModel) event.getObject()).getStationName();
        ticketTrain = ((SheduleModel) event.getObject()).getTrainNumber();
        ticketDate = ((SheduleModel) event.getObject()).getDate();

        log.info("onRowSelect " + ticketDate + " " + ticketTrain + " " + ticketDate);
//        facesContext.getExternalContext().getSessionMap().put("ticketStation", ticketStation);
//        facesContext.getExternalContext().getSessionMap().put("ticketTrain", ticketTrain);

        FacesMessage msg = new FacesMessage("Shedule selected", "" + ((SheduleModel) event.getObject()).getStationName() +
                " " + ((SheduleModel) event.getObject()).getTrainNumber());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowUnselect(SelectEvent event) {
        FacesMessage msg = new FacesMessage("Shedule unselected", "" +
                ((SheduleModel) event.getObject()).getStationName() + " " +
                ((SheduleModel) event.getObject()).getTrainNumber());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public SheduleDataModel getSheduleDataModel() {
        return sheduleDataModel;
    }

    public void setSheduleDataModel(SheduleDataModel sheduleDataModel) {
        this.sheduleDataModel = sheduleDataModel;
    }

    public void buyTicket() {
        LoginPassword lp = (LoginPassword) facesContext.getExternalContext().getSessionMap().get("account");
        ticketStation = (String)facesContext.getExternalContext().getSessionMap().get("ticketStation");
        ticketTrain = (String)facesContext.getExternalContext().getSessionMap().get("ticketTrain");
        log.info("buyTicket() " + lp.getEmail() + " " + ticketTrain + " " + ticketStation);
        boolean addSuccess = false;
        RequestContext context = RequestContext.getCurrentInstance();
        FacesMessage msg = null;
        if (ticketService.buyTicket(lp.getEmail(), ticketTrain, ticketStation)) {
            addSuccess = true;
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Ticket purchase", selectStation + " " + selectTrain);
        } else {
            addSuccess = false;
            msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Ticket purchase error",
                    "No available tickets at the moment");
        }

        FacesContext.getCurrentInstance().addMessage(null, msg);
        context.addCallbackParam("addSuccess", addSuccess);
        facesContext.getExternalContext().getSessionMap().remove("ticketStation");
        facesContext.getExternalContext().getSessionMap().remove("ticketTrain");
    }

    public String getTicketStation() {
        return ticketStation;
    }

    public void setTicketStation(String ticketStation) {
        this.ticketStation = ticketStation;
    }

    public String getTicketTrain() {
        return ticketTrain;
    }

    public void setTicketTrain(String ticketTrain) {
        this.ticketTrain = ticketTrain;
    }

    public String getTicketDate() {
        return ticketDate;
    }

    public void setTicketDate(String ticketDate) {
        this.ticketDate = ticketDate;
    }

    public void check(){
        log.info("really checked!");
        LoginPassword lp = (LoginPassword) facesContext.getExternalContext().getSessionMap().get("account");
//        ticketStation = (String)facesContext.getExternalContext().getSessionMap().get("ticketStation");
//        ticketTrain = (String)facesContext.getExternalContext().getSessionMap().get("ticketTrain");
        log.info("buyTicket() " + lp.getEmail() + " " + ticketTrain + " " + ticketStation);
        boolean addSuccess = false;
        RequestContext context = RequestContext.getCurrentInstance();
        FacesMessage msg = null;
        if (ticketService.buyTicket(lp.getEmail(), ticketTrain, ticketStation)) {
            addSuccess = true;
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Ticket purchase", selectStation + " " + selectTrain);
        } else {
            addSuccess = false;
            msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Ticket purchase error",
                    "No available tickets at the moment");
        }
        FacesContext.getCurrentInstance().addMessage(null, msg);
        context.addCallbackParam("addSuccess", addSuccess);
        facesContext.getExternalContext().getSessionMap().remove("ticketStation");
        facesContext.getExternalContext().getSessionMap().remove("ticketTrain");
    }

//    public void start(){
//        conversation.begin();
//    }
//    public void stop(){
//        conversation.end();
//    }
}