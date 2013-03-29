package org.jboss.as.quickstarts.kitchensink_ear.dto;

/**
 * Created with IntelliJ IDEA.
 * User: alex
 * Date: 3/2/13
 * Time: 10:13 PM
 * To change this template use File | Settings | File Templates.
 */
public class StationDTO implements CommonModel {

    private String name;

    public StationDTO() {
    }

    public StationDTO(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
