package hu.unideb.inf;

import javafx.beans.property.SimpleStringProperty;

public class ContactAdapter {
    private SimpleStringProperty lastName;
    private SimpleStringProperty firstName;
    private SimpleStringProperty phoneNumber;
    private SimpleStringProperty address;
    private SimpleStringProperty email;

    public ContactAdapter(Contact contact) {
        this.lastName = new SimpleStringProperty(contact.getLastName());
        this.firstName = new SimpleStringProperty(contact.getFirstName());
        this.phoneNumber = new SimpleStringProperty(contact.getPhoneNumber().toString());
        this.address = new SimpleStringProperty(contact.getAddress());
        this.email = new SimpleStringProperty(contact.getEmail());
    }

    public String getLastName() {
        return lastName.get();
    }

    public SimpleStringProperty lastNameProperty() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }

    public String getFirstName() {
        return firstName.get();
    }

    public SimpleStringProperty firstNameProperty() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    public String getPhoneNumber() {
        return phoneNumber.get();
    }

    public SimpleStringProperty phoneNumberProperty() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber.set(phoneNumber);
    }

    public String getAddress() {
        return address.get();
    }

    public SimpleStringProperty addressProperty() {
        return address;
    }

    public void setAddress(String address) {
        this.address.set(address);
    }

    public String getEmail() {
        return email.get();
    }

    public SimpleStringProperty emailProperty() {
        return email;
    }

    public void setEmail(String email) {
        this.email.set(email);
    }
}
