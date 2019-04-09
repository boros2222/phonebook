package hu.unideb.inf;

public class PhoneNumber {
    String number;

    private PhoneNumber(String number) {
        this.number = number;
    }

    public static PhoneNumber of(String number) throws Exception {
        if (!number.matches("[0-9]+"))
            throw new Exception("A telefonszám csak számot tartalmazhat!");
        if (number.length() != 11)
            throw new Exception("A telefonszám csak 11 karakter hosszú lehet!");

        return new PhoneNumber(number);
    }

    public String toString() {
        return this.number.replaceFirst("(\\d{2})(\\d{2})(\\d{3})(\\d+)", "$1 $2 / $3-$4");
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) throws Exception {
        if (!number.matches("[0-9]+"))
            throw new Exception("A telefonszám csak számot tartalmazhat!");
        if (number.length() != 11)
            throw new Exception("A telefonszám csak 11 karakter hosszú lehet!");

        this.number = number;
    }
}
