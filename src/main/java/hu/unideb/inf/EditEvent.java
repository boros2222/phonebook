package hu.unideb.inf;

import javafx.event.ActionEvent;

/**
 * Class for sending a Contact object through events.
 */
public class EditEvent extends ActionEvent {

    /** Storing a Contact object. */
    private Contact contact;

    /**
     * Constructing empty EditEvent.
     */
    public EditEvent() {
        contact = null;
    }

    /**
     * Constructing EditEvent with given Contact object.
     * @param contact Contact object.
     */
    public EditEvent(Contact contact) {
        this.contact = contact;
    }

    /**
     * Method for getting Contact object.
     * @return Contact object
     */
    public Contact getContact() {
        return contact;
    }

    /**
     * Method for setting Contact object.
     * @param contact Contact object
     */
    public void setContact(Contact contact) {
        this.contact = contact;
    }
}
