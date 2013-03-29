package org.jboss.as.quickstarts.kitchensink_ear.entity;

import org.jboss.as.quickstarts.kitchensink_ear.dto.TicketDTO;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

/**
 * Created by IntelliJ IDEA.
 * User: alex
 * Date: 2/17/13
 * Time: 2:53 PM
 * To change this template use File | Settings | File Templates.
 */
@Entity

@NamedQueries({
        @NamedQuery(name = "getAllTickets", query = "SELECT ticket FROM Ticket ticket"),
        @NamedQuery(name = "getPassengerTicketOwner",
                query = "SELECT ticket FROM Ticket ticket WHERE ticket.passenger = :passenger"),
        @NamedQuery(name = "getTrainTicketOwner",
                query = "SELECT ticket FROM Ticket ticket WHERE ticket.train = :train"),
        @NamedQuery(name = "getTicketsByTrainNumber",
                query = "Select ticket from Ticket ticket where ticket.train.number = :trainNumber"),
        @NamedQuery(name = "getPassengerByTrain",
                query = "Select ticket from Ticket ticket where ticket.train = :train")
})
public class Ticket implements Serializable {
    public Ticket() {
    }

    public Ticket(int number, Passenger passenger, Train train) {
        this.number = number;
        this.passenger = passenger;
        this.train = train;
    }

    @Id
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private int number;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    private Passenger passenger;

    public Passenger getPassenger() {
        return passenger;
    }

    public void setPassenger(Passenger passenger) {
        this.passenger = passenger;
    }


    @ManyToOne(cascade = CascadeType.ALL)
    private Train train;


    public Train getTrain() {
        return train;
    }

    public void setTrain(Train train) {
        this.train = train;
    }

    @PrePersist
    public void prepareId() {
        if (id == null) {
            id = UUID.randomUUID().toString();
        }
    }

    public boolean equals(Train t) {
        return this.getId().equals(t.getId());
    }
}
