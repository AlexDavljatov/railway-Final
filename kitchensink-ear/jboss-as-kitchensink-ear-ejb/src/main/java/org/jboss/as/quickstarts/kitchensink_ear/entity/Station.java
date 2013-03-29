package org.jboss.as.quickstarts.kitchensink_ear.entity;

import org.jboss.as.quickstarts.kitchensink_ear.dto.StationDTO;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

/**
 * Created by IntelliJ IDEA.
 * User: alex
 * Date: 2/17/13
 * Time: 2:52 PM
 * To change this template use File | Settings | File Templates.
 */
@Entity
@NamedQueries({
        @NamedQuery(name = "getStationByName", query =
                "select station from Station station where station.name = :stationName")
})
public class Station implements Serializable, Comparable<Station> {
    @Id
    private String id;
    @OneToMany(mappedBy = "station")
    private List<Shedule> shedules;

    public Station() {
    }

    public Station(String name, List<Train> trains) {
        this.name = name;
    }

    public Station(StationDTO station) {
        name = station.getName();
        shedules = null;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String name;

    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }

    public List<Shedule> getShedules() {
        return shedules;
    }

    public void setShedules(List<Shedule> shedules) {
        this.shedules = shedules;
    }

    @PrePersist
    public void prepareId() {
        if (id == null) {
            id = UUID.randomUUID().toString();
        }
    }

    @Override
    public int compareTo(Station o) {
        return name.compareTo(o.getName());
    }
}
