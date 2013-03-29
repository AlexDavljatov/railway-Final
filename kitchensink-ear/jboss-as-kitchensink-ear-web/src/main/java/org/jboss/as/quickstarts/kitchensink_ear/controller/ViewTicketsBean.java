package org.jboss.as.quickstarts.kitchensink_ear.controller;

import org.jboss.as.quickstarts.kitchensink_ear.data.LoginPassword;
import org.jboss.as.quickstarts.kitchensink_ear.dto.StationDTO;
import org.jboss.as.quickstarts.kitchensink_ear.dto.TicketDTO;
import org.jboss.as.quickstarts.kitchensink_ear.model.datamodel.TicketDataModel;
import org.jboss.as.quickstarts.kitchensink_ear.service.TicketService;
import org.primefaces.event.SelectEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
//import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: Alex
 * Date: 17.03.13
 * Time: 0:48
 * To change this template use File | Settings | File Templates.
 */

@Model
public class ViewTicketsBean {
//    @Inject
//    private Logger log;
    private static final Logger log = LoggerFactory.getLogger(ViewTicketsBean.class);

    @Inject
    private FacesContext facesContext;
    @Inject
    private TicketService ticketService;

//    private String stationName;

    private List<TicketDTO> tickets;
    private TicketDTO selectedTicket;
    //    private List<String> stationNames;
    private TicketDataModel ticketDataModel;
    private String newStation;
    private String ticketNumber;
    private String trainNumber;

    @PostConstruct
    private void init() {
        log.info("ViewTicketController", "init()");
        tickets = new LinkedList<TicketDTO>();

//        stationNames = new LinkedList<String>();
        LoginPassword lp = (LoginPassword) facesContext.getExternalContext().getSessionMap().get("account");
        log.info("ViewTicketController.init()", "user: ", lp.getEmail());
        List<TicketDTO> ticketDTOs = ticketService.getPassengersTickets(lp.getEmail());
        for (TicketDTO ticketDTO : ticketDTOs) {
            tickets.add(ticketDTO);
//            stationNames.add(stationDTO.getName());
        }
        ticketDataModel = new TicketDataModel(tickets);
    }

    public void onRowSelect(SelectEvent event) {
        FacesMessage msg = new FacesMessage("Ticket Selected", "Ticket No " +
                ((TicketDTO) event.getObject()).getNumber() + " Train No" +
                ((TicketDTO) event.getObject()).getTrain().getNumber());
//        getSheduleByStation(((StationDTO) event.getObject()).getName());
        ticketNumber = String.valueOf(((TicketDTO) event.getObject()).getNumber());
        trainNumber = String.valueOf(((TicketDTO) event.getObject()).getNumber());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowUnSelect(SelectEvent event) {
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



    public TicketService getTicketService() {
        return ticketService;
    }

    public void setTicketService(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    public List<TicketDTO> getTickets() {
        return tickets;
    }

    public void setTickets(List<TicketDTO> tickets) {
        this.tickets = tickets;
    }

    public TicketDTO getSelectedTicket() {
        return selectedTicket;
    }

    public void setSelectedTicket(TicketDTO selectedTicket) {
        this.selectedTicket = selectedTicket;
    }

    public TicketDataModel getTicketDataModel() {
        return ticketDataModel;
    }

    public void setTicketDataModel(TicketDataModel ticketDataModel) {
        this.ticketDataModel = ticketDataModel;
    }

    public String getTicketNumber() {
        return ticketNumber;
    }

    public void setTicketNumber(String ticketNumber) {
        this.ticketNumber = ticketNumber;
    }

    public String getTrainNumber() {
        return trainNumber;
    }

    public void setTrainNumber(String trainNumber) {
        this.trainNumber = trainNumber;
    }
}
