package org.jboss.as.quickstarts.kitchensink_ear.dto;

/**
 * Created with IntelliJ IDEA.
 * User: alex
 * Date: 2/27/13
 * Time: 12:12 AM
 * To change this template use File | Settings | File Templates.
 */

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.jboss.as.quickstarts.kitchensink_ear.entity.Passenger;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Data object for a User
 */

public class PassengerDTO implements CommonModel {

    @NotNull
    @NotEmpty (message = "'Email' field can't be empty")
    @Email(message = "Invalid email")
    private String email;
    @NotNull
    @NotEmpty(message = "'Name' field can't be empty")
    private String name;
    @NotNull
    @NotEmpty(message = "'Surname' field can't be empty")
    private String surname;
    @NotNull
    @NotEmpty(message = "'Password' field can't be empty")
    private String password;
//    @NotNull(message = "'Date' field can't be empty")
//    @NotEmpty(message = "'Date' field can't be empty")
    @Past
    private Date birthdayDate;

    //TODO: enumerator
    @NotNull
    private boolean administrator;

    public PassengerDTO(String name, String surname, String email, String password, Date birthdayDate, boolean administrator) {
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.birthdayDate = birthdayDate;
        this.administrator = administrator;
    }

    public PassengerDTO() {
        email = "";
        name = "";
        surname = "";
        password = "";
        birthdayDate = null;
        administrator = false;
    }

    public PassengerDTO(String name, String surname, String email, String password, Date birthdayDate) {
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.birthdayDate = birthdayDate;
        this.administrator = false;
    }

    public PassengerDTO(Passenger passenger){
        email = passenger.getEmail();
        name = passenger.getName();
        surname = passenger.getSurname();
        password = passenger.getPassword();
//        birthdayDate = passenger.getBirthDayDate();
        //TODO: another Date
        birthdayDate = new Date(System.currentTimeMillis());
        administrator = passenger.isAdministrator();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Date getBirthdayDate() {
        return birthdayDate;
    }

    public void setBirthdayDate(Date birthdayDate) {
        this.birthdayDate = birthdayDate;
    }

    public boolean isAdministrator() {
        return administrator;
    }

    public void setAdministrator(boolean administrator) {
        this.administrator = administrator;
    }
}