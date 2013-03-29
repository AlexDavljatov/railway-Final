package org.jboss.as.quickstarts.kitchensink_ear.model;

/**
 * Created with IntelliJ IDEA.
 * User: Alex
 * Date: 26.03.13
 * Time: 1:45
 * To change this template use File | Settings | File Templates.
 */
public class SuitableShedule {
    private String train;
    private String date;

    public SuitableShedule(String train, String date) {
        this.train = train;
        this.date = date;
    }

    public String getTrain() {
        return train;
    }

    public void setTrain(String train) {
        this.train = train;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
