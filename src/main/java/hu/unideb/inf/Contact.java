package hu.unideb.inf;

import org.apache.commons.validator.routines.EmailValidator;
import java.util.Objects;

/**
 * Class for storing a contact.
 */
public class Contact {

    /** Lastname. */
    private String lastName;

    /** Firstname. */
    private String firstName;

    /** Phone number. */
    private PhoneNumber phoneNumber;

    /** Address. */
    private String address;

    /** Email address. */
    private String email;

    /**
     * Constructing empty Contact object.
     */
    public Contact() {
        this.lastName = "";
        this.firstName = "";
        this.phoneNumber = PhoneNumber.of("00000000000");
        this.address = "";
        this.email = "";
    }

    /**
     * Constructing Contact object with given data.
     * @param lastName Lastname
     * @param firstName Firstname
     * @param phoneNumber Phone number
     * @param address Address
     * @param email Email address
     * @throws IllegalArgumentException when error happens.
     * See: {@link #setFirstName(String)}, {@link #setLastName(String)}, {@link #setEmail(String)}
     */
    public Contact(String lastName, String firstName, PhoneNumber phoneNumber, String address, String email) throws IllegalArgumentException {
        this.setLastName(lastName);
        this.setFirstName(firstName);
        this.setEmail(email);

        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    /**
     * Method for getting lastName variable.
     * @return lastName variable
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Method for setting lastName variable.
     * @param lastName what we want to set the lastName variable to
     * @throws IllegalArgumentException when {@code lastName} is empty
     */
    public void setLastName(String lastName) throws IllegalArgumentException {
        if(!lastName.equals(""))
            this.lastName = lastName;
        else
            throw new IllegalArgumentException("A név nem lehet üres!");
    }

    /**
     * Method for getting firstName variable.
     * @return firstName variable
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Method for setting firstName variable.
     * @param firstName what we want to set the firstName variable to
     * @throws IllegalArgumentException when {@code firstName} is empty
     */
    public void setFirstName(String firstName) throws IllegalArgumentException {
        if(!firstName.equals(""))
            this.firstName = firstName;
        else
            throw new IllegalArgumentException("A név nem lehet üres!");
    }

    /**
     * Method for getting phoneNumber variable.
     * @return phoneNumber variable
     */
    public PhoneNumber getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Method for setting phoneNumber variable.
     * @param phoneNumber what we want to set the phoneNumber variable to
     */
    public void setPhoneNumber(PhoneNumber phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Method for getting address variable.
     * @return address variable
     */
    public String getAddress() {
        return address;
    }

    /**
     * Method for setting address variable.
     * @param address what we want to set the address variable to
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Method for getting email variable.
     * @return email variable
     */
    public String getEmail() {
        return email;
    }

    /**
     * Method for setting email variable.
     * @param email what we want to set the email variable to
     * @throws IllegalArgumentException when {@code email} is not a valid email address
     */
    public void setEmail(String email) throws IllegalArgumentException {
        if(EmailValidator.getInstance().isValid(email))
            this.email = email;
        else
            throw new IllegalArgumentException("Hibás email cím!");
    }

    /**
     * Tells if two Contact objects are the same.
     * @param o what we want to compare to
     * @return true if two Contact objects are the same, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Contact contact = (Contact) o;
        return Objects.equals(getLastName(), contact.getLastName()) &&
                Objects.equals(getFirstName(), contact.getFirstName()) &&
                Objects.equals(getPhoneNumber(), contact.getPhoneNumber()) &&
                Objects.equals(getAddress(), contact.getAddress()) &&
                Objects.equals(getEmail(), contact.getEmail());
    }

    /**
     * Returns hashcode of the Contact object.
     * @return hashcode of the Contact object
     */
    @Override
    public int hashCode() {
        return Objects.hash(getLastName(), getFirstName(), getPhoneNumber(), getAddress(), getEmail());
    }
}
