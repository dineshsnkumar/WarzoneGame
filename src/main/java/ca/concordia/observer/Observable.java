package ca.concordia.observer;

import java.util.ArrayList;
import java.util.List;

/**
 * This is the fundamental class to provide the implementation of the notification mechanism such as attaching and detaching
 * This class connects observers and observables
 * @author Solmaz
 */
public class Observable {
    /**
     * list of observers to save these objects during attaching and detaching processes
     */
    private List<Observer> l_observers=new ArrayList<Observer>();

    /**
     * This method attaches a view to the model
     * @param p_o the observer which is added to list of of observers to be notified
     */
    public void attach(Observer p_o){
        this.l_observers.add(p_o);
    }

    /**
     * This method detaches observer objects from the observer list in it
     * @param p_o is the observer to object be removed from the list of observers
     */
    public void detach(Observer p_o){
        if(!l_observers.isEmpty()){
            l_observers.remove(p_o);
        }
    }

    /**
     * This method notifies all the views attached to the model
     * @param p_o object including the information to be observed
     */
    public void notifyObservers(Observable p_o){
        for (Observer l_o:l_observers){
            l_o.update(p_o);
        }
    }
}
