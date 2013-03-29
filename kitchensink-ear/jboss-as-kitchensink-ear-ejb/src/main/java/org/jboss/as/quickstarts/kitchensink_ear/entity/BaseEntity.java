package org.jboss.as.quickstarts.kitchensink_ear.entity;

import javax.persistence.MappedSuperclass;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: alex
 * Date: 2/28/13
 * Time: 1:13 AM
 * To change this template use File | Settings | File Templates.
 */
@MappedSuperclass
public class BaseEntity implements Serializable {

    /*   @PrePersist
       public void prepareId() {
           if (id == null) {
               id = UUID.randomUUID().toString();
           }
       }
   */
}