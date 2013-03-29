package org.jboss.as.quickstarts.kitchensink_ear.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * User: alex
 * Date: 3/4/13
 * Time: 3:13 PM
 * To change this template use File | Settings | File Templates.
 */
@Entity
@NamedQueries({
        @NamedQuery(name="getAllAnotherShedules",
                query = "Select shedule from Shedule shedule"),
        @NamedQuery(name="getTrainByStationUsingAnotherShedule",
                query = "SELECT shedule from Shedule shedule where shedule.station.name = :stationName"),
        @NamedQuery(name="findTrainByTimeBoundaries",
                query = "select shedule.train from Shedule shedule where (shedule.time >= :lowBoundary) and (shedule.time <= :highBoundary)"),
        @NamedQuery(name="getAnotherShedulerByTrainNumber",
                query = "select shedule from Shedule  shedule where shedule.train.number = :trainNumber"),
        @NamedQuery(name="getAnotherShedulerByStationAndTrain",
                query = "select shedule from Shedule shedule where (shedule.train.number= :trainNumber and shedule.station.name= :stationName)")
})
public class Shedule implements Serializable, Comparable<Shedule> {


    public Shedule() {
    }

    public Shedule(Station station, Train train, Date time) {
        this.train = train;
        this.station = station;
        this.time = time;
    }

    @Id
    private String id;

    public String getId() {
        return id;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    private Train train;

    public Train getTrain() {
        return train;
    }

    public void setTrain(Train train) {
        this.train = train;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    private Station station;

    public Station getStation() {
        return station;
    }

    public void setStation(Station station) {
        this.station = station;
    }

    private Date time;

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    @PrePersist
    public void prepareId() {
        if (id == null) {
            id = UUID.randomUUID().toString();
        }
    }

    @Override
    public int compareTo(Shedule o) {
        if (train.getNumber() != o.getTrain().getNumber()) return (train.getNumber() - o.getTrain().getNumber());
        return time.compareTo(o.getTime());
    }
}
