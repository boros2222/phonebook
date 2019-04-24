package hu.unideb.inf;

import java.io.*;
import java.util.ArrayList;
import java.util.regex.Pattern;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class for storing multiple contacts.
 */
public class Contacts {

    /** Stores contacts. */
    private ArrayList<Contact> elements = new ArrayList<>();

    /** Name of the file for saving contacts to disk. */
    private String fileName;

    /** Pattern for checking valid filename. */
    private Pattern filenamePattern = Pattern.compile("[A-Za-z0-9_-]+\\.json");

    /** Logger. */
    private static Logger logger = LoggerFactory.getLogger(Controller.class);

    /**
     * Constructing Contacts object with a default filename.
     */
    public Contacts() { this.fileName = "data.json"; }

    /**
     * Constructing Contacts object with a given filename.
     * @param fileName name of file
     * @throws IllegalArgumentException when error happens. See: {@link #setFileName(String)}
     */
    public Contacts(String fileName) throws IllegalArgumentException {
        this.setFileName(fileName);
    }

    /**
     * Method for adding a contact.
     * @param contact what we want to add
     */
    public void addContact(Contact contact) {
        elements.add(contact);
    }

    /**
     * Method for removing a contact.
     * @param contact what we want to remove
     */
    public void removeContact(Contact contact) {
        elements.remove(contact);
    }

    /**
     * Method for loading contacts from a JSON file.
     * @throws FileNotFoundException when error occurs while reading from file
     */
    public void loadFromDisk() throws FileNotFoundException {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        File file = new File(this.fileName);

        if(file.exists()) {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            this.elements = gson.fromJson(bufferedReader, new TypeToken<ArrayList<Contact>>(){}.getType());

            logger.info("Data successfully loaded from file (" + this.fileName + ")");
        }
        else {
            this.elements = new ArrayList<>();

            logger.info("File not found (" + file.getName() + ")");
        }
    }

    /**
     * Method for saving contacts to a JSON file.
     * @throws IOException when error occurs while saving to file
     */
    public void saveToDisk() throws IOException {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        File file = new File(this.fileName);

        if(file.exists()) {
            logger.info("Appending to file (" + file.getName() + ")");
            FileWriter writer = new FileWriter(file);
            writer.write(gson.toJson(this.elements));
            writer.close();
        }
        else {
            logger.info("Creating file (" + file.getName() + ")");
            FileWriter writer = new FileWriter(file, false);
            writer.write(gson.toJson(this.elements));
            writer.close();
        }

        logger.info("Data successfully written to file (" + this.fileName + ")");
    }

    /**
     * Method for getting a list of contacts.
     * @return list of contacts
     */
    public ArrayList<Contact> getElements() {
        return elements;
    }

    /**
     * Method for setting the contacts stored in object.
     * @param elements list of contacts
     */
    public void setElements(ArrayList<Contact> elements) {
        this.elements = elements;
    }

    /**
     * Method for getting the name of file.
     * @return the name of file
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * Method for setting the name of file.
     * @param fileName what we want to set the filename to
     * @throws IllegalArgumentException when {@code fileName} does not end with ".json"
     */
    public void setFileName(String fileName) throws IllegalArgumentException {
        if(filenamePattern.matcher(fileName).matches())
            this.fileName = fileName;
        else
            throw new IllegalArgumentException("Hibás fájlnév!");
    }
}
