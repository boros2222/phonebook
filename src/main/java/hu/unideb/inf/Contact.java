package hu.unideb.inf;

import org.apache.commons.validator.routines.EmailValidator;

public class Contact {
    private String lastName;
    private String firstName;
    private PhoneNumber phoneNumber;
    private String address;
    private String email;

    public Contact() {
        this.lastName = "";
        this.firstName = "";
        this.phoneNumber = PhoneNumber.of("00000000000");
        this.address = "";
        this.email = "";
    }

    public Contact(String lastName, String firstName, PhoneNumber phoneNumber, String address, String email) throws IllegalArgumentException {
        this.setLastName(lastName);
        this.setFirstName(firstName);
        this.setEmail(email);

        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) throws IllegalArgumentException {
        if(!lastName.equals(""))
            this.lastName = lastName;
        else
            throw new IllegalArgumentException("A név nem lehet üres!");
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) throws IllegalArgumentException {
        if(!firstName.equals(""))
            this.firstName = firstName;
        else
            throw new IllegalArgumentException("A név nem lehet üres!");
    }

    public PhoneNumber getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(PhoneNumber phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) throws IllegalArgumentException {
        if(EmailValidator.getInstance().isValid(email))
            this.email = email;
        else
            throw new IllegalArgumentException("Hibás email cím!");
    }
}
