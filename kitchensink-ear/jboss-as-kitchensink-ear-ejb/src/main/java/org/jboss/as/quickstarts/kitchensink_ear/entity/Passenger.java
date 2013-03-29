package org.jboss.as.quickstarts.kitchensink_ear.entity;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Past;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.xml.bind.annotation.XmlRootElement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
        @NamedQuery(name = "getAllPassengers", query = "Select p From Passenger p"),
        @NamedQuery(name = "getPassengerByEmail", query = "Select p from Passenger p where p.email = :email"),
        @NamedQuery(name = "isPassengerAdministrator", query = "Select p.administrator from Passenger p where p.email = :email"),
        @NamedQuery(name = "getPassengerTicketsByEmail", query = "Select p.tickets from Passenger p where p.email = :email")
})
public class Passenger extends BaseEntity {
    private static final long serialVersionUID = 1L;
    public Passenger() {
    }

    public Passenger(String name, String surname, String email, String password,
                     Date birthdayDate,
                     boolean administrator) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.birthdayDate = birthdayDate;
        this.administrator = administrator;
    }

    public Passenger(String name, String surname, String email, String password,
                     Date birthdayDate) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.birthdayDate = birthdayDate;
        this.administrator = false;
    }

    @Id
    String id;

    String getId() {
        return id;
    }
    @NotNull
    @NotEmpty (message = "This field can't be empty")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NotNull
    @NotEmpty (message = "This field can't be empty")
    private String surname;

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
    @NotNull
    private boolean administrator = false;

    public boolean isAdministrator() {
        return administrator;
    }

    public void setAdministrator(boolean administrator) {
        this.administrator = administrator;
    }

    @NotNull
    @NotEmpty (message = "This field can't be empty")
    @Email(message = "Invalid email")
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @NotNull
    @NotEmpty (message = "This field can't be empty")
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

//    @NotNull
//    @NotEmpty (message = "This field can't be empty")
    @Past
    private Date birthdayDate;

    public Date getBirthdayDate() {
        return birthdayDate;
    }

    public void setBirthdayDate(Date birthdayDate) {
        this.birthdayDate = birthdayDate;
    }

    @OneToMany(mappedBy = "passenger")
    private List<Ticket> tickets;

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    @PrePersist
    public void prepareId() {
        if (id == null) {
            id = UUID.randomUUID().toString();
        }
    }

    public boolean equals(Passenger p) {
        return (this.getName().equals(p.getName()) && this.getSurname().equals(p.getSurname())
//                && this.getBirthdayDate().equals(p.getBirthdayDate())
        );
    }

}
