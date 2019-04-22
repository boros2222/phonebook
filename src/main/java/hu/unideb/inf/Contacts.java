package hu.unideb.inf;

import java.io.*;
import java.util.ArrayList;
import java.util.regex.Pattern;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Contacts {
    private ArrayList<Contact> elements = new ArrayList<>();
    private String fileName;
    private Pattern filenamePattern = Pattern.compile("[A-Za-z0-9_-]+\\.json");

    private static Logger logger = LoggerFactory.getLogger(Controller.class);

    public Contacts() { this.fileName = "data.json"; }

    public Contacts(String fileName) throws Exception {
        if(filenamePattern.matcher(fileName).matches())
            this.fileName = fileName;
        else
            throw new Exception("Hibás fájlnév!");
    }

    public void addContact(Contact contact) {
        elements.add(contact);
    }

    public void removeContact(Contact contact) {
        elements.remove(contact);
    }

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

    public ArrayList<Contact> getElements() {
        return elements;
    }

    public void setElements(ArrayList<Contact> elements) {
        this.elements = elements;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) throws Exception {
        if(filenamePattern.matcher(fileName).matches())
            this.fileName = fileName;
        else
            throw new Exception("Hibás fájlnév!");
    }
}
