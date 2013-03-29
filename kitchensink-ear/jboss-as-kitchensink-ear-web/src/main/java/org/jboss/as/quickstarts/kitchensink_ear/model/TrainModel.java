package org.jboss.as.quickstarts.kitchensink_ear.model;

import org.jboss.as.quickstarts.kitchensink_ear.dto.TrainDTO;

/**
 * Created with IntelliJ IDEA.
 * User: Alex
 * Date: 22.03.13
 * Time: 18:18
 * To change this template use File | Settings | File Templates.
 */
public class TrainModel {
    private String number;

    private String sitsNumber;

    public TrainModel() {
    }

    public TrainModel(String number, String sitsNumber) {
        this.number = number;
        this.sitsNumber = sitsNumber;
    }

    public TrainModel(TrainDTO trainDTO){
        number = String.valueOf(trainDTO.getNumber());
        sitsNumber = String.valueOf(trainDTO.getSitsNumber());
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getSitsNumber() {
        return sitsNumber;
    }

    public void setSitsNumber(String sitsNumber) {
        this.sitsNumber = sitsNumber;
    }
}
