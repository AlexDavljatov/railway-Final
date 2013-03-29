package org.jboss.as.quickstarts.kitchensink_ear.controller.admin;

import org.jboss.as.quickstarts.kitchensink_ear.dto.PassengerDTO;
import org.jboss.as.quickstarts.kitchensink_ear.dto.TrainDTO;
import org.jboss.as.quickstarts.kitchensink_ear.model.datamodel.TrainDataModel;
import org.jboss.as.quickstarts.kitchensink_ear.model.TrainModel;
import org.jboss.as.quickstarts.kitchensink_ear.service.PassengerService;
import org.jboss.as.quickstarts.kitchensink_ear.service.TrainService;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Alex
 * Date: 17.03.13
 * Time: 0:44
 * To change this template use File | Settings | File Templates.
 */
@Model
/*@Named
@ConversationScoped*/
public class EditTrainBean implements Serializable {
  /*  @Inject
    private Logger log;*/
    @Inject
    private FacesContext facesContext;
    @Inject
    private TrainService trainService;
    @Inject
    private PassengerService passengerService;

    @Inject
    private Conversation conversation;

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(EditTrainBean.class);

    private List<TrainModel> trains;
    private TrainModel selectedTrain;
    private TrainDataModel trainDataModel;
    private List<PassengerDTO> passengers;
    private String newTrainNumber;
    private String newTrainSitsNumber;
    private LinkedList<String> passEmails;

    @PostConstruct
    private void init() {
//        conversation.begin();
        log.debug("EditTrainBean", "init()");
        List<TrainDTO> trainDTOs = trainService.viewAllTrains();
        trains = new LinkedList<TrainModel>();
        for (TrainDTO trainDTO : trainDTOs) trains.add(new TrainModel(trainDTO));
        trainDataModel = new TrainDataModel(trains);
        log.debug("EditTrainBean", "init()", trains);
        passEmails = new LinkedList<String>();
        log.info("EditTrainBean " );


    }

    @Produces
    @Named
    public List<TrainModel> getTrains() {
        log.debug("EditTrainBean", "getTrains");
        return trains;
    }

    public TrainModel getSelectedTrain() {
        return selectedTrain;
    }

    public void setSelectedTrain(TrainModel selectedTrain) {
        log.debug("EditTrainBean", "setSelectedTrain", selectedTrain.getNumber());
        this.selectedTrain = selectedTrain;
    }

    public TrainDataModel getTrainDataModel() {
        return trainDataModel;
    }

    private void getPassengersByTrainNumber(String trainNumber) {
        log.info("getPassengersByTrainNumber" + trainNumber);
        passengers = passengerService.getPassengersByTrainNumber(trainNumber);
    }

    public void onRowSelect(SelectEvent event) {
        log.info("onRowSelect " + ((TrainModel) event.getObject()).getNumber());
        log.info("getPassengersByTrainNumber" + ((TrainModel) event.getObject()).getNumber());
        passengers = passengerService.getPassengersByTrainNumber(((TrainModel) event.getObject()).getNumber());
        log.info("getPassengersByTrainNumber" + passengers);
        passEmails = new LinkedList<String>();
        for (PassengerDTO p: passengers) passEmails.add(p.getEmail());
        log.info("getPassengersByTrainNumber" + passEmails);
        FacesMessage msg = new FacesMessage("Train selected", ""+((TrainModel) event.getObject()).getNumber());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowUnselect(SelectEvent event) {
        FacesMessage msg = new FacesMessage("Train unselected", ((TrainModel) event.getObject()).getNumber());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public List<PassengerDTO> getPassengers() {
        return passengers;
    }

    public void setPassengers(List<PassengerDTO> passengers) {
        this.passengers = passengers;
    }

    public String getNewTrainNumber() {
        return newTrainNumber;
    }

    public void setNewTrainNumber(String newTrainNumber) {
        this.newTrainNumber = newTrainNumber;
    }

    public String getNewTrainSitsNumber() {
        return newTrainSitsNumber;
    }

    public void setNewTrainSitsNumber(String newTrainSitsNumber) {
        this.newTrainSitsNumber = newTrainSitsNumber;
    }

    public void addNewTrain(ActionEvent actionEvent) {
        log.info("addNewStation " + newTrainNumber);
        RequestContext context = RequestContext.getCurrentInstance();
        FacesMessage msg = null;
        boolean addSuccess = false;
        TrainModel newTrainModel = new TrainModel(newTrainNumber, newTrainSitsNumber);
        if (newTrainNumber != null && newTrainSitsNumber != null &&
                isNumeric(newTrainNumber) && isNumeric(newTrainSitsNumber)) {
            if (trainService.addTrain(new TrainDTO(Integer.valueOf(newTrainNumber),
                    Integer.valueOf(newTrainSitsNumber)))) {
                addSuccess = true;

                trains.add(newTrainModel);
                msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Train added", newTrainNumber);
            } else {
                addSuccess = false;
                msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Add station error",
                        "That train is already registered");
            }
        } else {
            addSuccess = false;
            msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Add train error", "Invalid field format");
        }
        newTrainNumber = "";
        newTrainSitsNumber = "";
        FacesContext.getCurrentInstance().addMessage(null, msg);
        context.addCallbackParam("addSuccess", addSuccess);
    }

    private boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public List<String> getPassEmails() {
        return passEmails;
    }
}
