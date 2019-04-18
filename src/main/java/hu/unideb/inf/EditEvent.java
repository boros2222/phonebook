package hu.unideb.inf;

import javafx.event.ActionEvent;

public class EditEvent extends ActionEvent {
    private Contact contact;

    public EditEvent() {
        contact = null;
    }

    public EditEvent(Contact contact) {
        this.contact = contact;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }
}
