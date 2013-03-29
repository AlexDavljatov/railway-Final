package org.jboss.as.quickstarts.kitchensink_ear.dao.trains;


import org.jboss.as.quickstarts.kitchensink_ear.dao.BaseDAO;
import org.jboss.as.quickstarts.kitchensink_ear.entity.Train;

/**
 * Created with IntelliJ IDEA.
 * User: alex
 * Date: 3/2/13
 * Time: 2:42 PM
 * To change this template use File | Settings | File Templates.
 */
public interface TrainDAO extends BaseDAO<Train> {

    Train getElementById(String id);
}
