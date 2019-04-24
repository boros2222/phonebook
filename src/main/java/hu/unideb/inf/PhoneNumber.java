package hu.unideb.inf;

import java.util.Objects;

/**
 * Class for storing phone numbers.
 */
public class PhoneNumber {

    /** Phone number. */
    String number;

    /**
     * Constructor for instantiating a PhoneNumber.
     * It is private because {@link #of(String)}
     * method is used for creating an object
     * @param number phone number we want to store
     * @throws IllegalArgumentException when {@code number}
     * parameter is invalid {@link #setNumber(String)}
     */
    private PhoneNumber(String number) throws IllegalArgumentException {
        this.setNumber(number);
    }

    /**
     * Method for creating a PhoneNumber instance.
     * @param number phone number we want to store
     * @return new instance of PhoneNumber
     * @throws IllegalArgumentException when {@code number}
     * parameter is invalid {@link #setNumber(String)}
     */
    public static PhoneNumber of(String number) throws IllegalArgumentException {
        return new PhoneNumber(number);
    }

    /**
     * Gives back a formatted form of {@code number}.
     * Example: "06701234567" becomes "06 70 / 123-4567"
     * Example: "00000000000" means empty phone number
     * @return formatted phone number as string
     */
    public String toString() {
        if(!number.equals("00000000000"))
            return this.number.replaceFirst("(\\d{2})(\\d{2})(\\d{3})(\\d+)", "$1 $2 / $3-$4");
        else
            return "";
    }

    /**
     * Returns number variable.
     * @return number variable
     */
    public String getNumber() {
        return number;
    }

    /**
     * Method for setting phone number.
     * @param number what we want to set the number variable to
     * @throws IllegalArgumentException when number contains other than number
     * @throws IllegalArgumentException when number's length is not 11
     */
    public void setNumber(String number) throws IllegalArgumentException {
        if (!number.matches("[0-9]+"))
            throw new IllegalArgumentException("A telefonszám csak számot tartalmazhat!");
        if (number.length() != 11)
            throw new IllegalArgumentException("A telefonszám csak 11 karakter hosszú lehet!");

        this.number = number;
    }

    /**
     * Tells if two PhoneNumber objects are the same.
     * @param o the object we want to compare to
     * @return true if the two objects are the same, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PhoneNumber that = (PhoneNumber) o;

        return this.number.equals(that.getNumber());
    }

    /**
     * Returns hashcode of PhoneNumber object.
     * @return hashcode of PhoneNumber object
     */
    @Override
    public int hashCode() {
        return Objects.hash(number);
    }
}
