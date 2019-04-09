package hu.unideb.inf;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Contacts {
    private ArrayList<Contact> elements = new ArrayList<Contact>();
    private String fileName;

    public Contacts() { this.fileName = "data.json"; }

    public Contacts(String fileName) {
        this.fileName = fileName;
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
        BufferedReader bufferedReader = new BufferedReader(new FileReader(this.fileName));

        this.elements = gson.fromJson(bufferedReader, new TypeToken<ArrayList<Contact>>(){}.getType());
    }

    public void saveToDisk() throws IOException {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        FileWriter writer = new FileWriter(this.fileName);
        writer.write(gson.toJson(this.elements));
        writer.close();
    }

    public ArrayList<Contact> getElements() {
        return elements;
    }

    public void setElements(ArrayList<Contact> elements) {
        this.elements = elements;
    }
}
