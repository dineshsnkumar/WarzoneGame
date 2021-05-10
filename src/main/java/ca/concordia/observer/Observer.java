package ca.concordia.observer;

/**
 * This Observer interface results in implementation of the update method for all views.
 * To explain more, this interface is used for the abstraction of the Observer
 * @author Solmaz
 */
public interface Observer {//it can be an abstract class too
    /**
     * This method queries the model object and displays its updated state
     * @param p_o this object is passed by the Observable
     */
    public void update(Observable p_o);
}
