package org.jboss.as.quickstarts.kitchensink_ear.dao;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: alex
 * Date: 3/2/13
 * Time: 2:40 PM
 * To change this template use File | Settings | File Templates.
 */
public interface BaseDAO<T> {

    public T getElement(Object o);

    public boolean addElement(T element);

    public boolean updateElement(T element);

    public List<T> getAllElements();

    public boolean removeElement(T element);

}
