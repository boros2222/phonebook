package hu.unideb.inf;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import java.util.ArrayList;

public class FXMLController {

    @FXML
    private AnchorPane mainPane;
    @FXML
    private Label menuLabel;
    @FXML
    private MenuBar menu;
    @FXML
    private Menu contactsMenu;
    @FXML
    private Menu newContactMenu;
    @FXML
    private Menu creatorMenu;
    @FXML
    private AnchorPane contentPane;
    @FXML
    private TableView tableView;

    @FXML
    private void handleButtonAction(ActionEvent event) {
    }

    public void initialize() {
        setupTable();
        ObservableList<ContactAdapter> viewData = loadAndAdaptData();
        if (viewData != null)
            tableView.setItems(viewData);

    }

    private ObservableList<ContactAdapter> loadAndAdaptData() {
        Contacts c = new Contacts("contacts.json");
        /*try {
            c.addContact(new Contact("Teszt", "Elek", PhoneNumber.of("06001234567"), "9999 Perecváros, sajt utca 9.", "elek@teszt.hu"));
            c.saveToDisk();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }*/

        try {
            c.loadFromDisk();
/*            System.out.println(c.getElements().getClass());

            Iterator<Contact> it = c.getElements().iterator();
            while(it.hasNext()) {
                System.out.println("=====");
                PhoneNumber a = it.next().getPhoneNumber();
                if (a != null)
                    System.out.println(a.toString());
                else
                    System.out.println(a);
                System.out.println("=====");
            }*/

            ArrayList<Contact> data = c.getElements();
            ObservableList<ContactAdapter> viewData = FXCollections.observableArrayList();

            for(Contact contact: data)
                viewData.add(new ContactAdapter(contact));

            return viewData;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    private void setupTable() {
        menuLabel.setText("Névjegyek");

        TableColumn lastNameCol = new TableColumn("Vezetéknév");
        lastNameCol.setMinWidth(120);
        lastNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        lastNameCol.setCellValueFactory(new PropertyValueFactory<ContactAdapter, String>("lastName"));

        TableColumn firstNameCol = new TableColumn("Keresztnév");
        firstNameCol.setMinWidth(120);
        firstNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        firstNameCol.setCellValueFactory(new PropertyValueFactory<ContactAdapter, String>("firstName"));

        TableColumn phoneNumberCol = new TableColumn("Telefonszám");
        phoneNumberCol.setMinWidth(150);
        phoneNumberCol.setCellFactory(TextFieldTableCell.forTableColumn());
        phoneNumberCol.setCellValueFactory(new PropertyValueFactory<ContactAdapter, String>("phoneNumber"));

        TableColumn addressCol = new TableColumn("Lakcím");
        addressCol.setMinWidth(300);
        addressCol.setCellFactory(TextFieldTableCell.forTableColumn());
        addressCol.setCellValueFactory(new PropertyValueFactory<ContactAdapter, String>("address"));

        TableColumn emailCol = new TableColumn("Email");
        emailCol.setMinWidth(250);
        emailCol.setCellFactory(TextFieldTableCell.forTableColumn());
        emailCol.setCellValueFactory(new PropertyValueFactory<ContactAdapter, String>("email"));

        tableView.getColumns().addAll(lastNameCol, firstNameCol, phoneNumberCol, addressCol, emailCol);
    }
}
