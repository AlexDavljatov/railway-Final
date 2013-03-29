package org.jboss.as.quickstarts.kitchensink_ear.data;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * User: Alex
 * Date: 18.03.13
 * Time: 15:28
 * To change this template use File | Settings | File Templates.
 */
//@Entity
//User is a keyword in some SQL dialects!
//@Table(name="Users")
@XmlRootElement
public class LoginPassword implements Serializable{

    public LoginPassword() {
        email = "";
        password = "";
    }

    @NotNull
    @NotEmpty
    @Email(message = "Invalid email")
    private String email;

    @NotNull
    @Size(min = 4, max = 35, message = "Password length is between 4 and 35 characters")
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
