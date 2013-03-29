package org.jboss.as.quickstarts.kitchensink_ear.model;

import org.jboss.as.quickstarts.kitchensink_ear.dto.SheduleDTO;

import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * User: Alex
 * Date: 24.03.13
 * Time: 21:21
 * To change this template use File | Settings | File Templates.
 */
public class SheduleModel {

    private String unique;
    private String stationName;
    private String trainNumber;
    private String date;

    public SheduleModel(String stationName, String trainNumber, String date) {
//        this.unique = UUID.randomUUID().toString();
        this.unique =stationName+trainNumber+date;
        this.stationName = stationName;
        this.trainNumber = trainNumber;
        this.date = date;
    }

    public SheduleModel() {
    }

    public SheduleModel(SheduleDTO sheduleDTO){
        this.unique = sheduleDTO.getStationName() + sheduleDTO.getTrainNumber() + sheduleDTO.getTime();
        stationName = sheduleDTO.getStationName();
        trainNumber = sheduleDTO.getTrainNumber();
        date = sheduleDTO.getTime().toString();
    }

    public String getUnique() {
        return unique;
    }

    public void setUnique(String unique) {
        this.unique = unique;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public String getTrainNumber() {
        return trainNumber;
    }

    public void setTrainNumber(String trainNumber) {
        this.trainNumber = trainNumber;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
