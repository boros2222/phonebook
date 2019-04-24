package hu.unideb.inf;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ContactTest {

    private Contact contact;

    @BeforeEach
    public void setUp() {
        contact = new Contact("Teszt", "Elek", PhoneNumber.of("06701234567"), "Debrecen", "elek@teszt.hu");
    }

    @AfterEach
    public void tearDown() {
        contact = null;
    }

    @Test
    public void contactEquals() {
        Contact c1 = new Contact();
        Contact c2 = new Contact();
        assertTrue(c1.equals(c2), "c1 and c2 should be equal");
    }

    @Test
    public void contactGetLastName() {
        assertEquals("Teszt", contact.getLastName());
    }

    @Test
    public void contactSetLastName() {
        contact.setLastName("Haha");
        assertEquals("Haha", contact.getLastName());
    }

    @Test
    public void contactLastNameCannotBeEmpty() {
        IllegalArgumentException thrown =
                assertThrows(IllegalArgumentException.class,
                        () -> contact.setLastName(""),
                        "Exception expected to be thrown");

        assertTrue(thrown.getMessage().equals("A név nem lehet üres!"));
    }

    @Test
    public void contactGetFirstName() {
        assertEquals("Elek", contact.getFirstName());
    }

    @Test
    public void contactSetFirstName() {
        contact.setFirstName("Pista");
        assertEquals("Pista", contact.getFirstName());
    }

    @Test
    public void contactFirstNameCannotBeEmpty() {
        IllegalArgumentException thrown =
                assertThrows(IllegalArgumentException.class,
                        () -> contact.setFirstName(""),
                        "Exception expected to be thrown");

        assertTrue(thrown.getMessage().equals("A név nem lehet üres!"));
    }

    @Test
    public void contactGetPhoneNumber() {
        assertEquals(PhoneNumber.of("06701234567"), contact.getPhoneNumber());
    }

    @Test
    public void contactSetPhoneNumber() {
        contact.setPhoneNumber(PhoneNumber.of("00000000000"));
        assertEquals(PhoneNumber.of("00000000000"), contact.getPhoneNumber());
    }

    @Test
    public void contactGetAddress() {
        assertEquals("Debrecen", contact.getAddress());
    }

    @Test
    public void contactSetAddress() {
        contact.setAddress("Budapest");
        assertEquals("Budapest", contact.getAddress());
    }

    @Test
    public void contactEmail() {
        assertEquals("elek@teszt.hu", contact.getEmail());
    }

    @Test
    public void contactSetEmail() {
        contact.setEmail("pista@haha.hu");
        assertEquals("pista@haha.hu", contact.getEmail());
    }

    @Test
    public void contactEmailWrongFormat() {
        IllegalArgumentException thrown =
                assertThrows(IllegalArgumentException.class,
                        () -> contact.setEmail("pista@"),
                        "Exception expected to be thrown");

        assertTrue(thrown.getMessage().equals("Hibás email cím!"));
    }
}
