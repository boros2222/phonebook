package hu.unideb.inf;


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PhoneNumberTest {
    @Test
    public void empty() {
        PhoneNumber n = PhoneNumber.of("00000000000");
        assertEquals("", n.toString(), "Phone number should be empty");
    }
}