package org.jboss.as.quickstarts.kitchensink_ear.dto;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: alex
 * Date: 3/3/13
 * Time: 4:40 AM
 * To change this template use File | Settings | File Templates.
 */
public class SheduleDTO implements CommonModel{

    public SheduleDTO() {
    }

    public SheduleDTO(String trainNumber, String stationName, Date time) {
        this.trainNumber = trainNumber;
        this.stationName= stationName;
        this.time = time;
    }

    private String trainNumber;
    private String stationName;
    private Date time;

    public String getTrainNumber() {
        return trainNumber;
    }

    public void setTrainNumber(String trainNumber) {
        this.trainNumber = trainNumber;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
