package org.jboss.as.quickstarts.kitchensink_ear.entity;

import org.jboss.as.quickstarts.kitchensink_ear.dto.TrainDTO;

import javax.persistence.*;
import java.util.List;
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
        @NamedQuery(name = "getTrainByNumber", query = "SELECT train from Train train where train.number = :trainNumber"),
        @NamedQuery(name = "getTrainById", query = "SELECT train from Train train where train.id = :trainId"),
        @NamedQuery(name = "getTrainTicketsByNumber", query = "Select p.tickets from Train p where p.number = :number"),
})
public class Train extends BaseEntity implements Comparable<Train> {

     public Train() {
    }

    public Train(int number, int sits_number, List<Ticket> tickets) {
        this.number = number;
        this.sits_number = sits_number;
        this.tickets = tickets;
    }

    @Id
    private String id;

    public Train(TrainDTO train) {
        number = train.getNumber();
        sits_number = train.getSitsNumber();
        tickets = null;
    }

    public String getId() {
        return id;
    }

    private int number;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    private int sits_number;

    public int getSits_number() {
        return sits_number;
    }

    public void setSits_number(int sits_number) {
        this.sits_number = sits_number;
    }

    @OneToMany(mappedBy = "train")
    private List<Ticket> tickets;

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    //
    @OneToMany(mappedBy = "train")
    private List<Shedule> shedules;

    public List<Shedule> getShedules() {
        return shedules;
    }

    public void setShedules(List<Shedule> shedules) {
        this.shedules = shedules;
    }

    @Override
    public int compareTo(Train o) {
        return (this.getNumber() - o.getNumber());
    }

    @PrePersist
    public void prepareId() {
        if (id == null) {
            id = UUID.randomUUID().toString();
        }
    }
}
