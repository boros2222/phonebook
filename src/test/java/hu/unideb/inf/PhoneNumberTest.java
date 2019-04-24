package hu.unideb.inf;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PhoneNumberTest {

    @Test
    public void emptyPhoneNumber() {
        PhoneNumber n = PhoneNumber.of("00000000000");
        assertEquals("", n.toString(), "Phone number should be empty");
    }

    @Test
    public void phoneNumberGetter() {
        PhoneNumber n = PhoneNumber.of("06701234567");
        assertEquals("06701234567", n.getNumber(), "Phone number should be the same");
    }

    @Test
    public void phoneNumberToString() {
        PhoneNumber n = PhoneNumber.of("06701234567");
        assertEquals("06 70 / 123-4567", n.toString(), "Phone number should be in a specific format");
    }

    @Test
    public void phoneNumberEquals() {
        PhoneNumber n1 = PhoneNumber.of("06701234567");
        PhoneNumber n2 = PhoneNumber.of("06701234567");
        assertTrue(n1.equals(n2), "n1 and n2 should be equal");
    }

    @Test
    public void phoneNumberShouldContainOnlyNumbers() {
        IllegalArgumentException thrown =
                assertThrows(IllegalArgumentException.class,
                        () -> PhoneNumber.of("123abc"),
                        "Exception expected to be thrown");

        assertTrue(thrown.getMessage().equals("A telefonszám csak számot tartalmazhat!"));
    }

    @Test
    public void phoneNumberShouldBeLongEnough() {
        IllegalArgumentException thrown =
                assertThrows(IllegalArgumentException.class,
                        () -> PhoneNumber.of("123456"),
                        "Exception expected to be thrown");

        assertTrue(thrown.getMessage().equals("A telefonszám csak 11 karakter hosszú lehet!"));
    }
}