package hu.unideb.inf;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class ContactsTest {

    private Contacts contacts;

    @BeforeEach
    public void setUp() {
        contacts = new Contacts("test.json");
    }

    @AfterEach
    public void tearDown() {
        contacts = null;
    }

    @Test
    public void contactsAddContact() {
        contacts.addContact(new Contact());

        assertEquals(new Contact(), contacts.getElements().get(0));
    }

    @Test
    public void contactsRemoveContact() {
        Contact c = new Contact();
        c.setFirstName("Pali");
        contacts.addContact(c);
        contacts.removeContact(c);

        assertTrue(contacts.getElements().isEmpty());
    }

    @Test
    public void contactsSaveAndLoad() throws Exception {
        Contact c = new Contact();
        c.setFirstName("Pali");
        contacts.addContact(c);

        ArrayList<Contact> before = contacts.getElements();
        contacts.saveToDisk();
        contacts.loadFromDisk();
        ArrayList<Contact> after = contacts.getElements();

        assertEquals(after, before);
    }

    @Test
    public void contactsGetElements() {
        Contact c = new Contact();
        c.setFirstName("Pali");

        contacts.addContact(c);

        ArrayList<Contact> arr = new ArrayList<>();
        arr.add(c);

        assertEquals(arr, contacts.getElements());
    }

    @Test
    public void contactsSetElements() {
        Contact c = new Contact();
        c.setFirstName("Pali");

        ArrayList<Contact> arr = new ArrayList<>();
        arr.add(c);

        contacts.setElements(arr);

        assertEquals(arr, contacts.getElements());
    }

    @Test
    public void contactsGetFileName() {
        assertEquals("test.json", contacts.getFileName());
    }

    @Test
    public void contactsSetFileName() {
        contacts.setFileName("asd.json");

        assertEquals("asd.json", contacts.getFileName());
    }

    @Test
    public void contactsFileNameWrongFormat() {
        IllegalArgumentException thrown =
                assertThrows(IllegalArgumentException.class,
                        () -> contacts.setFileName("asd.txt"),
                        "Exception expected to be thrown");

        assertTrue(thrown.getMessage().equals("Hibás fájlnév!"));
    }

}
