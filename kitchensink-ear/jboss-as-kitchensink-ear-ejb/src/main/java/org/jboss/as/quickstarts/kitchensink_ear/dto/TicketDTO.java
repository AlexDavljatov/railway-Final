package org.jboss.as.quickstarts.kitchensink_ear.dto;

/**
 * Created with IntelliJ IDEA.
 * User: alex
 * Date: 3/3/13
 * Time: 10:30 PM
 * To change this template use File | Settings | File Templates.
 */
public class TicketDTO implements CommonModel {

    private int number;

    private PassengerDTO user;

    private TrainDTO train;

    public TicketDTO() {
    }

    public TicketDTO(int number, PassengerDTO user, TrainDTO train) {
        this.number = number;
        this.user = user;
        this.train = train;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public PassengerDTO getUser() {
        return user;
    }

    public void setUser(PassengerDTO user) {
        this.user = user;
    }

    public TrainDTO getTrain() {
        return train;
    }

    public void setTrain(TrainDTO train) {
        this.train = train;
    }
}
