package fr.univ_lyon1.info.m1.microblog.observer;

/**
 * The Observer interface defines the contract for observer classes
 * that wish to be notified of changes in the model.
 */
public interface Observer {
   /**
    * Updates the observer with the latest information or state changes.
    */
   void update();
}
